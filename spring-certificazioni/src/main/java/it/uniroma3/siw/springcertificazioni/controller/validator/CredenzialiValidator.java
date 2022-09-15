package it.uniroma3.siw.springcertificazioni.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;

/**
 * Validator for Credentials
 */
@Component
public class CredenzialiValidator implements Validator {

    @Autowired
    private CredenzialiService credentialsService;

    final Integer MAX_USERNAME_LENGTH = 20;
    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;

    @Override
    public void validate(Object o, Errors errors) {
        Credenziali credenziali = (Credenziali) o;
        String username = credenziali.getUsername().trim();
        String password = credenziali.getPassword().trim();

        if (username.isEmpty())
            errors.rejectValue("username", "NotBlank");
        else if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "Size");
        else if (this.credentialsService.getCredenziali(username) != null)
            errors.rejectValue("username", "Unique");

        if (password.isEmpty())
            errors.rejectValue("password", "NotBlank");
        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            errors.rejectValue("password", "Size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Credenziali.class.equals(clazz);
    }

}