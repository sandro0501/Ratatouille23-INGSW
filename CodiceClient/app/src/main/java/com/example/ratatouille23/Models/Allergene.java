package com.example.ratatouille23.Models;

import java.io.Serializable;

public class Allergene implements Serializable {

    private listaAllergeni nome;

    public Allergene(listaAllergeni nome) {
        this.nome = nome;
    }

    public listaAllergeni getNome() {
        return nome;
    }

    public void setNome(listaAllergeni nome) {
        this.nome = nome;
    }
}
