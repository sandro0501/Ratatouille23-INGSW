package com.example.ratatouille23.Presenters;

import android.content.Intent;

import com.example.ratatouille23.DAO.DAOAvviso;
import com.example.ratatouille23.DAO.DAOAvvisoImpl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.BachecaFragment;
import com.example.ratatouille23.Views.CreazioneAvvisoActivity;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterBacheca extends PresenterBase {

    private static PresenterBacheca instance;

    private DAOAvviso daoBacheca;
    private DAOUtente daoUtente;

    static Boolean bachecaAttiva = true;

    private PresenterBacheca() {
        daoBacheca = DAOFactory.getInstance().getDAOAvviso();
        daoUtente = DAOFactory.getInstance().getDAOUtente();
    };
    public static PresenterBacheca getInstance(){

        if (instance==null) {
            instance = new PresenterBacheca();
        }
        return instance;

    }

    public Boolean getBachecaAttiva() {
        return bachecaAttiva;
    }

    public void setBachecaAttiva(Boolean valore) {
        bachecaAttiva = valore;
    }

    public void setAvvisi(BachecaFragment context, Utente utente)
    {
        daoBacheca.getAvvisi(utente, context,new DAOAvvisoImpl.BachecaCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti) {
                context.setAvvisiUtente(avvisiUtenteNuovi,avvisiUtenteLetti,avvisiutenteNascosti);
            }

            @Override
            public void onAggiuntaAvviso(Boolean added) { }

            public void onVisualizzaAvviso(){ }

            public void onNascondiAvviso() { }
        });
    }

    public void insertAvviso(CreazioneAvvisoActivity context, InserisciAvvisoHandler handler)
    {
        daoBacheca.insertAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context);
            }

            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){ }

            public void onAggiuntaAvviso(Boolean added) {
                if (added) context.mostraConfermaCreazioneAvvisoDialog();
                else context.mostraErroreCreazioneAvvisoDialog();
            }
            public void onVisualizzaAvviso(){ }

            public void onNascondiAvviso() { }
        });
    }

    public void setUtentiCorrenti(Ristorante ristorante, BachecaFragment context, Utente utenteCorrente)
    {
        daoUtente.ottieniDipendenti(ristorante, new DAOUtenteImpl.DipendentiCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onRichiestaDipendenti(ArrayList<Utente> utenti)
            {
                context.setUtentiCorrenti(utenti);

                setAvvisi(context, utenteCorrente);
            }
        });
    }

    public void visualizzaAvviso(BachecaFragment context, Intent intentFromBachecaToVisualizzaAvviso, AggiornaAvvisoHandler handler)
    {

        daoBacheca.visualizzaAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){ }

            public void onAggiuntaAvviso(Boolean added) { }

            public void onVisualizzaAvviso()
            {
                context.getActivity().startActivity(intentFromBachecaToVisualizzaAvviso);
            }

            public void onNascondiAvviso() { }

        });
    }

    public void nascondiAvviso(BachecaFragment context, AggiornaAvvisoHandler handler)
    {
        daoBacheca.nascondiAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks(){
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){}

            public void onAggiuntaAvviso(Boolean added) { }

            public void onVisualizzaAvviso() { }

            public void onNascondiAvviso()
            {
                PresenterBacheca.getInstance().setAvvisi(context, handler.utente);
            }

        });
    }

}
