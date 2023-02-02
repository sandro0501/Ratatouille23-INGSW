package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Handlers.AggiornaRuoloHandler;
import com.example.ratatouille23.Handlers.RegistraUtenteHandler;
import com.example.ratatouille23.Handlers.UtenteHandler;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.AggiuntaDipendenteActivity;
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
        daoUtente.ottieniDipendenti(ristorante, new DAOUtenteImpl.DipendentiCallbacks() {
            @Override
            public void onRichiestaDipendenti(ArrayList<Utente> listaDipendenti) {
                context.setListaDipendenti(listaDipendenti);
            }
        });
    }

    public void rimuoviDipendente(DipendenteFragment context, UtenteHandler utente) {
        daoUtente.rimuoviDipendente(utente, new DAOUtenteImpl.RimuoviDipendenteCallbacks() {
            @Override
            public void onEliminazioneDipendente() {
                context.dipendenteLicenziato();
            }
        });
    }

    public void aggiungiDipendente(AggiuntaDipendenteActivity context, RegistraUtenteHandler handler) {
        daoUtente.aggiungiDipendente(handler, new DAOUtenteImpl.AggiungiDipendenteCallbacks() {
            @Override
            public void onAggiuntaDipendente() {
                context.dipendenteAggiunto();
            }

            @Override
            public void onUtenteGiaPresente() {
                PresenterDipendenti.getInstance().mostraAlert(context, "Errore!", "L'email inserita è già utilizzata da un altro utente!");
            }

            @Override
            public void onUtenteLicenziatoPrecedentemente() {
                PresenterDipendenti.getInstance().mostraAlertFinishActivity(context, "Attenzione!", "L'utente lavorava per questo ristorante in passato e dovrà utilizzare la sua ultima password per accedere!");
            }
        });
    }

    public void modificaDipendente(DipendenteFragment context, AggiornaRuoloHandler handler) {
        daoUtente.modificaDipendente(handler, new DAOUtenteImpl.ModificaDipendenteCallbacks() {
            @Override
            public void onModificaDipendente() {
                context.ruoloDipendenteModificato();
            }
        });
    }
}
