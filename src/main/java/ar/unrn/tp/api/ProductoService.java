package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;

import java.util.List;

public interface ProductoService {

    void crearMarca(String nombre);

    Marca crearMarcaSiNoExiste(String nombre);

    void crearCategoria(String nombre);

    Categoria crearCategoriaSiNoExiste(String nombre);

    List<Marca> listMarcas();

    List<Categoria> listCategorias();

    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, float precio, Long IdCategoria, Long idMarca);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long IdCategoría, Long idMarca);

    //Devuelve todos los productos
    List<Producto> listarProductos();

    List<Producto> buscarProductos(List<Long> idsProductos);
}
