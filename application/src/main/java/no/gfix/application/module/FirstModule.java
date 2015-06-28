package no.gfix.application.module;

import no.gfix.application.service.first.FirstServiceImpl;
import no.gfix.application.service.first.FirstServiceInterface;
import no.gfix.application.service.second.SecondServiceImpl;
import no.gfix.application.service.second.SecondServiceInterface;
import no.gfix.crawler.annotations.Module;

@Module(id = "M001")
public class FirstModule {
    private FirstServiceInterface firstService = new FirstServiceImpl();
    private SecondServiceInterface secondService = new SecondServiceImpl();

    public void doModuleStuff() {
        System.out.println("Doing first module stuff");
        secondService.callA();
        doCallA();
        doCallB();
        firstService.callA();
    }

    private void doCallA() {
        firstService.callA();
    }

    private void doCallB() {
        firstService.callB();
    }
}
