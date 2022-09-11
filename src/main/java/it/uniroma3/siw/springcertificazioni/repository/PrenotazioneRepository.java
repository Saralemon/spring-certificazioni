package it.uniroma3.siw.springcertificazioni.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Prenotazione;
import it.uniroma3.siw.springcertificazioni.model.Utente;


@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

    public List<Prenotazione> findByUtente(Utente utente);
    
}
