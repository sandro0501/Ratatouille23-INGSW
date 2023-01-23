package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Prodotto;

import java.util.ArrayList;

public interface DAOProdotto {

    public void getProdottiOpenFoodFactsDaStringa(String stringaIniziale, DAOProdottoImpl.ProdottoCallbacks callback);

}
