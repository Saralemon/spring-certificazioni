package it.uniroma3.siw.springcertificazioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.repository.UtenteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Utente getStudente(Long id) {
        Optional<Utente> result = this.utenteRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Utente salvaStudente(Utente user) {
        return this.utenteRepository.save(user);
    }

    @Transactional
    public void cancellaStudente(Long id) {
        this.utenteRepository.deleteById(id);
    }

    public List<Utente> getStudenti() {
        List<Utente> result = new ArrayList<>();
        Iterable<Utente> iterable = this.utenteRepository.findAll();
        for (Utente user : iterable)
            result.add(user);
        return result;
    }

    public boolean esisteStudenteNome(Long id, String nome) {
        return this.utenteRepository.existsByIdNotAndNome(id, nome);
    }

    public boolean esisteStudenteCognome(Long id, String cognome) {
        return this.utenteRepository.existsByIdNotAndCognome(id, cognome);
    }
}