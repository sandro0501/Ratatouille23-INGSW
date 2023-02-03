package com.example.ratatouille23.Presenters;

import android.util.Log;
import android.view.Menu;

import com.example.ratatouille23.DAO.DAOElemento;
import com.example.ratatouille23.DAO.DAOElementoImpl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.DAO.DAOSezioneMenu;
import com.example.ratatouille23.DAO.DAOSezioneMenuImpl;
import com.example.ratatouille23.Handlers.EliminaPreparazioniHandler;
import com.example.ratatouille23.Handlers.EliminaSezioniHandler;
import com.example.ratatouille23.Handlers.HandlePreparazione;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.listaAllergeni;
import com.example.ratatouille23.Views.MenuFragment;
import com.example.ratatouille23.Views.VisualizzazioneIngredientiElementoActivity;

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

    public void aggiungiSezione(MenuFragment context, SezioneMenu sezione) {
        daoSezioneMenu.aggiungiSezioneMenu(sezione, new DAOSezioneMenuImpl.AggiungiSezioneCallbacks() {
            @Override
            public void onAggiuntaSezione() {
                context.aggiornaMenu();
            }
        });
    }

    public void modificaSezione(SezioneMenu sezione)
    {
        daoSezioneMenu.modificaSezioneMenu(sezione, new DAOSezioneMenuImpl.ModificaSezioneCallbacks() {
            @Override
            public void onModificato() {

            }
        });
    }

    public void rimuoviSezione(MenuFragment context, SezioneMenu sezione) {
        EliminaSezioniHandler handler = new EliminaSezioniHandler();
        ArrayList<SezioneMenu> listaSezioni = new ArrayList<>();
        listaSezioni.add(sezione);
        handler.sezioni = listaSezioni;
        daoSezioneMenu.rimuoviSezioneMenu(handler, new DAOSezioneMenuImpl.RimuoviSezioneCallbacks() {
            @Override
            public void onRimozioneSezione() {
                context.aggiornaMenu();
            }
        });
    }

    public void aggiungiElemento(MenuFragment context, Elemento elemento) {
        daoElemento.insertElemento(elemento, new DAOElementoImpl.AggiuntaElementiCallbacks() {
            @Override
            public void onAggiuntaElemento(Elemento elwithid) {
               impostaAllergeni(elwithid, elemento.getPresenta(), context, false);
            }
        });
    }

    public void modificaElemento(Elemento elemento, ArrayList<Allergene> allergeni, MenuFragment context){
        daoElemento.modificaElemento(elemento, new DAOElementoImpl.ModificaElementoCallbacks() {
            @Override
            public void onModificato() {
                impostaAllergeni(elemento, allergeni, context, true);
            }
        });
    }

    public void impostaAllergeni(Elemento elemento, ArrayList<Allergene> allergeni, MenuFragment context,boolean modifica) {
        daoElemento.impostaAllergeni(elemento, allergeni, new DAOElementoImpl.ImpostaAllergeniCallbacks() {
            @Override
            public void onImpostati() {
                if(modifica)
                    context.elementoModificato();
                else
                    context.elementoAggiuntoCorrettamente();
            }
        });

    }

    public void impostaPreparazione(Elemento elemento, Prodotto prodotto, double quantita, VisualizzazioneIngredientiElementoActivity context)
    {
        HandlePreparazione handle = new HandlePreparazione();
        handle.idElemento = elemento;
        handle.idProdotto = prodotto;
        handle.quantita = quantita;
        daoElemento.impostaPreparazione(handle, new DAOElementoImpl.ImpostaPreparazioneCallbacks() {
            @Override
            public void onImpostata(boolean esito) {
                context.tentativoImpostato(esito);
            }
        });
    }


    public void eliminaPreparazione(EliminaPreparazioniHandler handle, VisualizzazioneIngredientiElementoActivity context)
    {
        daoElemento.eliminaPreparazione(handle, new DAOElementoImpl.EliminapreparazioneCallbacks() {
            @Override
            public void onEliminati(boolean esito) {
                context.tentativoRimozione(esito);
            }
        });
    }

}
