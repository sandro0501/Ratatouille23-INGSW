package com.example.ratatouille23.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Elemento implements Serializable {

    private int idElemento;
    @SerializedName("denominazioneP")
    private String denominazionePrincipale;
    @SerializedName("denominazioneS")
    private String denominazioneSecondaria = "";
    @SerializedName("descrizioneP")
    private String descrizionePrincipale;
    @SerializedName("descrizioneS")
    private String descrizioneSecondaria = "";
    private Double costo;
    private int posizione;
    private transient ArrayList<Allergene> presenta = new ArrayList<>();
    private transient ArrayList<Preparazione> preparazione = new ArrayList<>();
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
