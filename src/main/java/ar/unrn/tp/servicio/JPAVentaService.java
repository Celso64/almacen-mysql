package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.servicio.utils.DescuentosManager;
import ar.unrn.tp.servicio.utils.EntityUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Objects;

@Service
public class JPAVentaService implements VentaService {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final DescuentoService descuentoService;
    private final EntityUtil<Venta> ventas;

    public JPAVentaService(@NonNull ClienteService clienteService, @NonNull ProductoService productoService, @NonNull DescuentoService descuentoService, @NonNull EntityUtil<Venta> ventas) {
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.descuentoService = descuentoService;
        this.ventas = ventas;
    }

 /*   @Override
    public void realizarVenta(List<Long> productos, Long idTarjeta) {
        ventas.ejecutarTransaccion((em) -> {
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

            Venta nuevaVenta = new Venta(cliente, listaProductos, total);
            em.persist(nuevaVenta);

        });
    }*/

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
        return ventas.ejecutarQuery((em) -> {
            TypedQuery<Venta> ventaQuery = em.createQuery("select v from Venta v", Venta.class);
            List<Venta> ventaList = ventaQuery.getResultList();
            ventaList.forEach(x -> x.getCliente().toString());
            ventaList.forEach(x -> x.getProductos().toString());
            return ventaList;
        });
    }
}
