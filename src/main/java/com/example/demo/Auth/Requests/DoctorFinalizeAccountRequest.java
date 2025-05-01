package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorFinalizeAccountRequest {
    @NotNull(message = "lattitude is required")
    private Long lat;
    @NotNull(message = "longtitude is required")
    private Long lngt;
    @NotBlank(message = "description cant be empty")
    private String description;

}
