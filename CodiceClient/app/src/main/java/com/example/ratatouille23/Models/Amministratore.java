package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Amministratore extends Gestore implements Serializable {

    private boolean superA;

    public boolean isSuperA() {
        return superA;
    }

    public void setSuperA(boolean superA) {
        this.superA = superA;
    }

    public Amministratore(String nome, String cognome, String email, boolean superA) {
        super(nome, cognome, email);
        this.superA = superA;
    }
}
