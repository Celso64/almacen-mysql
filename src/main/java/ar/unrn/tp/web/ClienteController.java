package ar.unrn.tp.web;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.dto.output.ClienteOut;
import ar.unrn.tp.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clientes;

    @GetMapping
    public List<Cliente> listAll(){
        return clientes.listarClientes();
    }

    @GetMapping("/{id}")
    public ClienteOut findByID(@PathVariable("id") Long id){
        System.out.println("ID: " + id);
        return ClienteOut.fromModel(clientes.buscarCliente(id));
    }
}
