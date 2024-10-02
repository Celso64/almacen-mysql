package ar.unrn.tp.servicio;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.servicio.utils.EntityUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JPADescuentoService implements DescuentoService {

    private final EntityUtil<Descuento> descuentos;
    private final EntityUtil<DescuentoTarjeta> descuentosTarjetas;
    private final EntityUtil<DescuentoMarca> descuentosMarcas;
    private final EntityUtil<Producto> productos;

    public JPADescuentoService(@NonNull EntityUtil<Descuento> descuentos, @NonNull EntityUtil<DescuentoTarjeta> descuentosTarjetas, @NonNull EntityUtil<DescuentoMarca> descuentosMarcas, @NonNull EntityUtil<Producto> productos) {
        this.descuentos = descuentos;
        this.descuentosTarjetas = descuentosTarjetas;
        this.descuentosMarcas = descuentosMarcas;
        this.productos = productos;
    }

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        descuentosTarjetas.ejecutarTransaccion((em) -> {
            TypedQuery<Tarjeta> tarjetaQuery = em.createQuery("select t from Tarjeta t where t.marca = :marca", Tarjeta.class);
            tarjetaQuery.setParameter("marca", marcaTarjeta);
            Tarjeta tarjeta = tarjetaQuery.getSingleResult();
            DescuentoTarjeta nuevoDescuento = new DescuentoTarjeta(fechaDesde, fechaHasta, (double) porcentaje, tarjeta);
            em.persist(nuevoDescuento);
        });
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        descuentosMarcas.ejecutarTransaccion((em) -> {
            TypedQuery<Marca> marcaQuery = em.createQuery("select m from Marca m where m.nombre= :nombre", Marca.class);
            marcaQuery.setParameter("nombre", marcaProducto.toUpperCase());
            Marca marca = marcaQuery.getSingleResult();
            DescuentoMarca nuevoDescuento = new DescuentoMarca(fechaDesde, fechaHasta, marca, (double) porcentaje);
            em.persist(nuevoDescuento);
        });
    }

    @Override
    public List<Descuento> listAllDescuentos() {
        return descuentos.ejecutarQuery((em) -> {
            TypedQuery<Descuento> descuentoQuery = em.createQuery("select d from Descuento d", Descuento.class);
            return descuentoQuery.getResultList();
        });
    }

    @Override
    public List<Descuento> listarPorMarcas(String marcaProducto, String marcaTarjeta) {
        return descuentos.ejecutarQuery((em) -> {
            TypedQuery<DescuentoTarjeta> descuentoTQuery = em.createQuery("SELECT d FROM DescuentoTarjeta d where :marca IN d.tarjeta.id", DescuentoTarjeta.class);
            descuentoTQuery.setParameter("marca", marcaTarjeta);

            TypedQuery<DescuentoMarca> descuentoMQuery = em.createQuery("SELECT d FROM DescuentoMarca d where d.marca.nombre = :marca", DescuentoMarca.class);
            descuentoMQuery.setParameter("marca", marcaProducto.toUpperCase());

            List<Descuento> descuentoList = new ArrayList<>();
            descuentoList.addAll(descuentoTQuery.getResultList());
            descuentoList.addAll(descuentoMQuery.getResultList());

            return descuentoList;
        });
    }

    @Override
    public Double calcularDescuentoMarca(Long idProducto, String marca) {
        Producto p = productos.ejecutarIndividualQuery((em) -> em.find(Producto.class, idProducto));
        DescuentoMarca dm = descuentosMarcas.ejecutarIndividualQuery((em) -> {
            TypedQuery<DescuentoMarca> marcaQuery = em.createQuery("SELECT d FROM DescuentoMarca d where d.marca.nombre = :marca", DescuentoMarca.class);
            marcaQuery.setParameter("marca", marca.toUpperCase());
            return marcaQuery.getResultList().stream().findFirst().get();
        });
        return dm.calcularDescuento(p);
    }

    @Override
    public Double calcularDescuentoTarjeta(Long idProducto, Long idTarjeta) {
        Producto p = productos.ejecutarIndividualQuery((em) -> em.find(Producto.class, idProducto));
        DescuentoTarjeta dm = descuentosTarjetas.ejecutarIndividualQuery((em) -> {
            TypedQuery<DescuentoTarjeta> tarjetaQuery = em.createQuery(
                    "SELECT dt " +
                            "FROM DescuentoTarjeta dt " +
                            "WHERE dt.tarjeta.id = :idTarjeta"
                    , DescuentoTarjeta.class);
            tarjetaQuery.setParameter("idTarjeta", idTarjeta);
            return tarjetaQuery.getResultList().stream().findFirst().get();
        });
        return dm.calcularDescuento(p);
    }
}
