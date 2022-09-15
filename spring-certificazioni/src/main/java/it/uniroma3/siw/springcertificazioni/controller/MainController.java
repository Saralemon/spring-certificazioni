package it.uniroma3.siw.springcertificazioni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.springcertificazioni.controller.validator.UserDetailsComponent;
import it.uniroma3.siw.springcertificazioni.model.Utente;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final UserDetailsComponent userDetailsComponent;

	@GetMapping(value = { "/", "index" })
	public String index(Model model, @RequestParam(value = "error", required = false) boolean error) {
		model.addAttribute("error", error);
		return "index";
	}

	@GetMapping("/profilo")
	public String profilo(Model model) {
		Utente utente = this.userDetailsComponent.getUtenteAutenticato();
		model.addAttribute("utente", utente);
		return "profilo";
	}

}