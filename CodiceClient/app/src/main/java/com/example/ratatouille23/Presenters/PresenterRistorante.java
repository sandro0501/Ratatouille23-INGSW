package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Views.ModificaDettagliRistoranteActivity;
import com.example.ratatouille23.Views.RistoranteFragment;

import okhttp3.ResponseBody;
import retrofit2.Response;

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
        PresenterRistorante.getInstance().mostraAlertAttesaCaricamento(context);
        daoRistorante.modificaRistorante(ristoranteCorrente, new DAORistoranteImpl.RistoranteModificaCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterRistorante.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterRistorante.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onModificaRistorante() {
                PresenterRistorante.getInstance().nascondiAlertAttesaCaricamento();
                context.modificheEffettuate();
            }
        });
    }

    public void riceviRistorante(RistoranteFragment context, int idRistorante) {
        PresenterRistorante.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoRistorante.getRistorante(idRistorante, new DAORistoranteImpl.RistoranteRiceviCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterRistorante.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterRistorante.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onRicezioneRistorante(Ristorante ristorante) {
                PresenterRistorante.getInstance().nascondiAlertAttesaCaricamento();
                context.setRistorante(ristorante);
            }
        });

    }
}
