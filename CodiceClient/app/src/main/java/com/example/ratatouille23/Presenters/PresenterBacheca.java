package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOAvviso;
import com.example.ratatouille23.DAO.DAOAvvisoImpl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.BachecaFragment;

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

    public void setAvvisi(BachecaFragment context, Utente utente)
    {
        daoBacheca.getAvvisi(utente, new DAOAvvisoImpl.BachecaCallbacks() {
            @Override
            public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiutenteNascosti) {
                context.setAvvisiUtente(avvisiUtenteNuovi,avvisiUtenteLetti,avvisiutenteNascosti);
            }
        });
    }

}
