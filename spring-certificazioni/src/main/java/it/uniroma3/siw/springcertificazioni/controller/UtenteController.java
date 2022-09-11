package it.uniroma3.siw.springcertificazioni.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.springcertificazioni.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.springcertificazioni.controller.validator.UtenteValidator;
import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Ruolo;
import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import it.uniroma3.siw.springcertificazioni.service.UtenteService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteValidator utenteValidator;
    private final CredenzialiValidator credenzialiValidator;
    private final CredenzialiService credenzialiService;
    private final UtenteService utenteService;

    @GetMapping("/admin/utenti/new")
    public String getRegistrazioneForm(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("credenziali", new Credenziali());
        return "admin/registraUtenteForm";
    }

    @GetMapping("/admin/utenti")
    public String getStudenti(
            @RequestParam(value = "cancellazione", required = false) Boolean cancellazione,
            @RequestParam(value = "immatricolato", required = false) Boolean immatricolato,
            Model model) {
        List<Utente> studenti = this.utenteService.getStudenti();
        model.addAttribute("cancellazione", cancellazione);
        model.addAttribute("immatricolato", immatricolato);
        model.addAttribute("utenti", studenti);
        return "admin/utenti";
    }

    @GetMapping("/admin/utenti/{id}")
    public String getStudente(
            @RequestParam(value = "modifica", required = false) Boolean modifica,
            @PathVariable Long id,
            Model model) {
        Utente utente = this.utenteService.getStudente(id);
        model.addAttribute("utente", utente);
        model.addAttribute("modifica", modifica);
        return "utente";
    }

    @GetMapping("/admin/utenti/{id}/modify")
    public String modificaStudente(@PathVariable Long id, Model model) {
        model.addAttribute("utente", this.utenteService.getStudente(id));
        return "admin/modificaUtenteForm";
    }

    @GetMapping("/admin/utenti/{id}/delete")
    public String cancellaStudente(@PathVariable Long id, Model model) {
        Utente utente = utenteService.getStudente(id);
        Credenziali credenziali = credenzialiService.getCredenziali(utente);
        credenzialiService.cancellaCredenziali(credenziali.getId());
        return "redirect:/admin/utenti";
    }

    @PostMapping("/register")
    public String registaUtente(
            @Valid @ModelAttribute("utente") Utente utente,
            BindingResult utenteBindingResult,
            @Valid @ModelAttribute("credenziali") Credenziali credenziali,
            BindingResult credenzialBindingResult,
            Model model) {
        this.utenteValidator.validate(utente, utenteBindingResult);
        this.credenzialiValidator.validate(credenziali, credenzialBindingResult);

        if (!utenteBindingResult.hasErrors() && !credenzialBindingResult.hasErrors()) {
            credenziali.setRuolo(Ruolo.STUDENTE);
            credenziali.setUtente(utente);
            this.credenzialiService.salvaCredenziali(credenziali);
            return "admin/esitoRegistrazione";
        }
        return "admin/registraUtenteForm";
    }

    @PostMapping("/modify")
    public String modificaUtente(
            @Valid @ModelAttribute("utente") Utente utente,
            BindingResult utenteBindingResult,
            Model model) {
        this.utenteValidator.validate(utente, utenteBindingResult);

        if (!utenteBindingResult.hasErrors()) {
            this.utenteService.salvaStudente(utente);
            return "redirect:/admin/utenti";
        }
        return "admin/modificaUtenteForm";
    }
}