package com.example.ratatouille23.Presenters;

public class PresenterRistorante extends PresenterBase {

    private static PresenterRistorante instance;

    private PresenterRistorante() { };

    public static PresenterRistorante getInstance(){

        if (instance==null) {
            instance = new PresenterRistorante();
        }
        return instance;

    }
}
