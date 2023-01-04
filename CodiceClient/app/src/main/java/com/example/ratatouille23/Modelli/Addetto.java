package com.example.ratatouille23.Modelli;

public class Addetto extends Utente {

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
