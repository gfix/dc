package no.gfix.application.service.first;

import no.gfix.crawler.annotations.Service;
import no.gfix.crawler.annotations.ServiceInterface;

@ServiceInterface
public interface FirstServiceInterface {
    @Service(id = "S100")
    void callA();

    @Service(id = "S101")
    void callB();
}
