package no.gfix.crawler.processor;

import no.gfix.crawler.domain.ModuleWrapper;
import no.gfix.crawler.domain.RegisteredServices;
import no.gfix.crawler.domain.Usage;
import no.gfix.crawler.domain.service.ServiceField;
import no.gfix.crawler.domain.service.ServiceMethod;
import no.gfix.crawler.domain.service.ServiceStatement;

import java.util.ArrayList;
import java.util.List;

public class UsageAnalyzer {

    private final List<ServiceStatement> possibleServiceStatements;
    private final ModuleWrapper moduleWrapper;

    public UsageAnalyzer(List<ServiceStatement> possibleServiceStatements, ModuleWrapper moduleWrapper) {
        this.possibleServiceStatements = possibleServiceStatements;
        this.moduleWrapper = moduleWrapper;
    }

    public static UsageAnalyzer create(RegisteredServices registeredServices, List<ServiceField> utilizedServiceFields, ModuleWrapper moduleWrapper) {
        List<ServiceStatement> possibleServiceStatements = findPossibleServiceStatements(registeredServices, utilizedServiceFields);
        return new UsageAnalyzer(possibleServiceStatements, moduleWrapper);
    }

    // TODO: look for calls like "getService" which return a service interface, and add to this list (is there a better, more efficient, data structure?)
    private static List<ServiceStatement> findPossibleServiceStatements(RegisteredServices registeredServices, List<ServiceField> usedFields) {
        //exact science: average number of methods per service interface is 2
        List<ServiceStatement> possibleServiceStatements = new ArrayList<ServiceStatement>(usedFields.size() * 2);
        for (ServiceField usedField : usedFields) {
            for (ServiceMethod serviceMethod : registeredServices.getMethods(usedField.getServiceName())) {
                String statement = usedField.createStatement(serviceMethod.getName());
                possibleServiceStatements.add(ServiceStatement.create(statement, serviceMethod));
            }
        }

        return possibleServiceStatements;
    }

    public List<Usage> analyze() {
        List<Usage> usages = new ArrayList<Usage>();
        for (String statement : moduleWrapper.getStatements()) {
            for (ServiceStatement possibleServiceStatement : possibleServiceStatements) {
                if (statement.contains(possibleServiceStatement.getStatement())) {
                    usages.add(Usage.create(moduleWrapper.getModuleId(), possibleServiceStatement.getServiceMethod().getId()));
                }
            }

        }
        return usages;
    }

}
