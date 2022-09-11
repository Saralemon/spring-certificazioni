package it.uniroma3.siw.springcertificazioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.repository.UtenteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public Utente getUser(Long id) {
        Optional<Utente> result = this.utenteRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public Utente saveUser(Utente user) {
        return this.utenteRepository.save(user);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<Utente> getAllUsers() {
        List<Utente> result = new ArrayList<>();
        Iterable<Utente> iterable = this.utenteRepository.findAll();
        for(Utente user : iterable)
            result.add(user);
        return result;
    }
}