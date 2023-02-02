package com.example.ratatouille23.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Allergene implements Serializable {

    @Expose
    private int idAllergene;
    @Expose
    private listaAllergeni nome;

    public Allergene(listaAllergeni nome) {
        setNome(nome);
    }

    public listaAllergeni getNome() {
        return nome;
    }

    public void setNome(listaAllergeni nome) {
        this.nome = nome;
        if(this.nome.equals(listaAllergeni.Crostacei)) this.setIdAllergene(1);
        else if(this.nome.equals(listaAllergeni.Molluschi)) this.setIdAllergene(2);
        else if(this.nome.equals(listaAllergeni.Uova)) this.setIdAllergene(3);
        else if(this.nome.equals(listaAllergeni.Soia)) this.setIdAllergene(4);
        else if(this.nome.equals(listaAllergeni.Sedano))this.setIdAllergene(5);
        else if(this.nome.equals(listaAllergeni.Solfiti))this.setIdAllergene(6);
        else if(this.nome.equals(listaAllergeni.Lupini))this.setIdAllergene(7);
        else if(this.nome.equals(listaAllergeni.Lattosio))this.setIdAllergene(8);
        else if(this.nome.equals(listaAllergeni.Pesce))this.setIdAllergene(9);
        else if(this.nome.equals(listaAllergeni.Senape))this.setIdAllergene(10);
        else if(this.nome.equals(listaAllergeni.Noci))this.setIdAllergene(11);
        else if(this.nome.equals(listaAllergeni.Arachidi))this.setIdAllergene(12);
        else if(this.nome.equals(listaAllergeni.Sesamo))this.setIdAllergene(13);
        else if(this.nome.equals(listaAllergeni.Glutine))this.setIdAllergene(14);
    }

    public void setIdAllergene(int idAllergene) {this.idAllergene = idAllergene;}

    public int getIdAllergene() {
        return idAllergene;
    }
}
