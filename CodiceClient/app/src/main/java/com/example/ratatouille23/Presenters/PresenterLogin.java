package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.CaratteriIllecitiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Exceptions.PasswordNonAdeguataException;
import com.example.ratatouille23.Handlers.RecoverHandler;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.ConfermaCodiceActivity;
import com.example.ratatouille23.Views.PasswordRecoveryActivity;
import com.example.ratatouille23.Views.PrimoLoginModificaPasswordActivity;
import com.example.ratatouille23.Views.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterLogin extends PresenterBase {

    private DAOUtente daoUtente;

    private static PresenterLogin instance;

    private PresenterLogin() {
        daoUtente = DAOFactory.getInstance().getDAOUtente();
    };

    public static PresenterLogin getInstance(){

        if (instance==null) {
            instance = new PresenterLogin();
        }
        return instance;

    }

    public void bottoneLoginPremuto (LoginActivity context, String email, String password) throws CampiVuotiException {

        if (email.isEmpty() || password.isEmpty()) throw new CampiVuotiException();

        Utente utenteCorrente = new Utente();
        utenteCorrente.setEmail(email);
        PresenterLogin.getInstance().mostraAlertAttesaCaricamento(context);
        daoUtente.controllaDatiLogin(utenteCorrente, password, new DAOUtenteImpl.LoginCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                PresenterLogin.getInstance().mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                PresenterLogin.getInstance().mostraAlertErroreConnessione(context);
            }

            @Override
            public void onAccessoCorrettoUtente(Utente utenteControllato) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                context.effettuaAccesso(utenteControllato);
            }

            @Override
            public void onPrimoAccessoUtente(Utente utente, String session) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                context.effettuaPrimoAccessoUtente(utente, session);
            }

            @Override
            public void onAccessoErratoUtente() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                context.mostraAlertAccessoErrato();
            }

            @Override
            public void onAccessoUtenteLicenziato() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                PresenterLogin.getInstance().mostraAlert(context, "Errore!", "Sei stato licenziato, non puoi più accedere a questa applicazione!");
            }
        });

    }

    public void modificaPasswordPrimoLoginPremuto(PrimoLoginModificaPasswordActivity context, Utente utente, String sessione, String nuovaPassword, String nuovaPasswordConferma) throws CampiVuotiException, ConfermaPasswordErrataException, PasswordNonAdeguataException {
        if (nuovaPassword.isEmpty() || nuovaPasswordConferma.isEmpty()) throw new CampiVuotiException();
        else if (!nuovaPassword.equals(nuovaPasswordConferma)) throw new ConfermaPasswordErrataException();
        else if (!soddisfaRequisiti(nuovaPassword)) throw new PasswordNonAdeguataException();
        PresenterLogin.getInstance().mostraAlertAttesaCaricamento(context);
        daoUtente.modificaPasswordPrimoLogin(utente, sessione, nuovaPassword, new DAOUtenteImpl.ModificaPasswordPrimoLoginCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onModificaPasswordUtente(Utente utenteControllato) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                context.passwordModificataCorrettamente(utenteControllato);
            }

            @Override
            public void onModificaPasswordUtenteLicenziato() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                PresenterLogin.getInstance().mostraAlertFinishActivity(context, "Errore!", "Sei stato licenziato, non puoi più accedere a questa applicazione!");
            }
        });
    }

    public void bottoneRichiediCodicePremuto(String email, PasswordRecoveryActivity context)
    {
        Utente utente = new Utente();
        utente.setEmail(email);
        PresenterLogin.getInstance().mostraAlertAttesaCaricamento(context);
        daoUtente.recuperaPassword(utente, new DAOUtenteImpl.RecuperaPasswordCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onRichiestaCodice()
            {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                context.avviaConfermaCodice(email);
            }

            public void onConfermaCodice(){}

            @Override
            public void onCodiceErrato() {

            }

        });

    }


    public void bottoneResettaPasswordConCodicePremuto(String email, String codice, String password, String confermaPassword, ConfermaCodiceActivity context) throws PasswordNonAdeguataException, ConfermaPasswordErrataException, CampiVuotiException {
        RecoverHandler handle = new RecoverHandler();
        handle.code = codice;
        handle.password = password;
        handle.email = email;

        if (codice.isEmpty() || password.isEmpty() || confermaPassword.isEmpty()) throw new CampiVuotiException();
        if (!password.equals(confermaPassword)) throw new ConfermaPasswordErrataException();
        if (!soddisfaRequisiti(password)) throw new PasswordNonAdeguataException();
        PresenterLogin.getInstance().mostraAlertAttesaCaricamento(context);
        daoUtente.confermaPassword(handle, new DAOUtenteImpl.RecuperaPasswordCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                mostraAlertErroreConnessione(context);
            }

            @Override
            public void onRichiestaCodice() { }

            @Override
            public void onConfermaCodice()
            {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                context.tornaAlLogin();
            }

            @Override
            public void onCodiceErrato() {
                PresenterLogin.getInstance().nascondiAlertAttesaCaricamento();
                PresenterLogin.getInstance().mostraAlert(context, "Errore!", "Il codice inserito è errato!");
            }

        });

    }

}
