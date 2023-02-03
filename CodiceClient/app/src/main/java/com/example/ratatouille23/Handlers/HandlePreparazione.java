package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Prodotto;
import com.google.gson.annotations.Expose;

public class HandlePreparazione
{
    @Expose
    public Elemento idElemento;
    @Expose
    public Prodotto idProdotto;
    @Expose
    public double quantita;
}
