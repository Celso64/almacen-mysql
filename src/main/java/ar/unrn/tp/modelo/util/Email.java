package ar.unrn.tp.modelo.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    private static final String regex = "^(.+)@(.+)$";
    private static final Pattern pattern = Pattern.compile(regex);

    private final String email;

    public Email(String email) {
        Objects.requireNonNull(email);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Email Invalido");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
