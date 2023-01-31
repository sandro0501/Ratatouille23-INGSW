package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Amministratore;
import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;

import java.util.ArrayList;

public class UtenteHandler {

    public int idUtente;
    public String nome;
    public String cognome;
    public String email;
    public String ruolo;
    public boolean master;
    public Ristorante idRistorante;

    public UtenteHandler (Utente utente) {
        idUtente = utente.getIdUtente();
        nome = utente.getNome();
        cognome = utente.getCognome();
        email = utente.getEmail();
        ruolo = utente.getRuoloUtente();
        if (ruolo.equals("Amministratore"))
            master = ((Amministratore)utente).isSuperA();
        else
            master = false;

        idRistorante = utente.getIdRistorante();
    }

}
