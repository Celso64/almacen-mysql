package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicio.utils.EntityUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class JPAClienteService implements ClienteService {

    private final EntityUtil<Cliente> clientes;
    private final EntityUtil<Tarjeta> tarjetas;

    public JPAClienteService(@NonNull EntityUtil<Cliente> clientes, @NonNull EntityUtil<Tarjeta> tarjetas) {
        this.clientes = clientes;
        this.tarjetas = tarjetas;
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        clientes.ejecutarTransaccion((em) -> {
            var cliente = new Cliente(nombre, apellido, email, dni);
            em.persist(cliente);
        });
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        clientes.ejecutarTransaccion((em) -> {
            var cliente = em.find(Cliente.class, idCliente);
            cliente.update(nombre, apellido, email);
            em.persist(cliente);
        });
    }

    @Override
    public List<Cliente> listarClientes() {
        return clientes.ejecutarQuery((em -> {
            TypedQuery<Cliente> clientes = em.createQuery(
                    "select c from Cliente c",
                    Cliente.class);
            clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
            return clientes.getResultList();
        }));
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca, Double fondos) {
        clientes.ejecutarTransaccion((em) -> {
            Cliente cliente = em.find(Cliente.class, idCliente);
            cliente.agregarTarjeta(new Tarjeta(nro, marca, fondos));
        });
    }

    @Override
    public List<Tarjeta> listarTarjetas(Long idCliente) {
        return tarjetas.ejecutarQuery((em -> {
            TypedQuery<Tarjeta> tarjetas = em.createQuery(
                    "select t from Cliente c join c.tarjetas t where c.id = :id",
                    Tarjeta.class);
            tarjetas.setParameter("id", idCliente);
            tarjetas.getResultList().forEach(Object::toString);
            return tarjetas.getResultList();
        }));
    }

    @Override
    public Cliente buscarCliente(Long idCliente) {
        return clientes.ejecutarIndividualQuery((em -> {
            TypedQuery<Cliente> clientes = em.createQuery(
                    "select c from Cliente c where c.id = :id",
                    Cliente.class);
            clientes.setParameter("id", idCliente);
            clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
            return clientes.getSingleResult();
        }));
    }

    @Override
    public Cliente buscarClientePorTarjeta(Long idTarjeta) {
        return clientes.ejecutarIndividualQuery((em -> {
            TypedQuery<Cliente> clientes = em.createQuery(
                    "select c from Cliente c join c.tarjetas t where t.id = :id",
                    Cliente.class);
            clientes.setParameter("id", idTarjeta);
            clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
            return clientes.getSingleResult();
        }));
    }

    @Override
    public Tarjeta buscarTarjeta(Long idTarjeta) {
        return tarjetas.ejecutarIndividualQuery((em) -> em.find(Tarjeta.class, idTarjeta));
    }
}
