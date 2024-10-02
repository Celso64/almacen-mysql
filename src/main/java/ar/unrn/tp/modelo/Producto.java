package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String codigo, descripcion;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    Marca marca;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    Categoria categoria;

    Double precio;

    public Producto(@NonNull String nombre, @NonNull String descripcion, @NonNull Marca marca, @NonNull Categoria categoria, @NonNull Double precio) {
        this.codigo = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
    }

    public void update(Producto productoNuevo) {
        this.codigo = productoNuevo.getCodigo();
        this.descripcion = productoNuevo.getDescripcion();
        this.marca = productoNuevo.getMarca();
        this.categoria = productoNuevo.getCategoria();
        this.precio = productoNuevo.getPrecio();
    }

    public String nombreMarca() {
        return this.marca.getNombre().toUpperCase();
    }
}
