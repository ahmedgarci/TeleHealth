package com.example.demo.Data.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.BaseUser;

@Repository
public interface UserRepo extends JpaRepository<BaseUser,Integer>{
    
}
