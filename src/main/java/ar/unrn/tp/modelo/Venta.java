package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "venta")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime fecha;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    Cliente cliente;

    @ElementCollection
    @CollectionTable(name = "venta_producto_precio",
            joinColumns = @JoinColumn(name = "venta_id"))
    @MapKeyJoinColumn(name = "producto_id") // Clave referida a Producto
    @Column(name = "precio") // Valor escalar Double
    Map<Producto, Double> productos = new HashMap<>();

    Double montoTotal;

    @Column(name = "factura", unique = true)
    String factura;

    public Venta(Cliente cliente, Map<Producto, Double> productos, Double montoTotal, String factura) {
        this.fecha = LocalDateTime.now();
        this.cliente = cliente;
        this.productos = productos;
        this.montoTotal = montoTotal;
        this.factura = factura;
    }

}
