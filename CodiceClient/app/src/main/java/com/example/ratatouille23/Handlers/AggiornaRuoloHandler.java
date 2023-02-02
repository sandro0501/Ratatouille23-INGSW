package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Ristorante;
import com.google.gson.annotations.Expose;

public class AggiornaRuoloHandler {

    @Expose
    public UtenteHandler utente;
    @Expose
    public Ristorante ristorante;
    @Expose
    public boolean passc = false;
}
