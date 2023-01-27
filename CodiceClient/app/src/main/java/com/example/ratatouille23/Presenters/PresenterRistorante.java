package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Views.ModificaDettagliRistoranteActivity;
import com.example.ratatouille23.Views.RistoranteFragment;

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
        daoRistorante.modificaRistorante(ristoranteCorrente, new DAORistoranteImpl.RistoranteModificaCallbacks() {
            @Override
            public void onModificaRistorante() {
                context.modificheEffettuate();
            }
        });
    }

    public void riceviRistorante(RistoranteFragment context, int idRistorante) {
        daoRistorante.getRistorante(idRistorante, new DAORistoranteImpl.RistoranteRiceviCallbacks() {
            @Override
            public void onRicezioneRistorante(Ristorante ristorante) {
                context.setRistorante(ristorante);
            }
        });

    }
}
