package ar.unrn.tp.servicio.utils;

import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.DescuentoMarca;
import ar.unrn.tp.modelo.DescuentoTarjeta;

import java.util.ArrayList;
import java.util.List;

public class DescuentosManager {
    private List<DescuentoTarjeta> descuentoTarjetas;
    private List<DescuentoMarca> descuentoMarcas;

    public DescuentosManager(List<Descuento> descuentos) {
        this.descuentoTarjetas = new ArrayList<>();
        this.descuentoMarcas = new ArrayList<>();

        descuentos.forEach(x -> x.agregarDescuentoTarjeta(this.descuentoTarjetas));
        descuentos.forEach(x -> x.agregarDescuentoMarca(this.descuentoMarcas));
    }

    public DescuentoMarca getDescuentoMarca(String marcaProducto) {
        return this.descuentoMarcas.stream().filter(x -> x.esMarca(marcaProducto)).findFirst().orElse(null);
    }

    public DescuentoTarjeta getDescuentoTarjeta(String marcaTarjeta) {
        return this.descuentoTarjetas.stream().filter(x -> x.esMarca(marcaTarjeta)).findFirst().orElse(null);
    }
}
