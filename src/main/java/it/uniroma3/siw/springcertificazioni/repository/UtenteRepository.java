package it.uniroma3.siw.springcertificazioni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {

}
