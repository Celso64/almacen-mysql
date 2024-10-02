package ar.unrn.tp;
import java.util.HashMap;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.servicio.JPAClienteService;
import ar.unrn.tp.servicio.JPADescuentoService;
import ar.unrn.tp.servicio.JPAProductoService;
import ar.unrn.tp.servicio.JPAVentaService;
import ar.unrn.tp.servicio.utils.EntityUtil;

import java.time.LocalDate;
import java.util.List;

public class PrimerEjecucion {

    public static void main(String[] args) {
       /* ClienteService clientes = new JPAClienteService(new EntityUtil<>(), new EntityUtil<>());
        ProductoService productoService = new JPAProductoService(new EntityUtil<>(), new EntityUtil<>(), new EntityUtil<>());
        DescuentoService descuentoService = new JPADescuentoService(new EntityUtil<>(), new EntityUtil<>(), new EntityUtil<>(), new EntityUtil<>());
        VentaService ventaService = new JPAVentaService(clientes, productoService, descuentoService, new EntityUtil<>());

        clientes.crearCliente("Juan", "Perez", "56412320", "juan@gmail.com");
        clientes.agregarTarjeta(1L, "545 342 453 653", "NARANJA", 1_250_000.0);

        productoService.crearCategoria("Ropa");
        productoService.crearMarca("Nike");

        descuentoService.crearDescuentoSobreTotal("NARANJA",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 11, 1),
                0.1f);

        descuentoService.crearDescuento("NIKE", LocalDate.of(2024, 6, 1), LocalDate.of(2025, 1, 20), 0.15f);

        List<Marca> marcas = productoService.listMarcas();
        List<Categoria> categorias = productoService.listCategorias();

        Long marcaID = marcas.get(0).getId();
        Long categoriaID = categorias.get(0).getId();

        productoService.crearProducto("Botines", "Botines de Futbol talla 44", 1250.0f, categoriaID, marcaID);


        var c = clientes.listarClientes();
        var p = productoService.listarProductos();
        descuentoService.listAllDescuentos().forEach(System.out::println);

        descuentoService.listarPorMarcas("NIKE", "NARANJA");

//        ventaService.realizarVenta(c.get(0).getId(), List.of(p.get(0).getId()), 2L);

        ventaService.ventas().forEach(System.out::println);*/
    }
}
