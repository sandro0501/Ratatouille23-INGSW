package com.example.ratatouille23.Models;

import java.util.ArrayList;

public class SezioneMenu {

    private String titolo;
    private int posizione;
    private ArrayList<Elemento> appartenente;

    public SezioneMenu(String nome, int posizione) {
        this.titolo = nome;
        this.posizione = posizione;
        ArrayList<Elemento> appartenente = new ArrayList<>();
    }

    public SezioneMenu(String nome, int posizione, ArrayList<Elemento> appartenente) {
        this.titolo = nome;
        this.posizione = posizione;
        this.appartenente = appartenente;
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
