package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Gestore extends Utente implements Serializable  {

    public Gestore() {}
    public Gestore(String nome, String cognome, String email) {
        super(nome, cognome, email);
    }
}
