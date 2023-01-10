package com.example.ratatouille23.Models;

public class Prodotto {

    private String nome;
    private String descrizione;
    private String unita;
    private String costoAcquisto;
    private double quantita;
    private double soglia;

    public Prodotto(String nome, String descrizione, String unita, String costoAcquisto, double quantita) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.unita = unita;
        this.costoAcquisto = costoAcquisto;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getUnita() {
        return unita;
    }

    public String getCostoAcquisto() {
        return costoAcquisto;
    }

    public double getQuantita() {
        return quantita;
    }

    public double getSoglia() {
        return soglia;
    }
}