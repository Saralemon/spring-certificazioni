package it.uniroma3.siw.springcertificazioni.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String nome;

    @NotBlank
    @Size(min = 3, max = 20)
    private String cognome;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.REMOVE)
    private List<Prenotazione> prenotazioni;

    public Utente() {
        this.prenotazioni = new LinkedList<>();
    }

}