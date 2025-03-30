package com.example.demo.Data.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.Role;
@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Role findByRoleName(String roleName);
}
