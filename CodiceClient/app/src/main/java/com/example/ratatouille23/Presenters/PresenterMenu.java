package com.example.ratatouille23.Presenters;

import android.util.Log;

import com.example.ratatouille23.DAO.DAOElemento;
import com.example.ratatouille23.DAO.DAOElementoImpl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.DAO.DAOSezioneMenu;
import com.example.ratatouille23.DAO.DAOSezioneMenuImpl;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Views.MenuFragment;

import java.util.ArrayList;

public class PresenterMenu extends PresenterBase {

    private static PresenterMenu instance;

    private DAOSezioneMenu daoSezioneMenu;
    private DAORistorante daoRistorante;
    private DAOElemento daoElemento;

    private PresenterMenu() {
        daoSezioneMenu = DAOFactory.getInstance().getDAOSezioneMenu();
        daoRistorante = DAOFactory.getInstance().getDAORistorante();
        daoElemento = DAOFactory.getInstance().getDAOElemento();
    };

    public static PresenterMenu getInstance(){

        if (instance==null) {
            instance = new PresenterMenu();
        }
        return instance;

    }

    public void aggiornaDatiRistorante(MenuFragment menuFragment, int idRistorante) {
        daoRistorante.getRistorante(idRistorante, new DAORistoranteImpl.RistoranteRiceviCallbacks() {
            @Override
            public void onRicezioneRistorante(Ristorante ristorante) {
                menuFragment.setRistoranteCorrente(ristorante);
            }
        });
    }

    public void estraiMenu(MenuFragment context, Ristorante ristoranteCorrente) {
        daoSezioneMenu.estraiMenu(ristoranteCorrente.getIdRistorante(), new DAOSezioneMenuImpl.EstraiMenuCallbacks() {
            @Override
            public void onEstratto(ArrayList<SezioneMenu> listaSezioni) {
                context.setListaSezioni(listaSezioni);
            }
        });
    }



    public void settaElementiDaIniziale(MenuFragment context, String stringaIniziale) {
        daoElemento.getElementiOpenFoodFactsDaStringa(stringaIniziale, new DAOElementoImpl.ElementiFoodFactsCallbacks() {
            @Override
            public void onCaricamentoListaElementiOpenFoodFacts(ArrayList<Elemento> listaElementiOttenuta) {
                Log.i("Prova", listaElementiOttenuta.toString());
                context.setupListaElementiOpenFoodFacts(listaElementiOttenuta);
            }
        });
    }
}
