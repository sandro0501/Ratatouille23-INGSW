package com.example.ratatouille23.Exceptions;

public class PasswordUgualeException extends Exception {
    @Override
    public String getMessage() {
        return "La nuova password deve essere diversa da quella attuale";
    }
}
