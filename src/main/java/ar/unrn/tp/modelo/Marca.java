package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "marca")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nombre;

    public Marca(String nombre) {
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre Vacio");
        this.nombre = nombre.toUpperCase();
    }

    public Boolean esMarca(String marcaNombre) {
        return this.nombre.equalsIgnoreCase(marcaNombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marca marca)) return false;
        return Objects.equals(id, marca.id) && nombre.equalsIgnoreCase(marca.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
