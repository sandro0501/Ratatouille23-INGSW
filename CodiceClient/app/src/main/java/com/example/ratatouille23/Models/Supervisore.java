package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Supervisore extends Gestore implements Serializable {

    public Supervisore() {
    }

    public Supervisore(String nome, String cognome, String email) {
        super(nome, cognome, email);
    }
}
