package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "marca_tarjeta")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarcaTarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nombre;

    @OneToMany(mappedBy = "marca")
    Set<Tarjeta> tarjetas;

    public MarcaTarjeta(String nombre) {
        this.nombre = nombre;
    }

    public Boolean esMarca(String marca) {
        return this.nombre.equalsIgnoreCase(marca);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarcaTarjeta that)) return false;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
