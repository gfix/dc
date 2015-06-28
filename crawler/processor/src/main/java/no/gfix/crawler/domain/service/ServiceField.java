package no.gfix.crawler.domain.service;

import javax.lang.model.element.Name;

public class ServiceField {
    private final Name serviceName;
    private final Name fieldName;

    public ServiceField(Name serviceName, Name fieldName) {
        this.serviceName = serviceName;
        this.fieldName = fieldName;
    }

    public static ServiceField create(Name serviceName, Name fieldName) {
        return new ServiceField(serviceName, fieldName);
    }

    public String createStatement(Name methodName) {
        return getFieldName() + "." + methodName.toString();
    }

    public Name getFieldName() {
        return fieldName;
    }

    public Name getServiceName() {
        return serviceName;
    }
}
