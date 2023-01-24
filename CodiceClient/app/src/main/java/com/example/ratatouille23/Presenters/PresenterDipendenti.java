package com.example.ratatouille23.Presenters;

public class PresenterDipendenti extends PresenterBase {

    private static PresenterDipendenti instance;

    private PresenterDipendenti() { };

    public static PresenterDipendenti getInstance(){

        if (instance==null) {
            instance = new PresenterDipendenti();
        }
        return instance;

    }
}
