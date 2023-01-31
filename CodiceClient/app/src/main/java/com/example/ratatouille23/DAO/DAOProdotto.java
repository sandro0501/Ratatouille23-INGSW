package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;

public interface DAOProdotto {

    void getProdottiOpenFoodFactsDaStringa(String stringaIniziale, DAOProdottoImpl.ProdottoCallbacks callback);

    void aggiungiProdotto(Prodotto prodotto, DAOProdottoImpl.AggiuntaProdottoCallbacks callbacks);

    void getDispensa(Ristorante ristorante, DAOProdottoImpl.OttenimentoDispensaCallbacks callback);

}
