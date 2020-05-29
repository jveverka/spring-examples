package itx.examples.springbank.common.dto;

public class Account {

    private final AccountId id;
    private final Long credit;

    public Account(AccountId id, Long credit) {
        this.id = id;
        this.credit = credit;
    }

    public Long getCredit() {
        return credit;
    }

    public AccountId getId() {
        return id;
    }

}
