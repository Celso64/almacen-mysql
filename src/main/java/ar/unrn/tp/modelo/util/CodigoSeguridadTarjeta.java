package ar.unrn.tp.modelo.util;

public class CodigoSeguridadTarjeta {

    private static final Integer MINIMO = 100;
    private static final Integer MAXIMO = 999;

    private final Integer codigo;

    public CodigoSeguridadTarjeta(Integer codigo) {
        if (esMenor(codigo) || esMayor(codigo)) throw new IllegalArgumentException("Codigo Invalido");
        this.codigo = codigo;
    }

    public Integer toInteger(){
        return this.codigo;
    }

    private static Boolean esMenor(Integer numero){
        return numero < MINIMO;
    }

    private static Boolean esMayor(Integer numero){
        return numero > MAXIMO;
    }
}
