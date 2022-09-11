package it.uniroma3.siw.springcertificazioni.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.repository.EsameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EsameService {

    private final EsameRepository esameRepository;

    @Transactional
    public Esame salvaBiglietto(Esame biglietto) {
        return this.esameRepository.save(biglietto);
    }

    public Esame getBiglietto(Long id) {
        return this.esameRepository.findById(id).get();
    }
    
}