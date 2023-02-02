package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.google.gson.annotations.Expose;

public class RegistraUtenteHandler {

    @Expose
    public UtenteHandler utente;
    @Expose
    public Ristorante ristorante;
    @Expose
    public String password;
}
