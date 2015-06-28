package no.gfix.application.module;

import no.gfix.application.service.second.SecondServiceImpl;
import no.gfix.application.service.second.SecondServiceInterface;
import no.gfix.crawler.annotations.Module;

@Module(id = "M002")
public class SecondModule {
    private SecondServiceInterface secondService = new SecondServiceImpl();

    public void doModuleStuff() {
        System.out.println("Doing first module stuff");
        secondService.callA();
    }

}
