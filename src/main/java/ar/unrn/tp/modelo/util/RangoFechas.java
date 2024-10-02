package ar.unrn.tp.modelo.util;

import java.time.LocalDate;

public class RangoFechas {

    private final LocalDate inicio,fin;

    public RangoFechas(LocalDate inicio, LocalDate fin) {
        if (inicio.isAfter(fin)) throw new IllegalArgumentException("La fecha de Inicio es mayor a la de Fin");
        if(fin.isBefore(LocalDate.now())) throw new IllegalArgumentException("Descuento Vencido");
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public Boolean seSuperpone(LocalDate inicio, LocalDate fin){
        return (this.inicio.isBefore(fin) && this.fin.isAfter(inicio))
                || (inicio.isBefore(this.fin) && fin.isAfter(this.inicio));
    }
}
