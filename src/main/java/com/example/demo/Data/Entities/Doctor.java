package com.example.demo.Data.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Builder
@Document(collection ="doctors")
public class Doctor extends User {
    private String specialization;
    private int yearsOfExperience;
    private String location;
}
