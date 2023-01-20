package com.example.ratatouille23.Models;

import java.util.Date;

public class Avviso {

    private String oggetto;
    private String corpo;
    private String dataCreazione;
    private Gestore autore;
    private int iconaAvviso;

    public Avviso(String oggetto, String corpo, String dataCreazione, Gestore autore, int iconaAvviso) {
        this.oggetto = oggetto;
        this.corpo = corpo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
        this.iconaAvviso = iconaAvviso;
    }

    public String getOggetto() {
        return oggetto;
    }

    public String getCorpo() {
        return corpo;
    }

    public String getDataCreazione() {
        return dataCreazione;
    }

    public Gestore getAutore() {
        return autore;
    }

    public int getIconaAvviso() {
        return iconaAvviso;
    }




}
