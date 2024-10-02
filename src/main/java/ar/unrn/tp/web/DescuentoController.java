package ar.unrn.tp.web;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.dto.output.DescuentoOut;
import ar.unrn.tp.modelo.DescuentoMarca;
import ar.unrn.tp.modelo.DescuentoTarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/descuento")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentos;

    @GetMapping
    public List<DescuentoOut> listALl(){
        return descuentos.listAllDescuentos().stream().map(DescuentoOut::fromModel).toList();
    }

    @GetMapping("/tarjeta")
    public List<DescuentoTarjeta> listTarjeta(){
        return descuentos.listAllDescuentos().stream().filter(x -> x instanceof DescuentoTarjeta).map(x -> (DescuentoTarjeta) x).toList();
    }

    @GetMapping("/marca")
    public List<DescuentoMarca> listMarca(){
        return descuentos.listAllDescuentos().stream().filter(x -> x instanceof DescuentoMarca).map(x -> (DescuentoMarca) x).toList();
    }
}
