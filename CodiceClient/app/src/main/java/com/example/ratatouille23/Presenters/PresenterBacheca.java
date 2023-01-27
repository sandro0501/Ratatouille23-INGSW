package com.example.ratatouille23.Presenters;

import android.content.Intent;

import com.example.ratatouille23.DAO.DAOAvviso;
import com.example.ratatouille23.DAO.DAOAvvisoImpl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.BachecaFragment;
import com.example.ratatouille23.Views.CreazioneAvvisoActivity;

import java.util.ArrayList;

public class PresenterBacheca extends PresenterBase {

    private static PresenterBacheca instance;

    private DAOAvviso daoBacheca;


    static Boolean bachecaAttiva = true;

    private PresenterBacheca() {
        daoBacheca = DAOFactory.getInstance().getDAOAvviso();
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

    public void setAvvisi(BachecaFragment context, int uid)
    {
        daoBacheca.getAvvisi(uid, new DAOAvvisoImpl.BachecaCallbacks() {
            @Override
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti) {
                context.setAvvisiUtente(avvisiUtenteNuovi,avvisiUtenteLetti,avvisiutenteNascosti);
            }

            @Override
            public void onAggiuntaAvviso(Boolean added) { }
            public void onVisualizzaAvviso(){ }
        });
    }

    public void insertAvviso(CreazioneAvvisoActivity context, InserisciAvvisoHandler handler)
    {
        daoBacheca.insertAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks() {
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){ }

            public void onAggiuntaAvviso(Boolean added) {
                if(added)   context.mostraConfermaCreazioneAvvisoDialog();
                else context.mostraErroreCreazioneAvvisoDialog();
            }
            public void onVisualizzaAvviso(){ }
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

        });
    }

    public void nascondiAvviso(AggiornaAvvisoHandler handler)
    {
        daoBacheca.nascondiAvviso(handler, new DAOAvvisoImpl.BachecaCallbacks(){
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti){}

            public void onAggiuntaAvviso(Boolean added) { }

            public void onVisualizzaAvviso() { }


        });
    }
}
