package ar.unrn.tp.modelo.util;

import java.util.Objects;

public class NumeroTarjeta {

    private String numero;

    public NumeroTarjeta(String numero) {
        Objects.requireNonNull(numero);
//        String numeroLimpio = numero.replace("_", "");
//        if(numeroLimpio.length() != 15) throw new IllegalArgumentException("Numero Invalido");
//
//        var numeros = new LinkedList<Integer>();
//
//        for(Integer i = 0; i < numeroLimpio.length(); i+=3){
//            Integer index = Math.min(i+3, numeroLimpio.length());
//            numeros.add(Integer.parseInt(numeroLimpio.substring(i, index)));
//        }

        this.numero = numero;
    }

    @Override
    public String toString() {
        return numero;
    }
}
