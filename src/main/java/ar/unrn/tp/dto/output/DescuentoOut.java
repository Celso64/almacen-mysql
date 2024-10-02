package ar.unrn.tp.dto.output;

import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.DescuentoMarca;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public record DescuentoOut(Long id, String fechaInicio, String fechaFin, String marca,String porcentaje, String tipo, Long diasRestantes) {

    private static final DateTimeFormatter formato = DateTimeFormatter.ISO_DATE;
    private static final DecimalFormat decimalFormat =new DecimalFormat("#.##");

    public static DescuentoOut fromModel(Descuento d){
        String porcentaje = decimalFormat.format(d.getPorcentajeDescuento() * 100.0) + "%";
        String tipo = (d instanceof DescuentoMarca) ? "MARCA" : "TARJETA";

        LocalDate desde = d.getFechaInicio();
        LocalDate hasta = d.getFechaFin();

        Long resta = ChronoUnit.DAYS.between(desde, hasta);

        return new DescuentoOut(d.getId(), desde.format(formato), hasta.format(formato), d.marca(), porcentaje, tipo, resta);
    }

}
