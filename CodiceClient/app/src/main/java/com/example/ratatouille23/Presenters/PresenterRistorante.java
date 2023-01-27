package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Views.ModificaDettagliRistoranteActivity;

public class PresenterRistorante extends PresenterBase {

    private static PresenterRistorante instance;

    private DAORistorante daoRistorante;

    private PresenterRistorante() {
        daoRistorante = DAOFactory.getInstance().getDAORistorante();
    };

    public static PresenterRistorante getInstance(){

        if (instance==null) {
            instance = new PresenterRistorante();
        }
        return instance;

    }

    public void confermaModifichePremuto(ModificaDettagliRistoranteActivity context, Ristorante ristoranteCorrente) {
        daoRistorante.modificaRistorante(ristoranteCorrente, new DAORistoranteImpl.RistoranteCallbacks() {
            @Override
            public void onModificaRistorante(boolean successo) {
                context.modificheEffettuate(successo);
            }
        });
    }
}
