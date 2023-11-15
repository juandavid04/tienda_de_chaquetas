package com.gustavo.tienda_de_chaquetas.dao.api;

import com.gustavo.tienda_de_chaquetas.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role,Long> {
}
