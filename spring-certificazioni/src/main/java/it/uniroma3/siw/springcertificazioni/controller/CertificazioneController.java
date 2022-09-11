package it.uniroma3.siw.springcertificazioni.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.springcertificazioni.controller.validator.EsameValidator;
import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.service.CertificazioneService;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CertificazioneController {

    private static final Logger log = LoggerFactory.getLogger(CertificazioneController.class);

    private final CertificazioneService certificazioneService;
    private final EsameValidator esameValidator;
    private final EsameService esameService;

    @GetMapping("/certificazioni/{id}")
    public String mostraCertificazione(
            @RequestParam(required = false, value = "prenotazione") boolean prenotazione,
            @PathVariable Long id,
            Model model) {
        log.info("Richiesta GET /certificazione/" + id);

        Certificazione certificazione = this.certificazioneService.getCertificazione(id);
        model.addAttribute("prenotazioneEffettuata", prenotazione);
        model.addAttribute("certificazione", certificazione);
        return "certificazione";
    }
    

    @GetMapping("/admin/certificazioni")
    public String getCertificazioni(Model model) {
        log.info("Richiesta GET /admin/certificazioni");
        model.addAttribute("certificazioni", this.certificazioneService.getCertificazioni());
        return "admin/certificazioni";
    }

    @GetMapping("/admin/certificazioni/new")
    public String newCertificazioni(Model model) {
        log.info("Richiesta GET /admin/certificazioni/new");
        model.addAttribute("certificazione", new Certificazione());
        return "admin/certificazioneForm";
    }

    @GetMapping("/admin/certificazioni/{id}/modify")
    public String modificaCertificazione(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/certificazioni/" + id + "/modify");
        model.addAttribute("certificazione", this.certificazioneService.getCertificazione(id));
        return "admin/certificazioneForm";
    }

    @GetMapping("/admin/certificazioni/{id}/delete")
    public String cancellaCertificazione(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/certificazioni/" + id + "/delete");
        this.certificazioneService.cancellaCertificazione(id);
        return "redirect:/admin/certificazioni";
    }

    @GetMapping("/admin/certificazioni/{id}/esami/new")
    public String creaesamiCertificazione(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/certificazioni/" + id + "/esami/new");
        model.addAttribute("certificazione", this.certificazioneService.getCertificazione(id));
        model.addAttribute("esame", new Esame());
        return "admin/esameForm";
    }

    @PostMapping("/admin/certificazioni/new")
    public String saveCertificazione(@Valid @ModelAttribute Certificazione certificazione, BindingResult bindingResult, Model model) {
        log.info("Richiesta POST /admin/certificazioni/new");

        if (!bindingResult.hasErrors()) {
            log.info("Parametri inseriti Corretti");
            this.certificazioneService.salvaCertificazione(certificazione);
            return "redirect:/admin/certificazioni";
        }
        log.warn("Parametri inseriti non Validi");
        return "admin/certificazioneForm";
    }

    @PostMapping("/admin/certificazioni/{id}/esami")
    public String salvaesamiCertificazione(
            @Valid @ModelAttribute Esame esame,
            BindingResult bindingResult,
            @PathVariable("id") Long idCertificazione,
            Model model) {
        log.info("Richiesta POST /admin/certificazione/" + idCertificazione + "/esami");
        Certificazione certificazione = this.certificazioneService.getCertificazione(idCertificazione);
        certificazione.getEsami().add(esame);
        esame.setCertificazione(certificazione);
        this.esameValidator.validate(esame, bindingResult);

        if (!bindingResult.hasErrors()) {
            this.esameService.salvaEsame(esame);
            return "redirect:/certificazioni/" + idCertificazione;
        }
        model.addAttribute("certificazione", certificazione);
        return "admin/esameForm";
    }

    @GetMapping("/admin/esame/{id}/modify")
    public String modificaEsame(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/esame/" + id + "/modify");
        Esame esame=this.esameService.getEsame(id);
        Certificazione certificazione=esame.getCertificazione();
        model.addAttribute("esame", esame);
        model.addAttribute("certificazione", certificazione);
        return "admin/esameForm";
    }

    @GetMapping("/admin/esame/{id}/delete")
    public String cancellaEsame(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/certificazioni/" + id + "/delete");
        this.esameService.cancellaEsame(id);
        return "redirect:/admin/certificazioni";
    }

}
