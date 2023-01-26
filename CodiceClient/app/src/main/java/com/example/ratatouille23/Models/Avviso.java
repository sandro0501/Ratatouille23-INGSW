package com.example.ratatouille23.Models;

import java.io.Serializable;
import java.util.Date;

public class Avviso implements Serializable {

    private String oggetto;
    private String corpo;
    private Date dataCreazione;
    private Gestore autore;

    public Avviso(String oggetto, String corpo, Date dataCreazione, Gestore autore) {
        this.oggetto = oggetto;
        this.corpo = corpo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
    }

    public String getOggetto() {
        return oggetto;
    }

    public String getCorpo() {
        return corpo;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public Gestore getAutore() {
        return autore;
    }

}
