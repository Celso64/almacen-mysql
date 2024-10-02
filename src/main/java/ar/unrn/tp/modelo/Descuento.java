package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.util.Porcentaje;
import ar.unrn.tp.modelo.util.RangoFechas;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "descuento")
@Inheritance
@DiscriminatorColumn(name = "tipo_descuento")
public abstract class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected LocalDate fechaInicio, fechaFin;
    protected Double porcentajeDescuento;

    protected Descuento(LocalDate fechaInicio, LocalDate fechaFin, Double porcentajeDescuento) {
        var rango = new RangoFechas(fechaInicio, fechaFin);
        this.fechaInicio = rango.getInicio();
        this.fechaFin = rango.getFin();
        this.porcentajeDescuento = new Porcentaje(porcentajeDescuento).getCoeficiente();
    }

    public Boolean estaVigente() {
        return LocalDate.now().isBefore(this.fechaFin);
    }

    public abstract String marca();

    public abstract Double calcularDescuento(Producto producto);

    /**
     * Calcula el monto que se debe descontar.
     *
     * @param original   : Monto sobre el que aplicar descuento.
     * @param porcentaje : Porcentaje del descuento.
     * @return El monto que se debe descontar para aplicar el descuento, 0.0 si el porcentaje esta fuera de rango
     * [0.0;1.0]
     */
    protected Double calcularDescuento(Double original, Double porcentaje) {
        if (this.estaVencido()) throw new IllegalStateException("Descuento Vencido");
        if (porcentaje > 1.0 || porcentaje < 0.0) return 0.0;
        return original * (1.0 - porcentaje);
    }

    private Boolean estaVencido() {
        return this.fechaFin.isBefore(LocalDate.now());
    }

    public Boolean estaSuperpuesto(RangoFechas rangoFechas) {
        return new RangoFechas(this.fechaInicio, this.fechaFin).seSuperpone(rangoFechas.getInicio(), rangoFechas.getFin());
    }


    public RangoFechas getPeriodo() {
        return new RangoFechas(this.fechaInicio, this.fechaFin);
    }

    public void agregarDescuentoTarjeta(List<DescuentoTarjeta> descuentoTarjetas) {
        return;
    }

    public abstract Double calcularDescuento(String marca, Double precio);

    public void agregarDescuentoMarca(List<DescuentoMarca> descuentoTarjetas) {
        return;
    }
}

