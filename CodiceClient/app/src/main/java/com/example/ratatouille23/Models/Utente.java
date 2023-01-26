package com.example.ratatouille23.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Utente implements Serializable {


    private int id;
    private String idToken;
    private String accessToken;    private String nome;
    private String cognome;
    private String email;
    private ArrayList<Bacheca> bacheca = new ArrayList<>();
    private Ristorante lavora;

    public Utente() {};

    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public Utente(String nome, String cognome, String email, ArrayList<Bacheca> bacheca) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.bacheca = bacheca;
    }

    public ArrayList<Bacheca> getBacheca() {
        return bacheca;
    }

    public void setBacheca(ArrayList<Bacheca> bacheca) {
        this.bacheca = bacheca;
    }

    public String getRuoloUtente() {
        if (this instanceof Amministratore) return "Amministratore";
        else if (this instanceof Supervisore) return "Supervisore";
        else {
            if (((Addetto)this).getRuolo().equals(ruoliPersonale.addettoAllaCucina)) return "Addetto alla cucina";
            else return "Addetto al servizio";
        }
    }

    public String getNomeCompleto() {
        return nome + " " + cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Ristorante getLavora() {
        return lavora;
    }

    public void setLavora(Ristorante lavora) {
        this.lavora = lavora;
    }
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }}
