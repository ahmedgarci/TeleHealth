package com.example.demo.GlobalHandler.Exceptions;

public class ActionNotAllowed extends RuntimeException{
    public ActionNotAllowed(String msg){
        super(msg);
    }
}
