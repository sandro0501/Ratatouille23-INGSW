package com.example.ratatouille23.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Avviso implements Serializable {

    @Expose
    private int idAvviso;
    @Expose
    private String oggetto;
    @Expose
    private String corpo;
    @Expose
    private LocalDate dataCreazione;
    @Expose
    private Gestore autore;

    public Avviso(int idAvviso,String oggetto, String corpo, LocalDate dataCreazione, Gestore autore) {
        this.idAvviso = idAvviso;
        this.oggetto = oggetto;
        this.corpo = corpo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
    }

    public Avviso(){}

    public String getOggetto() {
        return oggetto;
    }

    public String getCorpo() {
        return corpo;
    }

    public LocalDate getDataCreazione() {
        return dataCreazione;
    }

    public Gestore getAutore() {
        return autore;
    }

    public void setOggetto(String oggetto)
    {
        this.oggetto = oggetto;
    }
    public void setCorpo(String corpo)
    {
        this.corpo = corpo;
    }



}
