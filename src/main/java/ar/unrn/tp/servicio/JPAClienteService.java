package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JPAClienteService implements ClienteService {

    @PersistenceContext
    EntityManager em;

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        var cliente = new Cliente(nombre, apellido, email, dni);
        em.persist(cliente);
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        var cliente = em.find(Cliente.class, idCliente);
        cliente.update(nombre, apellido, email);
        em.persist(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        TypedQuery<Cliente> clientes = em.createQuery(
                "select c from Cliente c",
                Cliente.class);
        clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
        return clientes.getResultList();
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca, Double fondos) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        cliente.agregarTarjeta(new Tarjeta(nro, marca, fondos));
    }

    @Override
    public List<Tarjeta> listarTarjetas(Long idCliente) {
        TypedQuery<Tarjeta> tarjetas = em.createQuery(
                "select t from Cliente c join c.tarjetas t where c.id = :id",
                Tarjeta.class);
        tarjetas.setParameter("id", idCliente);
        tarjetas.getResultList().forEach(Object::toString);
        return tarjetas.getResultList();
    }

    @Override
    public Cliente buscarCliente(Long idCliente) {
        TypedQuery<Cliente> clientes = em.createQuery(
                "select c from Cliente c where c.id = :id",
                Cliente.class);
        clientes.setParameter("id", idCliente);
        clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
        return clientes.getSingleResult();
    }

    @Override
    public Cliente buscarClientePorTarjeta(Long idTarjeta) {
        TypedQuery<Cliente> clientes = em.createQuery(
                "select c from Cliente c join c.tarjetas t where t.id = :id",
                Cliente.class);
        clientes.setParameter("id", idTarjeta);
        clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
        return clientes.getSingleResult();
    }

    @Override
    public Tarjeta buscarTarjeta(Long idTarjeta) {
        return em.find(Tarjeta.class, idTarjeta);
    }
}
