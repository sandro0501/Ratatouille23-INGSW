package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Prodotto;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class EliminaProdottiHandler {
    @Expose
    public ArrayList<Prodotto> prodotti;

    public EliminaProdottiHandler(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }
}