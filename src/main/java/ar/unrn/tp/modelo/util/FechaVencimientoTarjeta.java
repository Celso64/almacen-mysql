package ar.unrn.tp.modelo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FechaVencimientoTarjeta {

    private final String fechaVencimiento;


    public FechaVencimientoTarjeta(String fechaVencimiento) {
            String patron = "dd/MM/yy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patron);
            LocalDate fecha = LocalDate.parse("01/" + fechaVencimiento, formatter);
            LocalDate fechaActual = LocalDate.now();

            if (!fecha.isAfter(fechaActual)) throw new IllegalArgumentException("Tarjeta Vencida");

            this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return fechaVencimiento;
    }
}
