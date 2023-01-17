package com.example.ratatouille23.Models;

import java.util.ArrayList;

public class Elemento {

    private String denominazionePrincipale;
    private String denominazioneSecondaria = "";
    private String descrizionePrincipale;
    private String descrizioneSecondaria = "";
    private Double costo;
    private int posizione;
    private ArrayList<Allergene> presenta;
    private ArrayList<Prodotto> preparatoCon;
    private ArrayList<Double> quantitaNecessaria;

    public Elemento(String denominazionePrincipale, String denominazioneSecondaria, String descrizionePrincipale, String descrizioneSecondaria, double costo, int posizione, ArrayList<Allergene> presenta, ArrayList<Prodotto> preparatoCon, ArrayList<Double> quantitaNecessaria) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.denominazioneSecondaria = denominazioneSecondaria;
        this.descrizionePrincipale = descrizionePrincipale;
        this.descrizioneSecondaria = descrizioneSecondaria;
        this.costo = costo;
        this.posizione = posizione;
        this.presenta = presenta;
        this.preparatoCon = preparatoCon;
        this.quantitaNecessaria = quantitaNecessaria;
    }

    public Elemento(String denominazionePrincipale, String descrizionePrincipale, double costo, int posizione, ArrayList<Allergene> presenta, ArrayList<Prodotto> preparatoCon, ArrayList<Double> quantitaNecessaria) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.descrizionePrincipale = descrizionePrincipale;
        this.costo = costo;
        this.posizione = posizione;
        this.presenta = presenta;
        this.preparatoCon = preparatoCon;
        this.quantitaNecessaria = quantitaNecessaria;
    }

    public Elemento(String denominazionePrincipale, String denominazioneSecondaria, String descrizionePrincipale, String descrizioneSecondaria, double costo, int posizione) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.denominazioneSecondaria = denominazioneSecondaria;
        this.descrizionePrincipale = descrizionePrincipale;
        this.descrizioneSecondaria = descrizioneSecondaria;
        this.costo = costo;
        this.posizione = posizione;
        presenta = new ArrayList<>();
        this.preparatoCon = new ArrayList<>();
        this.quantitaNecessaria = new ArrayList<>();
    }

    public Elemento(String denominazionePrincipale, String descrizionePrincipale, double costo, int posizione) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.descrizionePrincipale = descrizionePrincipale;
        this.costo = costo;
        this.posizione = posizione;
        presenta = new ArrayList<>();
        this.preparatoCon = new ArrayList<>();
        this.quantitaNecessaria = new ArrayList<>();
    }

    public String getDenominazionePrincipale() {
        return denominazionePrincipale;
    }

    public void setDenominazionePrincipale(String denominazionePrincipale) {
        this.denominazionePrincipale = denominazionePrincipale;
    }

    public String getDenominazioneSecondaria() {
        return denominazioneSecondaria;
    }

    public void setDenominazioneSecondaria(String denominazioneSecondaria) {
        this.denominazioneSecondaria = denominazioneSecondaria;
    }

    public String getDescrizionePrincipale() {
        return descrizionePrincipale;
    }

    public void setDescrizionePrincipale(String descrizionePrincipale) {
        this.descrizionePrincipale = descrizionePrincipale;
    }

    public String getDescrizioneSecondaria() {
        return descrizioneSecondaria;
    }

    public void setDescrizioneSecondaria(String descrizioneSecondaria) {
        this.descrizioneSecondaria = descrizioneSecondaria;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public ArrayList<Allergene> getPresenta() {
        return presenta;
    }

    public void setPresenta(ArrayList<Allergene> presenta) {
        this.presenta = presenta;
    }

    public ArrayList<Prodotto> getPreparatoCon() {
        return preparatoCon;
    }

    public void setPreparatoCon(ArrayList<Prodotto> preparatoCon) {
        this.preparatoCon = preparatoCon;
    }

    public ArrayList<Double> getQuantitaNecessaria() {
        return quantitaNecessaria;
    }

    public void setQuantitaNecessaria(ArrayList<Double> quantitaNecessaria) {
        this.quantitaNecessaria = quantitaNecessaria;
    }
}
