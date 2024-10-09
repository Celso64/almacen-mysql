package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ContadorService;
import ar.unrn.tp.modelo.ContadorAnual;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class JPAContadorService implements ContadorService {

    @PersistenceContext
    EntityManager em;

    @Override
    public String getContador() {
        String res;
        Integer esteAnio = LocalDate.now().getYear();

        TypedQuery<ContadorAnual> contadorQuery = em.createQuery("SELECT c FROM ContadorAnual c WHERE c.anio = :anio", ContadorAnual.class);
        contadorQuery.setParameter("anio", esteAnio);
        contadorQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);

        try {
            ContadorAnual contador = contadorQuery.getSingleResult();
            log.info("Existe contador {}", esteAnio);
            res = contador.getNumeroFactura();
            em.persist(contador);
        } catch (NoResultException e) {
            ContadorAnual contador = new ContadorAnual(esteAnio);
            log.info("Se creo contador {}", esteAnio);
            res = contador.getNumeroFactura();
            em.persist(contador);
        }
        return res;
    }
}
