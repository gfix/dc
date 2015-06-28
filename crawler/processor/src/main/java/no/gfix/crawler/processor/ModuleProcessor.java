package no.gfix.crawler.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.Trees;
import no.gfix.crawler.annotations.Module;
import no.gfix.crawler.annotations.Service;
import no.gfix.crawler.domain.ModuleWrapper;
import no.gfix.crawler.domain.RegisteredServices;
import no.gfix.crawler.domain.Usage;
import no.gfix.crawler.domain.service.ServiceField;
import no.gfix.crawler.domain.service.ServiceMethod;
import no.gfix.crawler.scanner.ClassTreeScanner;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public class ModuleProcessor extends AbstractProcessor {

    private RegisteredServices registeredServices = RegisteredServices.instance();
    private Set<Usage> usages = new HashSet<Usage>();
    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.trees = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element annotatedService : roundEnv.getElementsAnnotatedWith(Service.class)) {
            // Do validation on correct use
            registerService((ExecutableElement) annotatedService);
        }

        for (Element annotatedModule : roundEnv.getElementsAnnotatedWith(Module.class)) {
            // Do validations on correct annotation use here
            TypeElement module = (TypeElement) annotatedModule;
            ClassTreeScanner scanner = scanElement(module);

            List<ServiceField> utilizedServiceFields = findUtilizedServiceFields(scanner.getClassFields());

            UsageAnalyzer analyzer = UsageAnalyzer.create(
                    registeredServices,
                    utilizedServiceFields,
                    ModuleWrapper.create(
                            module.getAnnotation(Module.class).id(),
                            scanner.getMethods()
                    )
            );

            usages.addAll(analyzer.analyze());
        }

        // Json, write to file etc
        //Gson gson = new Gson();
        //String s = gson.toJson(usages);
        //filer.createResource()

        return false;
    }

    private List<ServiceField> findUtilizedServiceFields(List<VariableTree> variableTrees) {
        List<ServiceField> serviceFields = new ArrayList<ServiceField>();
        for (VariableTree variableTree : variableTrees) {
            IdentifierTree type = (IdentifierTree) variableTree.getType();
            if (registeredServices.contains(type.getName())) {
                serviceFields.add(ServiceField.create(type.getName(), variableTree.getName()));
            }
        }

        return serviceFields;
    }

    private void registerService(ExecutableElement method) {
        Name interfaceName = method.getEnclosingElement().getSimpleName();
        registeredServices.register(interfaceName, ServiceMethod.create(method.getAnnotation(Service.class).id(), method.getSimpleName()));
    }

    private ClassTreeScanner scanElement(Element element) {
        ClassTreeScanner classTreeScanner = ClassTreeScanner.create(element.getSimpleName());
        classTreeScanner.scan(
                this.trees.getPath(element),
                this.trees
        );
        return classTreeScanner;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(Module.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
