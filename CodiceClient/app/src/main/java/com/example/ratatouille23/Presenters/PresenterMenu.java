package com.example.ratatouille23.Presenters;

import android.media.audiofx.PresetReverb;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.DAO.DAOElemento;
import com.example.ratatouille23.DAO.DAOElementoImpl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.DAO.DAOSezioneMenu;
import com.example.ratatouille23.DAO.DAOSezioneMenuImpl;
import com.example.ratatouille23.Handlers.EliminaElementiHandler;
import com.example.ratatouille23.Handlers.EliminaPreparazioniHandler;
import com.example.ratatouille23.Handlers.EliminaSezioniHandler;
import com.example.ratatouille23.Handlers.HandlePreparazione;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Views.MenuFragment;
import com.example.ratatouille23.Views.VisualizzazioneIngredientiElementoActivity;

import java.io.File;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;

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
            public void onErroreDiHTTP(Response<ResponseBody> response) {
            }

            @Override
            public void onErroreConnessioneGenerico() {
            }

            @Override
            public void onRicezioneRistorante(Ristorante ristorante) {
                menuFragment.setRistoranteCorrente(ristorante);
            }
        });
    }

    public void estraiMenu(MenuFragment context, Ristorante ristoranteCorrente) {
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoSezioneMenu.estraiMenu(ristoranteCorrente.getIdRistorante(), new DAOSezioneMenuImpl.EstraiMenuCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onEstratto(ArrayList<SezioneMenu> listaSezioni) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.setListaSezioni(listaSezioni);
            }
        });
    }



    public void settaElementiDaIniziale(MenuFragment context, String stringaIniziale) {
        daoElemento.getElementiOpenFoodFactsDaStringa(stringaIniziale, new DAOElementoImpl.ElementiFoodFactsCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onCaricamentoListaElementiOpenFoodFacts(ArrayList<Elemento> listaElementiOttenuta) {
                Log.i("Prova", listaElementiOttenuta.toString());
                context.setupListaElementiOpenFoodFacts(listaElementiOttenuta);
            }
        });
    }

    public void aggiungiSezione(MenuFragment context, SezioneMenu sezione) {
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoSezioneMenu.aggiungiSezioneMenu(sezione, new DAOSezioneMenuImpl.AggiungiSezioneCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onAggiuntaSezione() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.aggiornaMenu();
            }
        });
    }

    public void modificaSezione(MenuFragment context, SezioneMenu sezione)
    {
        daoSezioneMenu.modificaSezioneMenu(sezione, new DAOSezioneMenuImpl.ModificaSezioneCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onModificato() {

            }
        });
    }

    public void rimuoviSezione(MenuFragment context, SezioneMenu sezione) {

        for (Elemento elemento : sezione.getAppartenente())
            rimuoviImmagine(context, elemento);

        EliminaSezioniHandler handler = new EliminaSezioniHandler();
        ArrayList<SezioneMenu> listaSezioni = new ArrayList<>();
        listaSezioni.add(sezione);
        handler.sezioni = listaSezioni;
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoSezioneMenu.rimuoviSezioneMenu(handler, new DAOSezioneMenuImpl.RimuoviSezioneCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onRimozioneSezione() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.mostraAlertEliminazioneSezioneEffettuata();
                context.aggiornaMenu();
            }
        });
    }

    private void rimuoviImmagine(MenuFragment context, Elemento elemento) {

        Amplify.Storage.remove(
                ((Integer)elemento.getIdElemento()).toString()+"_LogoElemento.jpg",
                result -> Log.i("MyAmplifyApp", "Successfully removed"),
                error -> Log.e("MyAmplifyApp", "Remove failure", error)
        );

        String pathLocale = (context.getContext().getFilesDir() + "/" + ((Integer)elemento.getIdElemento()).toString()+"_LogoElemento.jpg");

        File fdelete = new File(pathLocale);
        if (fdelete.exists()) {
            fdelete.delete();
        }

    }

    public void aggiungiElemento(MenuFragment context, Elemento elemento) {
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoElemento.insertElemento(elemento, new DAOElementoImpl.AggiuntaElementiCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onAggiuntaElemento(Elemento elwithid) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                impostaAllergeni(elwithid, elemento.getPresenta(), context, false);
            }
        });
    }

    public void modificaElemento(Elemento elemento, ArrayList<Allergene> allergeni, MenuFragment context){
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoElemento.modificaElemento(elemento, new DAOElementoImpl.ModificaElementoCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onModificato() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                impostaAllergeni(elemento, allergeni, context, true);
            }
        });
    }

    public void impostaAllergeni(Elemento elemento, ArrayList<Allergene> allergeni, MenuFragment context,boolean modifica) {
        daoElemento.impostaAllergeni(elemento, allergeni, new DAOElementoImpl.ImpostaAllergeniCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onImpostati() {
                if(modifica)
                    context.elementoModificato();
                else
                    context.elementoAggiuntoCorrettamente(elemento);
            }
        });

    }

    public void impostaPreparazione(Elemento elemento, Prodotto prodotto, double quantita, VisualizzazioneIngredientiElementoActivity context)
    {
        HandlePreparazione handle = new HandlePreparazione();
        handle.idElemento = elemento;
        handle.idProdotto = prodotto;
        handle.quantita = quantita;
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context);
        daoElemento.impostaPreparazione(handle, new DAOElementoImpl.ImpostaPreparazioneCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onImpostata(boolean esito) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.tentativoAggiuntaIngrediente(esito);
            }
        });
    }


    public void settaPosizioniMenu(MenuFragment context, ArrayList<SezioneMenu> listaSezioni) {
        for (SezioneMenu sezione : listaSezioni) {

            daoSezioneMenu.modificaSezioneMenu(sezione, new DAOSezioneMenuImpl.ModificaSezioneCallbacks() {
                @Override
                public void onErroreDiHTTP(Response<ResponseBody> response) {
                }

                @Override
                public void onErroreConnessioneGenerico() {
                }

                @Override
                public void onModificato() {

                }
            });

            for (Elemento elemento : sezione.getAppartenente()) {
                daoElemento.modificaElemento(elemento, new DAOElementoImpl.ModificaElementoCallbacks() {
                    @Override
                    public void onErroreDiHTTP(Response<ResponseBody> response) {
                    }

                    @Override
                    public void onErroreConnessioneGenerico() {
                    }

                    @Override
                    public void onModificato() {

                    }
                });
            }
        }

    }

    public void eliminaElementi(MenuFragment context, ArrayList<Elemento> listaElementi) {

        for (Elemento elemento : listaElementi)
            rimuoviImmagine(context, elemento);

        EliminaElementiHandler handler = new EliminaElementiHandler(listaElementi);
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoElemento.deleteElementi(handler, new DAOElementoImpl.EliminaElementiCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onEliminazioneElementi() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.mostraAlertEliminazioneElementoEffettuata();
                context.aggiornaMenu();
            }
        });
    }

    public void eliminaPreparazione(EliminaPreparazioniHandler handle, VisualizzazioneIngredientiElementoActivity context)
    {
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context);
        daoElemento.eliminaPreparazione(handle, new DAOElementoImpl.EliminapreparazioneCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onEliminati(boolean esito) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.tentativoRimozione(esito);
            }
        });
    }

    public void estraiIngredientiElemento(VisualizzazioneIngredientiElementoActivity context, Elemento elemento) {
        PresenterMenu.getInstance().mostraAlertAttesaCaricamento(context);
        daoSezioneMenu.estraiMenu(elemento.getAppartiene().getRistorante().getIdRistorante(), new DAOSezioneMenuImpl.EstraiMenuCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onEstratto(ArrayList<SezioneMenu> listaSezioni) {
                Elemento elementoAggiornato = null;
                for (SezioneMenu sezione : listaSezioni) {
                    for (Elemento elementoCorrente : sezione.getAppartenente()) {
                        if (elementoCorrente.getIdElemento() == elemento.getIdElemento())
                            elementoAggiornato = elementoCorrente;
                    }
                }

                PresenterMenu.getInstance().nascondiAlertAttesaCaricamento();
                context.aggiornaIngredientiElemento(elementoAggiornato);

            }
        });
    }
}
