package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.util.NumeroTarjeta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tarjeta")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero, marca;

    private Double fondos;


    public Tarjeta(String numero, String marcaTarjeta) {
        this.numero = new NumeroTarjeta(numero).toString();
        this.fondos = 0.0;
        this.marca = new MarcaTarjeta(marcaTarjeta).toString().toUpperCase();
    }

    public Tarjeta(String marca) {
        this.marca = marca;
    }

    public Tarjeta(String numero, String marcaTarjeta, Double fondos) {
        this(numero, marcaTarjeta);
        this.fondos = fondos;
    }

    public void agregarFondos(Double monto) {
        if (monto > 0) throw new IllegalArgumentException("No se puede agregar monto negativo");
        this.fondos += monto;
    }

    public void quitarFondos(Double monto) {
        if (monto > 0) throw new IllegalArgumentException("No se puede quitar monto negativo");
        if ((this.fondos - monto) < 0.0) throw new IllegalStateException("No hay sufientes fondos");
        this.fondos -= monto;
    }

    public Boolean esMarca(String marcaTarjeta) {
        return this.marca.equals(marcaTarjeta);
    }

    public Boolean tieneID(Long id) {
        return this.id.equals(id);
    }

}
