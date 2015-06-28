package no.gfix.crawler.domain;

public final class Usage {
    private final String moduleId;
    private final String serviceId;

    private Usage(String moduleId, String serviceId) {
        this.moduleId = moduleId;
        this.serviceId = serviceId;
    }

    public static Usage create(String moduleId, String serviceId) {
        return new Usage(moduleId, serviceId);
    }

    public String getModuleId() {
        return moduleId;
    }

    public String getServiceId() {
        return serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Usage usage = (Usage) o;

        return moduleId.equals(usage.moduleId) && serviceId.equals(usage.serviceId);

    }

    @Override
    public int hashCode() {
        int result = moduleId.hashCode();
        result = 31 * result + serviceId.hashCode();
        return result;
    }
}


