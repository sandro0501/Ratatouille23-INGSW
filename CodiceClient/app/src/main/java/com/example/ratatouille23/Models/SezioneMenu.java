package com.example.ratatouille23.Models;

import java.util.ArrayList;

public class SezioneMenu {

    private String titolo;
    private int posizione;
    private ArrayList<Elemento> appartenente = new ArrayList<>();
    private boolean inModifica = false;

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

    public boolean isInModifica() {
        return inModifica;
    }

    public void setInModifica(boolean inModifica) {
        this.inModifica = inModifica;
    }

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
}
