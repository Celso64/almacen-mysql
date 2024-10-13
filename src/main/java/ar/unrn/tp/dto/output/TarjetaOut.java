package ar.unrn.tp.dto.output;

import ar.unrn.tp.modelo.Tarjeta;

public record TarjetaOut(Long id, String marca, String numero, Double fondos) {

    public static TarjetaOut fromModel(Tarjeta t) {
        return new TarjetaOut(t.getId(), t.getMarca().getNombre().toUpperCase(), t.getNumero(), t.getFondos());
    }

}
