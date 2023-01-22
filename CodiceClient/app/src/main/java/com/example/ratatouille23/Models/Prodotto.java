package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Prodotto implements Serializable {

    private String nome;
    private String descrizione;
    private String unita;
    private String costoAcquisto;
    private double quantita;
    private double soglia;

    public Prodotto(String nome, String descrizione, String unita, String costoAcquisto, double quantita, double soglia) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.unita = unita;
        this.costoAcquisto = costoAcquisto;
        this.quantita = quantita;
        this.soglia = soglia;
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
