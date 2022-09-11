package it.uniroma3.siw.springcertificazioni.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Prenotazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime data;

    @ManyToOne
    private Utente utente;

    @OneToOne(cascade = CascadeType.MERGE)
    private Esame esame;

    public Prenotazione(Esame esame, Utente utente) {
        this.esame = esame;
        this.utente = utente;
    }
    

}
