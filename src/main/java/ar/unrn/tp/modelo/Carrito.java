package ar.unrn.tp.modelo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Carrito {

    private final Set<Producto> productos;
    private final Cliente cliente;

    public Carrito(Cliente cliente) {
        this.productos = new HashSet<>();
        this.cliente = cliente;
    }

    public void agregarProducto(Producto producto){
        this.productos.add(producto);
    }

    public void quitarProducto(Producto producto){
        this.productos.remove(producto);
    }

    public void quitarProducto(Long idProducto){
        //this.productos.removeIf(x -> x.mismoCodigo(idProducto));
    }

    public Collection<Producto> getProductos(){
        return Collections.unmodifiableCollection(this.productos);
    }

    public Double liquidar(){
        return 0.0;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
