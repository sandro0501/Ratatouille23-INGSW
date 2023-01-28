package com.example.ratatouille23.Presenters;

import android.util.Log;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOProdotto;
import com.example.ratatouille23.DAO.DAOProdottoImpl;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Views.DispensaFragment;

import java.util.ArrayList;

public class PresenterDispensa extends PresenterBase {

    private static PresenterDispensa instance;

    private DAOProdotto daoProdotto;

    private PresenterDispensa() {
        daoProdotto = DAOFactory.getInstance().getDAOProdotto();
    };

    public static PresenterDispensa getInstance(){

        if (instance==null) {
            instance = new PresenterDispensa();
        }
        return instance;

    }

    public boolean controllaSogliaProdotto(ArrayList<Prodotto> dispensa, int posizioneProdottoInDispensa) {
        double quantitaProdotto;
        double sogliaProdotto;
        boolean isProdottoSottoSogliaLimite = false;

        quantitaProdotto = dispensa.get(posizioneProdottoInDispensa).getQuantita();
        sogliaProdotto = dispensa.get(posizioneProdottoInDispensa).getSoglia();

        if(quantitaProdotto < sogliaProdotto){
            isProdottoSottoSogliaLimite = true;
        }
        return isProdottoSottoSogliaLimite;
    }

    public void settaProdottiDaIniziale(DispensaFragment context, String stringaIniziale) {

        daoProdotto.getProdottiOpenFoodFactsDaStringa(stringaIniziale, new DAOProdottoImpl.ProdottoCallbacks() {
            @Override
            public void onCaricamentoListaProdottiOpenFoodFacts(ArrayList<Prodotto> listaProdottiOttenuta) {
                Log.i("Prova", listaProdottiOttenuta.toString());
                context.setupListaProdottiOpenFoodFacts(listaProdottiOttenuta);
            }
        });

    }
}
