package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@DiscriminatorValue("DM")
public class DescuentoMarca extends Descuento {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    public DescuentoMarca(LocalDate fechaInicio, LocalDate fechaFin, Marca marca, Double porcentaje) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    @Override
    public String marca() {
        return this.marca.getNombre();
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return (this.esMarca(producto.nombreMarca()))
                ? (super.calcularDescuento(producto.getPrecio(), super.getPorcentajeDescuento()))
                : 0.0;
    }

    @Override
    public Double calcularDescuento(String marca, Double precio) {
        return (this.esMarca(marca.toUpperCase()))
                ? (super.calcularDescuento(precio, super.getPorcentajeDescuento()))
                : precio;
    }

    public Boolean esMarca(String marca) {
        return this.marca.esMarca(marca.toUpperCase());
    }

    @Override
    public void agregarDescuentoMarca(List<DescuentoMarca> descuentoTarjetas) {
        descuentoTarjetas.add(this);
    }
}
