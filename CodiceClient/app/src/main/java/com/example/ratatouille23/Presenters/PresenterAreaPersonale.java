package com.example.ratatouille23.Presenters;

public class PresenterAreaPersonale extends PresenterBase {

    private static PresenterAreaPersonale instance;

    private PresenterAreaPersonale() { };

    public static PresenterAreaPersonale getInstance(){

        if (instance==null) {
            instance = new PresenterAreaPersonale();
        }
        return instance;

    }

    public Boolean bottoneModificaPasswordPremuto(String vecchiaPassword, String nuovaPassword) {
        return true;
    }

}
