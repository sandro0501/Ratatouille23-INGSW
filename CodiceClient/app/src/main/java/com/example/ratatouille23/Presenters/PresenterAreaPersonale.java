package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAORistorante;
import com.example.ratatouille23.DAO.DAORistoranteImpl;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Exceptions.PasswordNonAdeguataException;
import com.example.ratatouille23.Exceptions.PasswordUgualeException;
import com.example.ratatouille23.Handlers.ModificaPasswordHandler;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Views.ModificaPasswordActivity;
import com.example.ratatouille23.Views.ProfiloFragment;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterAreaPersonale extends PresenterBase {

    private static PresenterAreaPersonale instance;
    private DAOUtente daoUtente;
    private DAORistorante daoRistorante;

    private PresenterAreaPersonale() {
        daoUtente = DAOFactory.getInstance().getDAOUtente();
        daoRistorante = DAOFactory.getInstance().getDAORistorante();
    };

    public static PresenterAreaPersonale getInstance(){

        if (instance==null) {
            instance = new PresenterAreaPersonale();
        }
        return instance;

    }

    public void modificaPasswordPremuto(ModificaPasswordActivity context, String accessKey, String vecchiaPassword, String nuovaPassword, String confermaPassword) throws ConfermaPasswordErrataException, CampiVuotiException, PasswordUgualeException, PasswordNonAdeguataException {

        controllaPassword(vecchiaPassword, nuovaPassword, confermaPassword);
        PresenterAreaPersonale.getInstance().mostraAlertAttesaCaricamento(context);
        ModificaPasswordHandler handler = new ModificaPasswordHandler();
        handler.accessToken = accessKey;
        handler.old = vecchiaPassword;
        handler.newp = nuovaPassword;

        daoUtente.modificaPassword(handler, new DAOUtenteImpl.ModificaPasswordCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterAreaPersonale.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterAreaPersonale.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onModificaPassword() {
                PresenterAreaPersonale.getInstance().nascondiAlertAttesaCaricamento();
                context.passwordModificataCorrettamente();
            }

            @Override
            public void onVecchiaPasswordErrata() {
                PresenterAreaPersonale.getInstance().nascondiAlertAttesaCaricamento();
                PresenterAreaPersonale.getInstance().mostraAlert(context, "Errore!", "La password inserita è errata!");
            }
        });

    }

    private void controllaPassword(String vecchiaPassword, String nuovaPassword, String confermaPassword) throws PasswordUgualeException, CampiVuotiException, PasswordNonAdeguataException, ConfermaPasswordErrataException {
        if (vecchiaPassword.isEmpty() || nuovaPassword.isEmpty() || confermaPassword.isEmpty()) throw new CampiVuotiException();
        if (!nuovaPassword.equals(confermaPassword)) throw new ConfermaPasswordErrataException();
        if (vecchiaPassword.equals(nuovaPassword)) throw new PasswordUgualeException();
        if (!soddisfaRequisiti(nuovaPassword)) throw new PasswordNonAdeguataException();
    }

    public void aggiornaRistorante(ProfiloFragment context, int idRistorante) {
        daoRistorante.getRistorante(idRistorante, new DAORistoranteImpl.RistoranteRiceviCallbacks() {
            @Override
            public void onRicezioneRistorante(Ristorante ristorante) {
                context.setRistoranteCorrente(ristorante);
            }

            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {

            }

            @Override
            public void onErroreConnessioneGenerico() {

            }
        });
    }
}
