package com.example.ratatouille23.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class Ristorante implements Serializable {

    @Expose
    private int idRistorante;
    @Expose
    private String denominazione;
    @Expose
    private String numeroTelefono;
    @Expose
    private String indirizzo;
    @Expose
    private String citta;
    @Expose
    private boolean turistico;
    @Expose
    private String urlFoto;

    @Expose(serialize = false)
    private ArrayList<SezioneMenu> offre = new ArrayList<>();
    @Expose(serialize = false)
    private ArrayList<Prodotto> utilizza = new ArrayList<>();

    public Ristorante() {}

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String nome) {
        this.denominazione = nome;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroDiTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public boolean isTuristico() {
        return turistico;
    }

    public void setTuristico(boolean turistico) {
        this.turistico = turistico;
    }

    public Ristorante(String nome, String numeroDiTelefono, String indirizzo, String citta, boolean turistico) {
        this.denominazione = nome;
        this.numeroTelefono = numeroDiTelefono;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.turistico = turistico;
    }

    public Ristorante(int id, String nome, String numeroDiTelefono, String indirizzo, String citta, boolean turistico, String url) {
        this.idRistorante = id;
        this.denominazione = nome;
        this.numeroTelefono = numeroDiTelefono;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.turistico = turistico;
        this.urlFoto = url;
    }

    public ArrayList<SezioneMenu> getOffre() {
        return offre;
    }

    public void setOffre(ArrayList<SezioneMenu> offre) {
        this.offre = offre;
    }

    public ArrayList<Prodotto> getUtilizza() {
        return utilizza;
    }

    public void setUtilizza(ArrayList<Prodotto> utilizza) {
        this.utilizza = utilizza;
    }

    public int getIdRistorante() {
        return idRistorante;
    }

    public void setIdRistorante(int id) {
        this.idRistorante = id;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
