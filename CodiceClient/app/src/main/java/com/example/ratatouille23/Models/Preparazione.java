package com.example.ratatouille23.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Preparazione implements Serializable  {

    @Expose
    Prodotto prodottoAssociato;
    @Expose
    double quantitaNecessaria;

    public Preparazione(Prodotto prodottoAssociato, double quantitaNecessaria) {
        this.prodottoAssociato = prodottoAssociato;
        this.quantitaNecessaria = quantitaNecessaria;
    }

    public Prodotto getProdottoAssociato() {
        return prodottoAssociato;
    }

    public void setProdottoAssociato(Prodotto prodottoAssociato) {
        this.prodottoAssociato = prodottoAssociato;
    }

    public double getQuantitaNecessaria() {
        return quantitaNecessaria;
    }

    public void setQuantitaNecessaria(double quantitaNecessaria) {
        this.quantitaNecessaria = quantitaNecessaria;
    }
}
