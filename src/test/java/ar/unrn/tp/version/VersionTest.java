package ar.unrn.tp.version;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Producto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class VersionTest {

    @Autowired
    private ProductoService productoService;

    @Test
    public void probarVersion() {

        Double precio1 = 132_465.0;
        Double precio2 = 64_754.5;

        Producto producto = productoService.listarProductos().get(0);
        log.info("VERSION EN EL FRONT: {}", producto.getVersion());

        float valor = (producto.getPrecio().equals(precio1)) ? precio2.floatValue() : precio1.floatValue();

        productoService.modificarProducto(producto.getId(), producto.getCodigo(), producto.getDescripcion(),
                valor, producto.getCategoria().getId(), producto.getMarca().getId(), producto.getVersion());
    }
}
