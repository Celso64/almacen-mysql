package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("DT")
public class DescuentoTarjeta extends Descuento {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_id", nullable = false)
    private Tarjeta tarjeta;

    public DescuentoTarjeta(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Tarjeta tarjeta) {
        super(fechaInicio, fechaFin, porcentaje);
        this.tarjeta = tarjeta;
    }

    @Override
    public String marca() {
        return this.tarjeta.getMarca();
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return super.calcularDescuento(producto.getPrecio(), super.getPorcentajeDescuento());
    }


    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        super.setPorcentajeDescuento(porcentajeDescuento);
    }

    public Double getPorcentajeDescuento() {
        return super.getPorcentajeDescuento();
    }

    public Boolean esMarca(String marcaTarjeta) {
        return this.tarjeta.esMarca(marcaTarjeta);
    }

    @Override
    public void agregarDescuentoTarjeta(List<DescuentoTarjeta> descuentoTarjetas) {
        descuentoTarjetas.add(this);
    }

    @Override
    public Double calcularDescuento(String marca, Double precio) {

        return (this.tarjeta.esMarca(marca.toUpperCase())) ? super.calcularDescuento(precio, super.getPorcentajeDescuento()) : precio;
    }
}
