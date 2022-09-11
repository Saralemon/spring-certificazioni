package it.uniroma3.siw.springcertificazioni.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Ruolo;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AutenticazioneController {
	
	private final CredenzialiService credenzialiService;

    @GetMapping("/logout")
	public String logout() {
		return "index";
	}
	
    @GetMapping("/default")
    public String defaultAfterLogin() {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziali credenziali = credenzialiService.getCredentials(userDetails.getUsername());
    	if (credenziali.getRuolo().equals(Ruolo.SEGRETERIA)) {
            return "admin/home";
        }
        return "home";
    }
	
}

    

