package ar.unrn.tp.modelo.util;

import java.util.Objects;

public class Dni {

    private final Integer dni;

    public Dni(String dni) {
        Objects.requireNonNull(dni);
        this.dni = Integer.parseInt(dni);
    }

    @Override
    public String toString() {
        return dni.toString();
    }
}
