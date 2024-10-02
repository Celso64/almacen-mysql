package ar.unrn.tp.dto.output;

import ar.unrn.tp.modelo.Producto;

public record ProductoOut(Long id, String nombre, String descripcion, Double precio, String marca, String categoria ) {

    public static ProductoOut fromModel(Producto p){
        return new ProductoOut(p.getId(), p.getCodigo(), p.getDescripcion(), p.getPrecio(), p.getMarca().getNombre(), p.getCategoria().getNombre());
    }
}
