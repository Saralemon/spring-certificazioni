package it.uniroma3.siw.springcertificazioni.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.springcertificazioni.controller.validator.UserDetailsComponent;
import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.model.Prenotazione;
import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import it.uniroma3.siw.springcertificazioni.service.PrenotazioneService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PrenotazioneController {

    private static final Logger log = LoggerFactory.getLogger(PrenotazioneController.class);

    private final EsameService esameService;
    private final PrenotazioneService prenotazioneService;
    private final UserDetailsComponent userDetailsComponent;

    @GetMapping("/prenotazioni/esami/{id}")
    public String effettuaPrenotazione(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        log.info("Richiesta GET /prenotazione/esame/" + id);
        Esame esame = this.esameService.getEsame(id);
        Utente utente = this.userDetailsComponent.getUtenteAutenticato();
        Prenotazione prenotazione = new Prenotazione(esame, utente);
        this.prenotazioneService.salvaPrenotazione(prenotazione);
        redirectAttributes.addAttribute("prenotazione", true);
        return "redirect:/certificazioni/" + esame.getCertificazione().getId();
    }

    @GetMapping("/prenotazioni/{id}/delete")
    public String annullaPrenotazione(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /prenotazione/" + id + "/delete");
        Prenotazione prenotazione = this.prenotazioneService.getPrenotazione(id);
        Esame esame = prenotazione.getEsame();
        this.esameService.salvaEsame(esame);
        this.prenotazioneService.cancellaPrenotazione(id);
        return "redirect:/profilo";
    }
    
}

