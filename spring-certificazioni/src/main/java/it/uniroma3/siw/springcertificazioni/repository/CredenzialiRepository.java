package it.uniroma3.siw.springcertificazioni.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Utente;

@Repository
public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
	
	public Optional<Credenziali> findByUsername(String username);
	public Optional<Credenziali> findByUtente(Utente utente);
}
