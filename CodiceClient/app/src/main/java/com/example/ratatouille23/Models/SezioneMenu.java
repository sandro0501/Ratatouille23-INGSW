package com.example.ratatouille23.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class SezioneMenu implements Serializable {

    private int idAvviso;
    private String titolo;
    private int posizione;
    private transient ArrayList<Elemento> appartenente = new ArrayList<>();
    private Ristorante ristorante;

    public SezioneMenu(String nome, int posizione) {
        this.titolo = nome;
        this.posizione = posizione;
    }

    public SezioneMenu(String nome, int posizione, ArrayList<Elemento> appartenente) {
        this.titolo = nome;
        this.posizione = posizione;
        this.appartenente = appartenente;

        for (Elemento el : appartenente) el.setAppartiene(this);
    }

    public void setIdAvviso(int idAvviso){ this.idAvviso = idAvviso;}

    public int getIdAvviso(){return idAvviso;}

    public ArrayList<Elemento> getAppartenente() {
        return appartenente;
    }

    public void setAppartenente(ArrayList<Elemento> appartenente) {
        this.appartenente = appartenente;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public Ristorante getRistorante() {
        return ristorante;
    }

    public void setRistorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    @Override
    public String toString() {
        return titolo;
    }

}
