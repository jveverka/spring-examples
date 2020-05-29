package itx.examples.springboot.di.services;

public interface ClientService {

    String printDefault(String message);

    String printStdErr(String message);

    String printStdOut(String message);

    String getData();

    String getDataPrototype();

}
