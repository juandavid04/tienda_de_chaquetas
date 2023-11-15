package com.gustavo.tienda_de_chaquetas.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gustavo.tienda_de_chaquetas.model.Persona;

@Repository
public interface PersonaRepository extends MongoRepository<Persona,Long> {
    Persona findByCorreo(String nombre);
}
