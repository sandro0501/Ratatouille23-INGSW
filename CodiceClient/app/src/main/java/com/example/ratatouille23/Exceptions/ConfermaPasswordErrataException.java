package com.example.ratatouille23.Exceptions;

public class ConfermaPasswordErrataException extends Exception {

    @Override
    public String getMessage() {
        return ("La password inserita e la sua conferma non coincidono");
    }
}
