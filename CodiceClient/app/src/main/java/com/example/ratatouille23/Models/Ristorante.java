package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Ristorante implements Serializable {

    private String nome;
    private String numeroDiTelefono;
    private String indirizzo;
    private String citta;
    private boolean turistico;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroDiTelefono() {
        return numeroDiTelefono;
    }

    public void setNumeroDiTelefono(String numeroDiTelefono) {
        this.numeroDiTelefono = numeroDiTelefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public boolean isTuristico() {
        return turistico;
    }

    public void setTuristico(boolean turistico) {
        this.turistico = turistico;
    }

    public Ristorante(String nome, String numeroDiTelefono, String indirizzo, String citta, boolean turistico) {
        this.nome = nome;
        this.numeroDiTelefono = numeroDiTelefono;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.turistico = turistico;
    }


}
