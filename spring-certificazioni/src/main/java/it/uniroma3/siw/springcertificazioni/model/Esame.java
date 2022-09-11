package it.uniroma3.siw.springcertificazioni.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Esame {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Positive
    private Integer durata;

    @NotBlank
    private String aula;

    @Future
    @NotNull
    private LocalDateTime data;

    @ManyToOne
    private Certificazione certificazione;

    public Esame(String aula, LocalDateTime data, Integer durata) {
        this.aula = aula;
        this.data = data;
        this.durata = durata;
    }
}
