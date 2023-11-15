package com.gustavo.tienda_de_chaquetas.dao.api;

import com.gustavo.tienda_de_chaquetas.model.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends MongoRepository<Persona,Long> {
}
