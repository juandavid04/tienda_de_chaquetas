package com.gustavo.tienda_de_chaquetas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import com.gustavo.tienda_de_chaquetas.dao.api.PersonaRepository;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
public class PersonaController {

    private final PersonaRepository personaRepository;

	public PersonaController(PersonaRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

    @GetMapping("/")
    public String getAll(){
        log.info("Probando el log");
        var result = personaRepository.findAll();
        System.out.println(result);
        return "index";
    }
    
}
