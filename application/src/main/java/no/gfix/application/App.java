package no.gfix.application;

import no.gfix.application.module.FirstModule;

public class App {

    public static void main(String[] args) {
        FirstModule firstModule = new FirstModule();
        firstModule.doModuleStuff();
    }
}
