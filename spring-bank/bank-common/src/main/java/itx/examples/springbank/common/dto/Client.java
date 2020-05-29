package itx.examples.springbank.common.dto;

public class Client {

    private final ClientId id;
    private final String firstName;
    private final String lastName;
    private final Account account;

    public Client(ClientId id, String firstName, String lastName, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }

    public ClientId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Account getAccount() {
        return account;
    }

}
