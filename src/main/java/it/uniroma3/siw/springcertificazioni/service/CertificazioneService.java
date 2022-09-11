package it.uniroma3.siw.springcertificazioni.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.repository.CertificazioneRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificazioneService {

    private final CertificazioneRepository certificazioneRepository;

    @Transactional
    public Certificazione salvaConcerto(Certificazione certificazione) {
        return this.certificazioneRepository.save(certificazione);
    }

    @Transactional
    public void cancellaConcerto(Long id) {
        this.certificazioneRepository.deleteById(id);
    }

    public List<Certificazione> getCertificazione() {
        return stream(this.certificazioneRepository.findAll().spliterator(), false).collect(toList());
    }
}

