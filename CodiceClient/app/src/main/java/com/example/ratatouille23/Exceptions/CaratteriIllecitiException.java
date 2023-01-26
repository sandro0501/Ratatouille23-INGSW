package com.example.ratatouille23.Exceptions;

public class CaratteriIllecitiException extends Exception {

    @Override
    public String getMessage() {
        return "Sono stati inseriti uno o più caratteri illeciti";
    }

}
