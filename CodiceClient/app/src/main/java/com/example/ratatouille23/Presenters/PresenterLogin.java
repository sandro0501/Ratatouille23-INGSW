package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.Models.Utente;

public class PresenterLogin extends PresenterBase {

    private static PresenterLogin instance;

    private PresenterLogin() { };

    public static PresenterLogin getInstance(){

        if (instance==null) {
            instance = new PresenterLogin();
        }
        return instance;

    }

    public Utente bottoneLoginPremuto (String email, String password) {

        return new Utente();
    }

    public boolean bottoneRichiediCodicePremuto(String email) {

        return true;
    }


    public boolean bottoneResettaPasswordConCodicePremuto(String codice, String password) {
        return true;
    }

}
