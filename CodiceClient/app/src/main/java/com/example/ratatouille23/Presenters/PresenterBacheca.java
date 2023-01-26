package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOBacheca;
import com.example.ratatouille23.DAO.DAOBachecaImpl;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.BachecaFragment;

import java.util.ArrayList;

public class PresenterBacheca extends PresenterBase {

    private static PresenterBacheca instance;

    private DAOBacheca daoBacheca;

    static Boolean bachecaAttiva = true;

    private PresenterBacheca() { };

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
        daoBacheca.getAvvisiUtenteCorrente(utente, new DAOBachecaImpl.BachecaCallbacks() {
            @Override
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti) {
                context.setAvvisiUtente(avvisiUtenteNuovi,avvisiUtenteLetti,avvisiutenteNascosti);
            }
        });
    }

}
