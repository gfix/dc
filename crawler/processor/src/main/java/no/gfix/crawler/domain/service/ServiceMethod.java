package no.gfix.crawler.domain.service;

import javax.lang.model.element.Name;

public class ServiceMethod {
    private final String id;
    private final Name name;

    private ServiceMethod(String id, Name name) {
        this.id = id;
        this.name = name;
    }

    public static ServiceMethod create(String id, Name name) {
        return new ServiceMethod(id, name);
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
