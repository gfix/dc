package no.gfix.application.service.first;

public class FirstServiceImpl implements FirstServiceInterface {
    @Override
    public void callA() {
        System.out.println("A called");
    }

    @Override
    public void callB() {
        System.out.println("B called");
    }
}
