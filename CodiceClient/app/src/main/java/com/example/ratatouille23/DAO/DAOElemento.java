package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.EliminaElementiHandler;
import com.example.ratatouille23.Handlers.EliminaPreparazioniHandler;
import com.example.ratatouille23.Handlers.HandlePreparazione;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;

import java.util.ArrayList;

public interface DAOElemento {

    void modificaElemento(Elemento elemento, DAOElementoImpl.ModificaElementoCallbacks callback);

    void impostaAllergeni(Elemento elemento, ArrayList<Allergene> allergeni, DAOElementoImpl.ImpostaAllergeniCallbacks callback);

    void getElementiOpenFoodFactsDaStringa(String stringaIniziale, DAOElementoImpl.ElementiFoodFactsCallbacks callback);

    void insertElemento(Elemento elementoDaAggiungere, DAOElementoImpl.AggiuntaElementiCallbacks callback);

    void impostaPreparazione(HandlePreparazione preparazione, DAOElementoImpl.ImpostaPreparazioneCallbacks callback);

    void deleteElementi(EliminaElementiHandler handler, DAOElementoImpl.EliminaElementiCallbacks callbacks);

    void eliminaPreparazione(EliminaPreparazioniHandler preparazioni, DAOElementoImpl.EliminapreparazioneCallbacks callback);

}
