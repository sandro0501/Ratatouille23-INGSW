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
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){ }

            public void onAggiuntaAvviso(Boolean added) {
                if (added) context.mostraConfermaCreazioneAvvisoDialog();
                else context.mostraErroreCreazioneAvvisoDialog();
            }
            public void onVisualizzaAvviso(){ }

            public void onNascondiAvviso() { }
        });
    }

    public void setUtentiCorrenti(Ristorante ristorante, BachecaFragment context)
    {
        daoUtente.ottieniDipendenti(ristorante, new DAOUtenteImpl.DipendentiCallbacks() {
            @Override
            public void onRichiestaDipendenti(ArrayList<Utente> utenti)
            {
                context.setUtentiCorrenti(utenti);
            }
        });
    }

    public void visualizzaAvviso(BachecaFragment context, Intent intentFromBachecaToVisualizzaAvviso, AggiornaAvvisoHandler handler)
    {

        daoBacheca.visualizzaAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks() {
        public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){ }

        public void onAggiuntaAvviso(Boolean added) { }

        public void onVisualizzaAvviso()
        {
            context.getContext().startActivity(intentFromBachecaToVisualizzaAvviso);
        }

        public void onNascondiAvviso() { }

        });
    }

    public void nascondiAvviso(BachecaFragment context, AggiornaAvvisoHandler handler)
    {
        daoBacheca.nascondiAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks(){
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
