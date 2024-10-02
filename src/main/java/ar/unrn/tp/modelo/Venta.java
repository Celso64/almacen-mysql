package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ElementCollection
    @CollectionTable(name = "venta_producto_precio",
            joinColumns = @JoinColumn(name = "venta_id"))
    @MapKeyJoinColumn(name = "producto_id") // Clave referida a Producto
    @Column(name = "precio") // Valor escalar Double
    private Map<Producto, Double> productos = new HashMap<>();

    private Double montoTotal;

    public Venta(Cliente cliente, Map<Producto, Double> productos, Double montoTotal) {
        this.fecha = LocalDateTime.now();
        this.cliente = cliente;
        this.productos = productos;
        this.montoTotal = montoTotal;
    }

}
