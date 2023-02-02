package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.EliminaProdottiHandler;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;

public interface DAOProdotto {

    void getProdottiOpenFoodFactsDaStringa(String stringaIniziale, DAOProdottoImpl.ProdottoCallbacks callback);

    void aggiungiProdotto(Prodotto prodotto, DAOProdottoImpl.AggiuntaProdottoCallbacks callback);

    void modificaProdotto(Prodotto prodotto, DAOProdottoImpl.ModificaProdottoCallbacks callback);

    void eliminaProdotto(EliminaProdottiHandler listaProdottiDaEliminare, DAOProdottoImpl.EliminazioneProdottoCallbacks callback);

    void getDispensa(Ristorante ristorante, DAOProdottoImpl.OttenimentoDispensaCallbacks callback);

}
