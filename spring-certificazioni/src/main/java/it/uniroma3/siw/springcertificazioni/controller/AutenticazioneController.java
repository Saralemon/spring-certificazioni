package it.uniroma3.siw.springcertificazioni.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Ruolo;
import it.uniroma3.siw.springcertificazioni.service.CertificazioneService;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AutenticazioneController {

    private final CredenzialiService credenzialiService;
    private final CertificazioneService certificazioneService;

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
        if (credenziali.getRuolo().equals(Ruolo.SEGRETERIA)) {
            return "admin/home";
        }
        List<Certificazione> certificazioni = this.certificazioneService.getCertificazioni();
        model.addAttribute("certificazioni", certificazioni);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "index";
    }

}