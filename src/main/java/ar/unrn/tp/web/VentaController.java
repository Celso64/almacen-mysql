package ar.unrn.tp.web;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Venta;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventas;

    @GetMapping
    public List<Venta> listAll(){
        return ventas.ventas();
    }

    @GetMapping("/calcular")
    public Float calcularMonto(@RequestParam("ids") Long[] ids){
        return ventas.calcularMonto(Arrays.stream(ids).toList(), 2L);
    }

/*    @PostMapping("/vender")
    public ResponseEntity<Boolean> vender(@RequestBody Long[] ids, @RequestBody Long idTarjeta){
        ventas.realizarVenta(Arrays.stream(ids).toList(), idTarjeta);
        return ResponseEntity.ok(true);
    }*/
}
