package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.DipendenteFragment;

import java.util.ArrayList;

public class PresenterDipendenti extends PresenterBase {

    private static PresenterDipendenti instance;
    private DAOUtente daoUtente;

    private PresenterDipendenti() {
        daoUtente = DAOFactory.getInstance().getDAOUtente();
    };

    public static PresenterDipendenti getInstance(){

        if (instance==null) {
            instance = new PresenterDipendenti();
        }
        return instance;

    }

    public void recuperaDipendentiDaRistorante(DipendenteFragment context, Ristorante ristorante) {
        daoUtente.recuperaDipendentiRistorante(ristorante, new DAOUtenteImpl.RecuperaDipendentiCallbacks() {
            @Override
            public void onDipendentiRicevuti(ArrayList<Utente> listaDipendenti) {
                context.setListaDipendenti(listaDipendenti);
            }
        });
    }
}
