package ar.unrn.tp.dto.output;

import ar.unrn.tp.modelo.Cliente;

import java.util.Set;
import java.util.stream.Collectors;

public record ClienteOut(Long id, String nombre, String apellido, String email, String dni, Set<TarjetaOut> tarjetas) {

    public static ClienteOut fromModel(Cliente c){
        Set<TarjetaOut> tarjetas = c.getTarjetas()
                .stream()
                .map(TarjetaOut::fromModel)
                .collect(Collectors.toSet());

        return new ClienteOut(c.getId(), c.getNombre(), c.getApellido(), c.getEmail(), c.getDni(),  tarjetas);
    }
}
