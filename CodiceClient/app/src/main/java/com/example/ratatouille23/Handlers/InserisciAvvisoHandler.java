package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.google.gson.annotations.Expose;

public class InserisciAvvisoHandler
{
    @Expose
    public Avviso avviso;
    @Expose
    public Utente autore;
    @Expose
    public Ristorante ristorante;
}
