package com.example.ratatouille23.Exceptions;

public class CampiVuotiException extends Exception {

    @Override
    public String getMessage() {
        return "Sono stati lasciati uno o più campi obbligatori vuoti";
    }

}
