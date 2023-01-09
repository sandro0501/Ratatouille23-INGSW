package com.example.ratatouille23.Models;

import java.util.Date;

public class Avviso {

    private String oggetto;
    private String corpo;
    private String dataCreazione;
    private String autore;
    private int iconaAvviso;
    private String ruoloAutoreAvviso;

    public Avviso(String oggetto, String corpo, String dataCreazione, String autore, int iconaAvviso, String ruoloAutoreAvviso) {
        this.oggetto = oggetto;
        this.corpo = corpo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
        this.iconaAvviso = iconaAvviso;
        this.ruoloAutoreAvviso = ruoloAutoreAvviso;
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

    public String getAutore() {
        return autore;
    }

    public int getIconaAvviso() {
        return iconaAvviso;
    }

    public String getRuoloAutoreAvviso() {
        return ruoloAutoreAvviso;
    }



}
