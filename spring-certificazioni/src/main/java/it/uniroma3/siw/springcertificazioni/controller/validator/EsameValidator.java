package it.uniroma3.siw.springcertificazioni.controller.validator;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EsameValidator implements Validator {

    private final EsameService esameService;

    @Override
    public void validate(Object target, Errors errors) {
        Esame esame = (Esame) target;
        Long id = (esame.getId() == null) ? 0l : esame.getId();
        Integer durata = esame.getDurata();
        String aula = esame.getAula().trim();
        LocalDateTime data = esame.getData();

        if (this.esameService.esisteEsame(id, aula, data, durata)) {
            errors.reject("Unique.esame");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Esame.class.equals(clazz);
    }

}
