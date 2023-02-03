package com.example.ratatouille23.Views;

import android.view.View;

import com.example.ratatouille23.Models.Elemento;

import java.io.File;

public interface RecyclerViewElementoMenuInterface {
    public void onElementoClicked(Elemento elementoCliccato, File fileImmagine, View itemView);

    public void onVediIngredientiElementoClicked(Elemento elemento, View view);
}
