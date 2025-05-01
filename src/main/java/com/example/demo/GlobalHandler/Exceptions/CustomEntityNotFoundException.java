package com.example.demo.GlobalHandler.Exceptions;


public class CustomEntityNotFoundException extends RuntimeException{
    public CustomEntityNotFoundException(String message){
        super(message);
    }
}
