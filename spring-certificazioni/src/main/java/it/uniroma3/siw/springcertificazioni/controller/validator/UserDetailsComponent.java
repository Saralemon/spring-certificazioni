package it.uniroma3.siw.springcertificazioni.controller.validator;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsComponent {

    private final CredenzialiService credenzialiService;

    public Credenziali getCredenzialiAutenticate() {
        UserDetails userDetails = (UserDetails) getContext().getAuthentication().getPrincipal();
        return this.credenzialiService.getCredenziali(userDetails.getUsername());
    }

    public Utente getUtenteAutenticato() {
        UserDetails userDetails = (UserDetails) getContext().getAuthentication().getPrincipal();
        Credenziali credenziali = this.credenzialiService.getCredenziali(userDetails.getUsername());
        return credenziali.getUtente();
    }

}
