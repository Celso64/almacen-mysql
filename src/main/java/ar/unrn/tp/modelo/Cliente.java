package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.util.Dni;
import ar.unrn.tp.modelo.util.Email;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre, apellido, email, dni;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Tarjeta> tarjetas;

    public Cliente(String nombre, String apellido, String email, String dni) {
        this.nombre = Objects.requireNonNull(nombre);
        this.apellido = Objects.requireNonNull(apellido);
        this.email = new Email(email).toString();
        this.dni = new Dni(dni).toString();
        this.tarjetas = new HashSet<>();
    }

    public Cliente(String nombre, String apellido, String email, String dni, Set<Tarjeta> tarjetas) {
        this(nombre, apellido, email, dni);
        this.tarjetas.addAll(tarjetas);
    }

    public Cliente(Long id) {
        this.id = id;
        this.tarjetas = null;
    }

    public void agregarTarjeta(Tarjeta tarjeta) {
        this.tarjetas.add(tarjeta);
    }

    public void eliminarTarjeta(Tarjeta tarjeta) {
        this.tarjetas.remove(tarjeta);
    }


    public Boolean mismoDNI(String dni) {
        return this.dni.equals(dni);
    }

    public void update(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = new Email(email).toString();
    }

    public Optional<Tarjeta> getTarjeta(Long idTarjeta) {
        return this.tarjetas.stream().filter(t -> t.tieneID(idTarjeta)).findFirst();
    }
}
