package com.example.demo.Data.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
    
}
