package no.gfix.application.service.second;

import no.gfix.crawler.annotations.Service;
import no.gfix.crawler.annotations.ServiceInterface;

@ServiceInterface
public interface SecondServiceInterface {
    @Service(id = "S200")
    void callA();
}
