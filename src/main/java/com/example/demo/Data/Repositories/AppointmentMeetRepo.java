package com.example.demo.Data.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.AppointmentMeet;

@Repository
public interface AppointmentMeetRepo extends JpaRepository<AppointmentMeet,Integer>{
    
}
