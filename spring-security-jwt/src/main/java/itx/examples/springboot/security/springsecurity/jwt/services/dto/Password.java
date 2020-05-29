package itx.examples.springboot.security.springsecurity.jwt.services.dto;

public class Password {

    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public boolean verify(String value) {
        return this.value.equals(value);
    }

    public static Password from(String value) {
        return new Password(value);
    }

}
