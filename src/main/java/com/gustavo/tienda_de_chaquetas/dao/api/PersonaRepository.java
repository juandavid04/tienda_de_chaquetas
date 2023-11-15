package com.gustavo.tienda_de_chaquetas.dao.api;

import com.gustavo.tienda_de_chaquetas.model.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonaRepository extends MongoRepository<Persona,Long> {
}
