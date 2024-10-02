package ar.unrn.tp.web;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.dto.input.ProductoInput;
import ar.unrn.tp.dto.output.ProductoOut;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productos;

    @GetMapping
    public List<ProductoOut> listAll(){
        return productos.listarProductos().stream().map(ProductoOut::fromModel).toList();
    }

    @PostMapping
    public Boolean addProducto(@RequestBody ProductoInput p){
        try{
            Marca m = productos.crearMarcaSiNoExiste(p.marca());
            Categoria c = productos.crearCategoriaSiNoExiste(p.categoria());
            productos.crearProducto(p.nombre(), p.descripcion(), p.precio(), c.getId(), m.getId());
        }catch (Exception e){
            log.warn(e.getMessage());
            log.warn(Arrays.stream(e.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n")));
            return false;
        }
        return true;
    }
}