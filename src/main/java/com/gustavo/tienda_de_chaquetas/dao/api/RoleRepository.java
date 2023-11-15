package com.gustavo.tienda_de_chaquetas.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gustavo.tienda_de_chaquetas.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role,Long> {
}
