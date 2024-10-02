package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JPAProductoService implements ProductoService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public void crearMarca(String nombre) {
        Marca nuevaMarca = new Marca(nombre);
        em.persist(nuevaMarca);
        log.info("CREATE Marca {}", nombre);
    }

    @Transactional
    @Override
    public Marca crearMarcaSiNoExiste(String nombre) {
        TypedQuery<Marca> m = em.createQuery("SELECT m FROM Marca m WHERE nombre= :nombre", Marca.class);
        m.setParameter("nombre", nombre);

        var res = m.getResultList();
        if (!res.isEmpty()) {
            log.info("Marca {} existe", res.size());
            return res.get(0);
        } else {
            em.persist(new Marca(nombre));
            TypedQuery<Marca> m2 = em.createQuery("SELECT m FROM Marca m WHERE nombre= :nombre", Marca.class);
            m2.setParameter("nombre", nombre);
            log.info("CREADO {}", res.size());
            return m2.getResultList().get(0);
        }
    }

    @Transactional
    @Override
    public void crearCategoria(String nombre) {
        Categoria nuevaCategoria = new Categoria(nombre);
        em.persist(nuevaCategoria);
        log.info("CREATE Categoria {}", nombre);
    }

    @Transactional
    @Override
    public Categoria crearCategoriaSiNoExiste(String nombre) {
        TypedQuery<Categoria> m = em.createQuery("SELECT c FROM Categoria c WHERE nombre= :nombre", Categoria.class);
        m.setParameter("nombre", nombre);

        var res = m.getResultList();
        if (!res.isEmpty())
            return res.get(0);
        else {
            em.persist(new Marca(nombre));
            TypedQuery<Categoria> m2 = em.createQuery("SELECT c FROM Categoria c WHERE nombre= :nombre", Categoria.class);
            m2.setParameter("nombre", nombre);
            return m2.getResultList().get(0);
        }
    }

    @Transactional
    @Override
    public List<Marca> listMarcas() {
        TypedQuery<Marca> marcaQuery = em.createQuery("select m from Marca m", Marca.class);
        List<Marca> marcas = marcaQuery.getResultList();
        log.info("LIST Marca: {} elementos", marcas.size());
        return marcas;
    }

    @Transactional
    @Override
    public List<Categoria> listCategorias() {
        TypedQuery<Categoria> CategoriaQuery = em.createQuery("select c from Categoria c", Categoria.class);
        List<Categoria> categorias = CategoriaQuery.getResultList();
        log.info("LIST Categoria: {} elementos", categorias.size());
        return categorias;
    }

    @Transactional
    @Override
    public void crearProducto(String codigo, String descripcion, float precio, Long IdCategoria, Long idMarca) {
        Marca marca = em.find(Marca.class, idMarca);
        Categoria categoria = em.find(Categoria.class, IdCategoria);

        Producto nuevoProducto = new Producto(codigo, descripcion, marca, categoria, (double) precio);
        em.persist(nuevoProducto);
        log.info("CREATE Producto {}", codigo);
    }

    @Transactional
    @Override
    public void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long IdCategoria, Long idMarca) {
        Producto producto = em.find(Producto.class, idProducto);
        Marca nuevaMarca = em.find(Marca.class, idMarca);
        Categoria nuevaCategoria = em.find(Categoria.class, IdCategoria);
        producto.update(new Producto(codigo, descripcion, nuevaMarca, nuevaCategoria, (double) precio));
        log.info("UPDATE Producto {}", idProducto);
    }

    @Transactional
    @Override
    public List<Producto> listarProductos() {
        TypedQuery<Producto> productosQuery = em.createQuery("select p from Producto p", Producto.class);
        List<Producto> productos = productosQuery.getResultList();
        log.info("LIST Producto {}", productos.size());
        return productos;
    }

    @Transactional
    @Override
    public List<Producto> buscarProductos(List<Long> idsProductos) {
        TypedQuery<Producto> productosQuery = em.createQuery("select p from Producto p where p.id in :ids", Producto.class);
        productosQuery.setParameter("ids", idsProductos);
        List<Producto> productos = productosQuery.getResultList();
        log.info("LIST SOME Producto {}", productos);
        return productos;
    }
}
