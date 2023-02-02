package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Elemento;

public interface DAOElemento {

    void getElementiOpenFoodFactsDaStringa(String stringaIniziale, DAOElementoImpl.ElementiFoodFactsCallbacks callback);

    void insertElemento(Elemento elementoDaAggiungere, DAOElementoImpl.AggiuntaElementiCallbacks callback);


    }
