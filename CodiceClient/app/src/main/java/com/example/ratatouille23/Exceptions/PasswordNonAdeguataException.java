package com.example.ratatouille23.Exceptions;

import androidx.annotation.Nullable;

public class PasswordNonAdeguataException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return ("La password fornita non rispetta i canoni necessari.\nAssiscurarsi che la password sia almeno lunga 10 caratteri e contenga:\n" +
                "almeno un carattere maiuscolo,\n" +
                "almeno un carattere minuscolo, \n" +
                "almeno una cifra, \n" +
                "almeno un simbolo, \n");
    }
}
