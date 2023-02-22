package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
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

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterDipendenti extends PresenterBase {

    private static PresenterDipendenti instance;
    private DAOUtente daoUtente;
    private DAORistorante daoRistorante;

    private PresenterDipendenti() {
        daoUtente = DAOFactory.getInstance().getDAOUtente();
        daoRistorante = DAOFactory.getInstance().getDAORistorante();
    };

    public static PresenterDipendenti getInstance(){

        if (instance==null) {
            instance = new PresenterDipendenti();
        }
        return instance;

    }

    public void recuperaDipendentiDaRistorante(DipendenteFragment context, Ristorante ristorante) {
        PresenterDipendenti.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoUtente.ottieniDipendenti(ristorante, new DAOUtenteImpl.DipendentiCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onRichiestaDipendenti(ArrayList<Utente> listaDipendenti) {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                context.setListaDipendenti(listaDipendenti);
            }
        });
    }

    public void rimuoviDipendente(DipendenteFragment context, UtenteHandler utente) {
        PresenterDipendenti.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoUtente.rimuoviDipendente(utente, new DAOUtenteImpl.RimuoviDipendenteCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onEliminazioneDipendente() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                context.dipendenteLicenziato();
            }
        });
    }

    public void aggiungiDipendente(AggiuntaDipendenteActivity context, RegistraUtenteHandler handler) {
        PresenterDipendenti.getInstance().mostraAlertAttesaCaricamento(context);
        daoUtente.aggiungiDipendente(handler, new DAOUtenteImpl.AggiungiDipendenteCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onAggiuntaDipendente() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                context.dipendenteAggiunto(handler.utente);
            }

            @Override
            public void onUtenteGiaPresente() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                PresenterDipendenti.getInstance().mostraAlert(context, "Errore!", "L'email inserita è già utilizzata da un altro utente!");
            }

            @Override
            public void onUtenteLicenziatoPrecedentemente() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                PresenterDipendenti.getInstance().mostraAlertFinishActivity(context, "Attenzione!", "L'utente lavorava per questo ristorante in passato e dovrà utilizzare la sua ultima password per accedere!");
            }
        });
    }

    public void modificaDipendente(DipendenteFragment context, AggiornaRuoloHandler handler) {
        PresenterDipendenti.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoUtente.modificaDipendente(handler, new DAOUtenteImpl.ModificaDipendenteCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onModificaDipendente() {
                PresenterDipendenti.getInstance().nascondiAlertAttesaCaricamento();
                context.ruoloDipendenteModificato();
            }
        });
    }

    public void aggiornaRistorante(DipendenteFragment context, int idRistorante) {
        daoRistorante.getRistorante(idRistorante, new DAORistoranteImpl.RistoranteRiceviCallbacks() {
            @Override
            public void onRicezioneRistorante(Ristorante ristorante) {
                context.setRistoranteCorrente(ristorante);
            }

            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {

            }

            @Override
            public void onErroreConnessioneGenerico() {

            }
        });
    }
}
