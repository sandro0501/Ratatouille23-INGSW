package com.example.ratatouille23.Models;

public class Preparazione {
    Prodotto prodottoAssociato;
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
