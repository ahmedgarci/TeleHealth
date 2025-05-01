package com.example.demo.Data.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Data.Entities.Role;
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(String roleName);
}
