package com.gustavo.tienda_de_chaquetas.controllers;

import com.gustavo.tienda_de_chaquetas.model.Persona;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.gustavo.tienda_de_chaquetas.dao.api.PersonaRepository;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
public class PersonaController {

    @Autowired
    private final PersonaRepository personaRepository;

	public PersonaController(PersonaRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

    @GetMapping("/")
    public String getAll(){
        log.info("Probando el log");
        personaRepository.save(new Persona(1L,"Juan","David","Porras","Gómez","juan@gmail.com","123456","password"));
        var result = personaRepository.findAll();
        System.out.println(result);
        return "index";
    }
    
}
