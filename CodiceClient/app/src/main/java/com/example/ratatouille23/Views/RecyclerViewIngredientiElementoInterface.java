package com.example.ratatouille23.Views;

import android.view.View;

import com.example.ratatouille23.Models.Prodotto;

public interface RecyclerViewIngredientiElementoInterface {
    void onProdottoAssociatoAElementoSelezionato(Prodotto prodottoSelezionato, View itemView);
}
