package com.example.ratatouille23;

import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Exceptions.PasswordNonAdeguataException;
import com.example.ratatouille23.Exceptions.PasswordUgualeException;
import com.example.ratatouille23.Presenters.PresenterAreaPersonale;

public class PresenterAreaPersonaleMock {

    private static PresenterAreaPersonaleMock instance;

    private PresenterAreaPersonaleMock() { };

    public static PresenterAreaPersonaleMock getInstance(){

        if (instance==null) {
            instance = new PresenterAreaPersonaleMock();
        }
        return instance;

    }

    public void controllaPassword(String vecchiaPassword, String nuovaPassword, String confermaPassword) throws PasswordUgualeException, CampiVuotiException, PasswordNonAdeguataException, ConfermaPasswordErrataException {
        if (vecchiaPassword.isEmpty() || nuovaPassword.isEmpty() || confermaPassword.isEmpty())
            throw new CampiVuotiException();
        if (!nuovaPassword.equals(confermaPassword))
            throw new ConfermaPasswordErrataException();
        if (vecchiaPassword.equals(nuovaPassword))
            throw new PasswordUgualeException();
        if (!soddisfaRequisiti(nuovaPassword))
            throw new PasswordNonAdeguataException();
    }

    public boolean soddisfaRequisiti(String nuovaPassword) {
        boolean flagMaiuscola = false;
        boolean flagMinuscola = false;
        boolean flagNumero = false;
        boolean flagSimbolo = false;
        String stringaSimboli = "^ $ * . [ ] { } ( ) ? @ # % & / , > < : ; | _ ~ = + - !";
        if (nuovaPassword.length() < 8)
            return false;
        for (int i = 0; i < nuovaPassword.length(); i++) {
            char carattereCorrente = nuovaPassword.charAt(i);
            if (Character.isUpperCase(carattereCorrente))
                flagMaiuscola = true;
            if (Character.isLowerCase(carattereCorrente))
                flagMinuscola = true;
            if (Character.isDigit(carattereCorrente))
                flagNumero = true;
            if (stringaSimboli.indexOf(carattereCorrente) >= 0)
                flagSimbolo = true;
        }
        return flagMaiuscola && flagMinuscola && flagNumero && flagSimbolo;
    }

}
