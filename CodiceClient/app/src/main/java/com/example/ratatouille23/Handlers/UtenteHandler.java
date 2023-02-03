package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Amministratore;
import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class UtenteHandler {

    @Expose
    public int idUtente;
    @Expose
    public String nome;
    @Expose
    public String cognome;
    @Expose
    public String email;
    @Expose
    public String ruolo;
    @Expose
    public boolean master;
    @Expose
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
