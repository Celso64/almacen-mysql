package ar.unrn.tp.servicio;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JPADescuentoService implements DescuentoService {

    @PersistenceContext
    EntityManager em;


    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        TypedQuery<Tarjeta> tarjetaQuery = em.createQuery("select t from Tarjeta t where t.marca = :marca", Tarjeta.class);
        tarjetaQuery.setParameter("marca", marcaTarjeta);
        Tarjeta tarjeta = tarjetaQuery.getSingleResult();
        DescuentoTarjeta nuevoDescuento = new DescuentoTarjeta(fechaDesde, fechaHasta, (double) porcentaje, tarjeta);
        em.persist(nuevoDescuento);
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        TypedQuery<Marca> marcaQuery = em.createQuery("select m from Marca m where m.nombre= :nombre", Marca.class);
        marcaQuery.setParameter("nombre", marcaProducto.toUpperCase());
        Marca marca = marcaQuery.getSingleResult();
        DescuentoMarca nuevoDescuento = new DescuentoMarca(fechaDesde, fechaHasta, marca, (double) porcentaje);
        em.persist(nuevoDescuento);
    }

    @Override
    public List<Descuento> listAllDescuentos() {
        TypedQuery<Descuento> descuentoQuery = em.createQuery("select d from Descuento d", Descuento.class);
        return descuentoQuery.getResultList();
    }

    @Override
    public List<Descuento> listarPorMarcas(String marcaProducto, String marcaTarjeta) {
        TypedQuery<DescuentoTarjeta> descuentoTQuery = em.createQuery("SELECT d FROM DescuentoTarjeta d where :marca IN d.tarjeta.id", DescuentoTarjeta.class);
        descuentoTQuery.setParameter("marca", marcaTarjeta);

        TypedQuery<DescuentoMarca> descuentoMQuery = em.createQuery("SELECT d FROM DescuentoMarca d where d.marca.nombre = :marca", DescuentoMarca.class);
        descuentoMQuery.setParameter("marca", marcaProducto.toUpperCase());

        List<Descuento> descuentoList = new ArrayList<>();
        descuentoList.addAll(descuentoTQuery.getResultList());
        descuentoList.addAll(descuentoMQuery.getResultList());

        return descuentoList;
    }

    @Override
    public Double calcularDescuentoMarca(Long idProducto, String marca) {
        Producto p = em.find(Producto.class, idProducto);
        TypedQuery<DescuentoMarca> marcaQuery = em.createQuery("SELECT d FROM DescuentoMarca d where d.marca.nombre = :marca", DescuentoMarca.class);
        marcaQuery.setParameter("marca", marca.toUpperCase());
        DescuentoMarca dm = marcaQuery.getResultList().stream().findFirst().get();
        return dm.calcularDescuento(p);
    }

    @Override
    public Double calcularDescuentoTarjeta(Long idProducto, Long idTarjeta) {
        Producto p = em.find(Producto.class, idProducto);
        TypedQuery<DescuentoTarjeta> tarjetaQuery = em.createQuery(
                "SELECT dt " +
                        "FROM DescuentoTarjeta dt " +
                        "WHERE dt.tarjeta.id = :idTarjeta"
                , DescuentoTarjeta.class);
        tarjetaQuery.setParameter("idTarjeta", idTarjeta);
        DescuentoTarjeta dm = tarjetaQuery.getResultList().stream().findFirst().get();
        return dm.calcularDescuento(p);
    }
}
