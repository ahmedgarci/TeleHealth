package com.example.demo.GlobalHandler;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;

@ControllerAdvice
public class AdviceController {
    
    @ExceptionHandler(CustomEntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFound(CustomEntityNotFoundException uncheckedException){
        return new ErrorResponse(uncheckedException.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArrayList<ErrorResponse>> handleInvalidInputs(MethodArgumentNotValidException exception){
        ArrayList<ErrorResponse> response = new ArrayList<>();
        for(FieldError error: exception.getBindingResult().getFieldErrors()){
            response.add(new ErrorResponse(error.getDefaultMessage()));             
        }
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    









}