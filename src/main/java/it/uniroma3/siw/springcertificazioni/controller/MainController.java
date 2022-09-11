package it.uniroma3.siw.springcertificazioni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.repository.CredenzialiRepository;

@Controller
public class MainController {

	@RequestMapping(value = { "/", "index" }, method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/crea")
	public String admin() {
		Utente utente = new Utente();
		utente.setNome("nome");
		utente.setCognome("cognome");
		Credenziali credenziali = new Credenziali();
		credenziali.setUsername("admin");
		credenziali.setPassword(passwordEncoder.encode("password"));
		credenziali.setUtente(utente);
		credenzialiRepository.save(credenziali);
		return "index";
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CredenzialiRepository credenzialiRepository;
}
