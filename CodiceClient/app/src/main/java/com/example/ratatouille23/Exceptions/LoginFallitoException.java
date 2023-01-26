package com.example.ratatouille23.Exceptions;

public class LoginFallitoException extends Exception{

    @Override
    public String getMessage() {
        return "I dati inseriti non risultano corretti";
    }

}
