package com.example.ratatouille23.Modelli;

public class Amministratore extends Gestore{

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
