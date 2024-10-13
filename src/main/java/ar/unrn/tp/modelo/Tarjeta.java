package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.util.NumeroTarjeta;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tarjeta")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_marca_tarjeta", nullable = false)
    MarcaTarjeta marca;

    private Double fondos;


    public Tarjeta(String numero, String marcaTarjeta) {
        this.numero = new NumeroTarjeta(numero).toString();
        this.fondos = 0.0;
        this.marca = new MarcaTarjeta(marcaTarjeta);
    }

    public Tarjeta(String marca) {
        this.marca.getNombre().equalsIgnoreCase(marca);
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
        return this.marca.getNombre().equalsIgnoreCase(marcaTarjeta);
    }

    public Boolean tieneID(Long id) {
        return this.id.equals(id);
    }

}
