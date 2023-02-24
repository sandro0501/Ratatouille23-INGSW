package com.example.ratatouille23.Presenters;

import android.util.Log;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOProdotto;
import com.example.ratatouille23.DAO.DAOProdottoImpl;
import com.example.ratatouille23.Exceptions.ProdottoMalFormatoException;
import com.example.ratatouille23.Handlers.EliminaProdottiHandler;
import com.example.ratatouille23.InterfacceMock.IProdotto;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Views.DispensaFragment;
import com.example.ratatouille23.Views.VisualizzazioneIngredientiElementoActivity;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterDispensa extends PresenterBase {

    private static PresenterDispensa instance;

    private DAOProdotto daoProdotto;

    private PresenterDispensa() {
        daoProdotto = DAOFactory.getInstance().getDAOProdotto();
    };

    public static PresenterDispensa getInstance(){

        if (instance==null) {
            instance = new PresenterDispensa();
        }
        return instance;

    }

    public boolean controllaSogliaProdotto(ArrayList<? extends IProdotto> dispensa, int posizioneProdottoInDispensa) {
        double quantitaProdotto;
        double sogliaProdotto;

        quantitaProdotto = dispensa.get(posizioneProdottoInDispensa).getQuantita();
        sogliaProdotto = dispensa.get(posizioneProdottoInDispensa).getSoglia();

        if (sogliaProdotto < 0 || quantitaProdotto < 0) throw new ProdottoMalFormatoException();

        return quantitaProdotto < sogliaProdotto;
    }

    public void settaProdottiDaIniziale(DispensaFragment context, String stringaIniziale) {
        daoProdotto.getProdottiOpenFoodFactsDaStringa(stringaIniziale, new DAOProdottoImpl.ProdottoCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onCaricamentoListaProdottiOpenFoodFacts(ArrayList<Prodotto> listaProdottiOttenuta) {
                Log.i("Prova", listaProdottiOttenuta.toString());
                context.setupListaProdottiOpenFoodFacts(listaProdottiOttenuta);
            }
        });

    }

    public void ottieniDispensaPerInserimentoIngredientiElementoRistorante(VisualizzazioneIngredientiElementoActivity context, Ristorante ristorante)
    {
        PresenterDispensa.getInstance().mostraAlertAttesaCaricamento(context);
        daoProdotto.getDispensa(ristorante, new DAOProdottoImpl.OttenimentoDispensaCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onRichiestaDispensa(ArrayList<Prodotto> listaProdotti) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                context.riempiDispensa(listaProdotti);
            }
        });
    }

    public void AggiungiProdottoInDispensa(DispensaFragment context, Prodotto prodottoAggiunto) {
        PresenterDispensa.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoProdotto.aggiungiProdotto(prodottoAggiunto, new DAOProdottoImpl.AggiuntaProdottoCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onAggiuntaProdotto(Boolean isAggiunto) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                if(isAggiunto){
                    context.mostraDialogConfermaInserimentoProdotto();
                } else {
                    context.mostraDialogErroreInserimentoProdotto();
                }
            }
        });
    }

    public void ModificaProdottoInDispensa (DispensaFragment context, Prodotto prodottoDaModificare){
        PresenterDispensa.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoProdotto.modificaProdotto(prodottoDaModificare, new DAOProdottoImpl.ModificaProdottoCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onModificaProdotto() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                context.prodottoInDispensaModificato();
            }
        });
    }

    public void EliminaProdottoInDispensa (DispensaFragment context, EliminaProdottiHandler listaProdottiDaEliminare){
        PresenterDispensa.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoProdotto.eliminaProdotto(listaProdottiDaEliminare, new DAOProdottoImpl.EliminazioneProdottoCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onEliminazioneProdotto() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                context.prodottoInDispensaEliminato();
            }
        });
    }

    public void ottieniDispensaDaRistorante(DispensaFragment context, Ristorante ristorante){
        PresenterDispensa.getInstance().mostraAlertAttesaCaricamento(context.getActivity());
        daoProdotto.getDispensa(ristorante, new DAOProdottoImpl.OttenimentoDispensaCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onRichiestaDispensa(ArrayList<Prodotto> listaProdotti) {
                PresenterDispensa.getInstance().nascondiAlertAttesaCaricamento();
                context.riempiDispensa(listaProdotti);
            }
        });
    }

    public void settaProdottiDaInizialeModifica(DispensaFragment context, String stringaIniziale) {

        daoProdotto.getProdottiOpenFoodFactsDaStringa(stringaIniziale, new DAOProdottoImpl.ProdottoCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getActivity(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getActivity());
            }

            @Override
            public void onCaricamentoListaProdottiOpenFoodFacts(ArrayList<Prodotto> listaProdottiOttenuta) {
                Log.i("Prova", listaProdottiOttenuta.toString());
                context.setupListaProdottiOpenFoodFactsModifica(listaProdottiOttenuta);
            }
        });

    }
}
