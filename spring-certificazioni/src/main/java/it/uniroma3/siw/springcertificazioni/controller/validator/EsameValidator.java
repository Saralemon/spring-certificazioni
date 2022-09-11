package it.uniroma3.siw.springcertificazioni.controller.validator;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EsameValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(EsameValidator.class);

    private final EsameService esameService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validazione Esame Iniziata");
        Esame esame = (Esame)target;
        Long id = (esame.getId()==null) ? 0l : esame.getId();
        Integer durata = esame.getDurata();
        String aula = esame.getAula().trim();
        LocalDateTime data = esame.getData();

        log.debug("Validazione Globale");
        if(this.esameService.esisteEsame(id, aula, data, durata)) {
            log.debug("Non si possono sotenere più esami nella stessa aula contemporaneamente");
            errors.reject("Unique.esame");
        }

        log.info("Validazione Esame Terminata");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Esame.class.equals(clazz);
    }

    


    
}
