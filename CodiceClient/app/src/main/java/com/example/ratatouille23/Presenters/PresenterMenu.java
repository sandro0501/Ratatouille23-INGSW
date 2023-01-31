package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.DAO.DAOSezioneMenu;
import com.example.ratatouille23.DAO.DAOSezioneMenuImpl;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Views.MenuFragment;

import java.util.ArrayList;

public class PresenterMenu extends PresenterBase {

    private static PresenterMenu instance;

    private DAOSezioneMenu daoSezioneMenu;
    private DAORistorante daoRistorante;

    private PresenterMenu() {
        daoSezioneMenu = DAOFactory.getInstance().getDAOSezioneMenu();
        daoRistorante = DAOFactory.getInstance().getDAORistorante();
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
}
