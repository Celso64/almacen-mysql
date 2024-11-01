package ar.unrn.tp.contador;

import ar.unrn.tp.api.ContadorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ContadorTest {

    @Autowired
    private ContadorService contadorService;

    @Test
    public void pedirContador() {
        String espero = "1-2024";
        String recibo = contadorService.getContador();

        assertEquals(espero, recibo);
    }
}
