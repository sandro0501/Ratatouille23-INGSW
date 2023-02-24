package com.example.ratatouille23.Models;

import com.example.ratatouille23.InterfacceMock.IProdotto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Prodotto implements Serializable, IProdotto {

    @Expose
    private int idProdotto;
    @Expose
    private String nome;
    @Expose
    private String descrizione;
    @Expose
    private String unita;
    @Expose
    @SerializedName("costo")
    private String costoAcquisto;
    @Expose
    private double quantita;
    @Expose
    private double soglia;
    @Expose
    @SerializedName("ristorante")
    private Ristorante utilizzatoDa;

    public Prodotto(String nome, String descrizione, String unita, String costoAcquisto, double quantita, double soglia) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.unita = unita;
        this.costoAcquisto = costoAcquisto;
        this.quantita = quantita;
        this.soglia = soglia;
    }

    public Prodotto(int idProdotto, String nome, String descrizione, String unita, String costoAcquisto, double quantita, double soglia) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.unita = unita;
        this.costoAcquisto = costoAcquisto;
        this.quantita = quantita;
        this.soglia = soglia;
    }

    public Prodotto(int idProdotto, String nome, String descrizione, String unita, String costoAcquisto, double quantita, double soglia, Ristorante utilizzatoDa) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.unita = unita;
        this.costoAcquisto = costoAcquisto;
        this.quantita = quantita;
        this.soglia = soglia;
        this.utilizzatoDa = utilizzatoDa;
    }

    public Prodotto(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public Prodotto() {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setUnita(String unita) {
        this.unita = unita;
    }

    public void setCostoAcquisto(String costoAcquisto) {
        this.costoAcquisto = costoAcquisto;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public void setSoglia(double soglia) {
        this.soglia = soglia;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public Ristorante getUtilizzatoDa() {
        return utilizzatoDa;
    }

    public void setUtilizzatoDa(Ristorante utilizzatoDa) {
        this.utilizzatoDa = utilizzatoDa;
    }


    @Override
    public String toString(){
        return nome;
    }

}
