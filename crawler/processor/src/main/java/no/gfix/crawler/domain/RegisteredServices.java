package no.gfix.crawler.domain;

import com.google.common.collect.Lists;
import no.gfix.crawler.domain.service.ServiceMethod;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredServices {
    private List<Name> serviceNames = new ArrayList<Name>();
    private Map<Name, List<ServiceMethod>> serviceMethodMap = new HashMap<Name, List<ServiceMethod>>();

    private RegisteredServices() {
    }

    public static RegisteredServices instance() {
        return new RegisteredServices();
    }

    public void register(Name serviceName, ServiceMethod serviceMethod) {
        if (!serviceNames.contains(serviceName)) {
            serviceNames.add(serviceName);
        }

        if (!serviceMethodMap.containsKey(serviceName)) {
            serviceMethodMap.put(serviceName, Lists.newArrayList(serviceMethod));
        } else {
            serviceMethodMap.get(serviceName).add(serviceMethod);
        }

    }

    public boolean contains(Name name) {
        return serviceNames.contains(name);
    }

    public List<ServiceMethod> getMethods(Name serviceName) {
        return serviceMethodMap.get(serviceName);
    }
}
