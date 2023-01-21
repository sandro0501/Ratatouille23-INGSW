package com.example.ratatouille23.Views;

import android.view.View;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;

public interface RecyclerViewElementoMenuInterface {
    public void onElementoClicked(Elemento elementoCliccato, View itemView);

    public void onVediIngredientiElementoClicked(Elemento elemento, View view);
}
