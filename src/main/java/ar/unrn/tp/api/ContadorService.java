package ar.unrn.tp.api;

public interface ContadorService {

    /**
     * Recupera el contador del año actual, sino existe, lo crea e inicia en 1.
     *
     * @return El contador.
     */
    String getContador();
}
