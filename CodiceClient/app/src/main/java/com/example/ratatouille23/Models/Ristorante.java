package com.example.ratatouille23.Models;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class Ristorante implements Serializable {

    private int id;
    private String nome;
    private String numeroDiTelefono;
    private String indirizzo;
    private String citta;
    private boolean turistico;
    private String urlFoto;
    private ArrayList<SezioneMenu> offre = new ArrayList<>();
    private ArrayList<Prodotto> utilizza = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroDiTelefono() {
        return numeroDiTelefono;
    }

    public void setNumeroDiTelefono(String numeroDiTelefono) {
        this.numeroDiTelefono = numeroDiTelefono;
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
        this.nome = nome;
        this.numeroDiTelefono = numeroDiTelefono;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.turistico = turistico;
    }

    public Ristorante(int id, String nome, String numeroDiTelefono, String indirizzo, String citta, boolean turistico, String url) {
        this.id = id;
        this.nome = nome;
        this.numeroDiTelefono = numeroDiTelefono;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
