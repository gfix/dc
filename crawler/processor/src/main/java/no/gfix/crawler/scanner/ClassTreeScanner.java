package no.gfix.crawler.scanner;

import com.sun.source.tree.*;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTreeScanner extends TreePathScanner<Object, Trees> {

    private Name elementName;
    private Map<Name, BlockTree> methods = new HashMap<Name, BlockTree>();
    private List<VariableTree> fields = new ArrayList<VariableTree>();

    private ClassTreeScanner() {

    }

    public static ClassTreeScanner create(Name elementName) {
        ClassTreeScanner scanner = new ClassTreeScanner();
        scanner.elementName = elementName;
        return scanner;
    }

    @Override
    public Object visitClass(ClassTree node, Trees trees) {
        if (node.getSimpleName().equals(this.elementName)) {
            for (Tree tree : node.getMembers()) {
                if (tree instanceof MethodTree) {
                    MethodTree methodTree = (MethodTree) tree;
                    methods.put(methodTree.getName(), methodTree.getBody());
                }
                if (tree instanceof VariableTree) {
                    fields.add((VariableTree) tree);
                }
            }
        }
        return super.visitClass(node, trees);
    }

    @Override
    public Object visitMethod(MethodTree node, Trees trees) {
        if (node.getName().equals(this.elementName)) {
            System.out.println("test");
        }
        return super.visitMethod(node, trees);
    }

    public Map<Name, BlockTree> getMethods() {
        return methods;
    }

    public List<VariableTree> getClassFields() {
        return fields;
    }
}
