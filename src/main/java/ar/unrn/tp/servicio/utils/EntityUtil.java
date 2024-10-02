package ar.unrn.tp.servicio.utils;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityUtil<T> {
    /*
     * ObjectDB: jpa-objectdb
     * PosgresSQL: jpa-postgres
     */
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void ejecutarTransaccion(Consumer<EntityManager> bloqueDeCodigo) {
        bloqueDeCodigo.accept(entityManager);
    }

    @Transactional
    public List<T> ejecutarQuery(Function<EntityManager, List<T>> bloqueDeCodigo) {
        return bloqueDeCodigo.apply(entityManager);
    }

    @Transactional
    public T ejecutarIndividualQuery(Function<EntityManager, T> bloqueDeCodigo) {
        return bloqueDeCodigo.apply(entityManager);
    }

}
