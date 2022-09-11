package it.uniroma3.siw.springcertificazioni.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Utente;
import it.uniroma3.siw.springcertificazioni.service.UtenteService;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class UtenteValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;
    
    private final UtenteService utenteService;
    
    @Override
    public void validate(Object o, Errors errors) {
        Utente user = (Utente) o;
        String nome = user.getNome().trim();
        String cognome = user.getCognome().trim();
        Long id = user.getId();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");

        if(this.utenteService.esisteStudenteNome(id, nome) && this.utenteService.esisteStudenteCognome(id, cognome)) {
                errors.rejectValue("omonimo", "Unique");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }

}

