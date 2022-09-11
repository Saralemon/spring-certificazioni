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

import lombok.Data;

@Data
@Entity
public class Esame {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String nome;

    @NotBlank
    private String aula;

    @Future
    @NotNull
    private LocalDateTime data;

    @ManyToOne
    private Certificazione certificazione;

    public Esame(String nome, String aula, LocalDateTime data) {
        this.nome = nome;
        this.aula = aula;
        this.data = data;
    }
}