package ar.unrn.tp.servicio;

import ar.unrn.tp.api.*;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.servicio.utils.DescuentosManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JPAVentaService implements VentaService {

    final ClienteService clienteService;
    final ProductoService productoService;
    final DescuentoService descuentoService;
    final ContadorService contadorService;
    @PersistenceContext
    EntityManager em;

    @Override
    public void realizarVenta(List<Long> productos, Long idTarjeta) {
        Cliente cliente = clienteService.buscarClientePorTarjeta(idTarjeta);
        Tarjeta tarjeta = cliente.getTarjeta(idTarjeta).orElse(null);
        List<Producto> productoList = productoService.buscarProductos(productos);
        DescuentosManager descuentos = new DescuentosManager(descuentoService.listAllDescuentos());

        Map<Producto, Double> listaProductos = new HashMap<>(productoList.size());
        Double total = 0.0;
        for (Producto p : productoList) {
            DescuentoMarca descuentoMarca = descuentos.getDescuentoMarca(p.getMarca().getNombre());
            DescuentoTarjeta descuentoTarjeta = descuentos.getDescuentoTarjeta(cliente.getTarjeta(idTarjeta).orElse(new Tarjeta("NULL")).getMarca());

            Double precio = (Objects.isNull(descuentoMarca)) ? p.getPrecio() : descuentoMarca.calcularDescuento(p);
            precio = (Objects.isNull(descuentoTarjeta)) ? precio : descuentoTarjeta.calcularDescuento(p);

            listaProductos.put(p, precio);
            total += precio;
        }

        Venta nuevaVenta = new Venta(cliente, listaProductos, total, contadorService.getContador());
        em.persist(nuevaVenta);

    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        Tarjeta tarjeta = clienteService.buscarTarjeta(idTarjeta);

        List<Producto> productoList = productoService.buscarProductos(productos);
        DescuentosManager descuentos = new DescuentosManager(descuentoService.listAllDescuentos());


        float total = 0.0f;
        for (Producto p : productoList) {
            DescuentoMarca descuentoMarca = descuentos.getDescuentoMarca(p.getMarca().getNombre());
            DescuentoTarjeta descuentoTarjeta = descuentos.getDescuentoTarjeta(tarjeta.getMarca());

            Double precio = (Objects.isNull(descuentoMarca)) ? p.getPrecio() : descuentoMarca.calcularDescuento(p.nombreMarca(), p.getPrecio());
            precio = (Objects.isNull(descuentoTarjeta)) ? precio : descuentoTarjeta.calcularDescuento(tarjeta.getMarca().toUpperCase(), precio);

            total += precio;
        }

        return total;
    }

    @Override
    public List<Venta> ventas() {
        TypedQuery<Venta> ventaQuery = em.createQuery("select v from Venta v", Venta.class);
        List<Venta> ventaList = ventaQuery.getResultList();
        ventaList.forEach(x -> x.getCliente().toString());
        ventaList.forEach(x -> x.getProductos().toString());
        return ventaList;
    }
}
