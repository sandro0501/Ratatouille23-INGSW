package com.example.ratatouille23.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Elemento implements Serializable {

    @Expose
    private int idElemento;
    @Expose
    @SerializedName("denominazioneP")
    private String denominazionePrincipale;
    @Expose
    @SerializedName("denominazioneS")
    private String denominazioneSecondaria = "";
    @Expose
    @SerializedName("descrizioneP")
    private String descrizionePrincipale;
    @Expose
    @SerializedName("descrizioneS")
    private String descrizioneSecondaria = "";
    @Expose
    private Double costo;
    @Expose
    private int posizione;
    @Expose(serialize = false)
    private ArrayList<Allergene> presenta = new ArrayList<>();
    @Expose(serialize = false)
    private ArrayList<Preparazione> preparazione = new ArrayList<>();
    @Expose
    @SerializedName("sezioneMenu")
    private SezioneMenu appartiene;

    public Elemento(String denominazionePrincipale, String denominazioneSecondaria, String descrizionePrincipale, String descrizioneSecondaria, Double costo, int posizione, ArrayList<Allergene> presenta, ArrayList<Preparazione> preparazione, SezioneMenu appartiene) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.denominazioneSecondaria = denominazioneSecondaria;
        this.descrizionePrincipale = descrizionePrincipale;
        this.descrizioneSecondaria = descrizioneSecondaria;
        this.costo = costo;
        this.posizione = posizione;
        this.presenta = presenta;
        this.preparazione = preparazione;
        this.appartiene = appartiene;
    }

    public Elemento(String denominazionePrincipale, String descrizionePrincipale, Double costo, int posizione, ArrayList<Allergene> presenta,  ArrayList<Preparazione> preparazione, SezioneMenu appartiene) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.descrizionePrincipale = descrizionePrincipale;
        this.costo = costo;
        this.posizione = posizione;
        this.presenta = presenta;
        this.preparazione = preparazione;
        this.appartiene = appartiene;
    }

    public Elemento(String denominazionePrincipale, String denominazioneSecondaria, String descrizionePrincipale, String descrizioneSecondaria, Double costo, int posizione, ArrayList<Allergene> presenta, ArrayList<Preparazione> preparazione) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.denominazioneSecondaria = denominazioneSecondaria;
        this.descrizionePrincipale = descrizionePrincipale;
        this.descrizioneSecondaria = descrizioneSecondaria;
        this.costo = costo;
        this.posizione = posizione;
        this.presenta = presenta;
        this.preparazione = preparazione;
    }

    public Elemento(String denominazionePrincipale, String descrizionePrincipale, double costo, int posizione, ArrayList<Allergene> presenta) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.descrizionePrincipale = descrizionePrincipale;
        this.costo = costo;
        this.posizione = posizione;
        this.presenta = presenta;
    }

    public Elemento(String denominazionePrincipale, String denominazioneSecondaria, String descrizionePrincipale, String descrizioneSecondaria, double costo, int posizione) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.denominazioneSecondaria = denominazioneSecondaria;
        this.descrizionePrincipale = descrizionePrincipale;
        this.descrizioneSecondaria = descrizioneSecondaria;
        this.costo = costo;
        this.posizione = posizione;
    }

    public Elemento(String denominazionePrincipale, String descrizionePrincipale, double costo, int posizione) {
        this.denominazionePrincipale = denominazionePrincipale;
        this.descrizionePrincipale = descrizionePrincipale;
        this.costo = costo;
        this.posizione = posizione;
    }

    public Elemento() {

    }

    public void setIdElemento(int idElemento){ this.idElemento = idElemento;}

    public int getIdElemento(){return idElemento;}

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

    public ArrayList<Preparazione> getPreparazione() {
        return preparazione;
    }

    public void setPreparazione(ArrayList<Preparazione> preparazione) {
        this.preparazione = preparazione;
    }

    public SezioneMenu getAppartiene() {
        return appartiene;
    }

    public void setAppartiene(SezioneMenu appartiene) {
        this.appartiene = appartiene;
    }

    @Override
    public String toString() {
        return denominazionePrincipale;
    }
}
