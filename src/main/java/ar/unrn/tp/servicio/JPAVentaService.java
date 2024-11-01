package ar.unrn.tp.servicio;

import ar.unrn.tp.api.*;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.servicio.utils.DescuentosManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log
public class JPAVentaService implements VentaService {

    final ClienteService clienteService;
    final ProductoService productoService;
    final DescuentoService descuentoService;
    final ContadorService contadorService;

    final JedisPool jedisPool;
    final ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager em;

    @Transactional
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
            DescuentoTarjeta descuentoTarjeta = descuentos.getDescuentoTarjeta(cliente.getTarjeta(idTarjeta).orElse(new Tarjeta("NULL")).getMarca().getNombre());

            Double precio = (Objects.isNull(descuentoMarca)) ? p.getPrecio() : descuentoMarca.calcularDescuento(p);
            precio = (Objects.isNull(descuentoTarjeta)) ? precio : descuentoTarjeta.calcularDescuento(p);

            listaProductos.put(p, precio);
            total += precio;
        }

        Venta nuevaVenta = new Venta(cliente, listaProductos, total, contadorService.getContador());
        em.persist(nuevaVenta);

    }

    @Transactional
    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        Tarjeta tarjeta = clienteService.buscarTarjeta(idTarjeta);

        List<Producto> productoList = productoService.buscarProductos(productos);
        DescuentosManager descuentos = new DescuentosManager(descuentoService.listAllDescuentos());


        float total = 0.0f;
        for (Producto p : productoList) {
            DescuentoMarca descuentoMarca = descuentos.getDescuentoMarca(p.getMarca().getNombre());
            DescuentoTarjeta descuentoTarjeta = descuentos.getDescuentoTarjeta(tarjeta.getMarca().getNombre());

            Double precio = (Objects.isNull(descuentoMarca)) ? p.getPrecio() : descuentoMarca.calcularDescuento(p.nombreMarca(), p.getPrecio());
            precio = (Objects.isNull(descuentoTarjeta)) ? precio : descuentoTarjeta.calcularDescuento(tarjeta.getMarca().getNombre().toUpperCase(), precio);

            total += precio;
        }

        return total;
    }

    @Transactional
    @Override
    public List<Venta> ventas() throws JsonProcessingException {
        Jedis jedis = jedisPool.getResource();
        String ventasR = jedis.get("historial_ventas");
        List<Venta> ventas = objectMapper.readValue(ventasR, objectMapper.getTypeFactory().constructCollectionType(List.class, Venta.class));

        if (ventas.isEmpty()) {
            TypedQuery<Venta> ventaQuery = em.createQuery("select v from Venta v", Venta.class);
            List<Venta> ventaList = ventaQuery.getResultList();

            ventaList.forEach(x -> x.getCliente().toString());
            ventaList.forEach(x -> x.getProductos().toString());

            String ventasJSON = objectMapper.writeValueAsString(ventaList);
            jedis.set("historial_ventas", ventasJSON);

            return ventaList;
        }

        jedis.close();
        return ventas;
    }

    @Transactional
    @Override
    public List<Venta> ventasPorIDCliente(Long idCliente, Integer cantidad) throws JsonProcessingException {

        final String redisName = "historial_ventas_%d".formatted(idCliente);

        Jedis jedis = jedisPool.getResource();

        String ventasR = jedis.get(redisName);
        List<Venta> ventas = objectMapper.readValue(ventasR, objectMapper.getTypeFactory().constructCollectionType(List.class, Venta.class));

        if (ventas.isEmpty()) {
            TypedQuery<Venta> ventaQuery = em.createQuery("SELECT v FROM Venta v WHERE v.cliente.id = :idCliente ", Venta.class);
            ventaQuery.setMaxResults(cantidad);
            ventaQuery.setParameter("idCliente", idCliente);

            List<Venta> ventaList = ventaQuery.getResultList();

//            ventaList.forEach(x -> x.getCliente().toString());
//            ventaList.forEach(x -> x.getProductos().toString());

            String ventasJSON = objectMapper.writeValueAsString(ventaList);
            jedis.set(redisName, ventasJSON);
            jedis.expire(redisName, Duration.ofSeconds(10).toMillis());
            log.info("SE USO REDIS");

            return ventaList;
        }
        jedis.close();
        return ventas;
    }
}
