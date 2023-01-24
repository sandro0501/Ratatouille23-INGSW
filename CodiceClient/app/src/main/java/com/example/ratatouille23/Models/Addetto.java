package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Addetto extends Utente implements Serializable {

    private ruoliPersonale ruolo;

    public ruoliPersonale getRuolo() {
        return ruolo;
    }

    public void setRuolo(ruoliPersonale ruolo) {
        this.ruolo = ruolo;
    }

    public Addetto(String nome, String cognome, String email, ruoliPersonale ruolo) {
        super(nome, cognome, email);
        this.ruolo = ruolo;
    }
}
