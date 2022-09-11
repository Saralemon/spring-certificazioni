package it.uniroma3.siw.springcertificazioni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Esame;

@Repository
public interface EsameRepository extends CrudRepository<Esame, Long> {

}
