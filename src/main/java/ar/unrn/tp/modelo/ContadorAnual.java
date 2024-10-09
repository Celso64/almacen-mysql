package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "contador", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"anio", "contador"})
})
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContadorAnual {

    @Transient
    static final String NRO_FACTURA_FORMATO = "%d-%d";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer anio, contador;

    public ContadorAnual(Integer anio) {
        this.anio = anio;
        this.contador = 1;
    }

    public String getNumeroFactura() {
        return NRO_FACTURA_FORMATO.formatted(contador++, anio);
    }
}
