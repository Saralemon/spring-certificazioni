package it.uniroma3.siw.springcertificazioni.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Certificazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min=3, max=100)
    private String nome;

    @NotBlank
    private String dipartimento;

    @OneToMany(mappedBy = "certificazione")
    private List<Esame> esami;

    public Certificazione() {
        this.esami = new LinkedList<>();
    }

    public Certificazione(String nome, String dipartimento) {
        this();
        this.nome = nome;
        this.dipartimento = dipartimento;
    }

}

