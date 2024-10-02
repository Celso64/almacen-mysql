package ar.unrn.tp.modelo.util;

public class Porcentaje {

    private final Double porcentaje;

    public Porcentaje(Double porcentaje) {
        if (esMenor(porcentaje) || esMayor(porcentaje)) throw new IllegalArgumentException("Porcentaje fuera de rango.");
        this.porcentaje = porcentaje;
    }

    public Double getCoeficiente(){
        return this.porcentaje;
    }

    private Boolean esMenor(Double numero) {
        return numero < 0.0;
    }

    private Boolean esMayor(Double numero) {
        return numero > 1.0;
    }
}
