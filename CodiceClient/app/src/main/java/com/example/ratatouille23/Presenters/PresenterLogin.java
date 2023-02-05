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

    public void bottoneLoginPremuto (LoginActivity context, String email, String password) throws CampiVuotiException, CaratteriIllecitiException {

        if (email.isEmpty() || password.isEmpty()) throw new CampiVuotiException();
        else if (email.contains("'") || password.contains("'")) throw new CaratteriIllecitiException();

        Utente utenteCorrente = new Utente();
        utenteCorrente.setEmail(email);
        daoUtente.controllaDatiLogin(utenteCorrente, password, new DAOUtenteImpl.LoginCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                PresenterLogin.getInstance().mostraAlertErroreHTTP(context, response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                PresenterLogin.getInstance().mostraAlertErroreConnessione(context);
            }

            @Override
            public void onAccessoCorrettoUtente(Utente utenteControllato) {
                context.effettuaAccesso(utenteControllato);
            }

            @Override
            public void onPrimoAccessoUtente(Utente utente, String session) {
                context.effettuaPrimoAccessoUtente(utente, session);
            }

            @Override
            public void onAccessoErratoUtente() {
                context.mostraAlertAccessoErrato();
            }

            @Override
            public void onAccessoUtenteLicenziato() {
                PresenterLogin.getInstance().mostraAlert(context, "Errore!", "Sei stato licenziato, non puoi più accedere a questa applicazione!");
            }
        });

    }

    public void modificaPasswordPrimoLoginPremuto(PrimoLoginModificaPasswordActivity context, Utente utente, String sessione, String nuovaPassword, String nuovaPasswordConferma) throws CampiVuotiException, ConfermaPasswordErrataException, PasswordNonAdeguataException {
        if (nuovaPassword.isEmpty() || nuovaPasswordConferma.isEmpty()) throw new CampiVuotiException();
        else if (!nuovaPassword.equals(nuovaPasswordConferma)) throw new ConfermaPasswordErrataException();
        else if (!soddisfaRequisiti(nuovaPassword)) throw new PasswordNonAdeguataException();
        daoUtente.modificaPasswordPrimoLogin(utente, sessione, nuovaPassword, new DAOUtenteImpl.ModificaPasswordPrimoLoginCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getBaseContext(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getBaseContext());
            }

            @Override
            public void onModificaPasswordUtente(Utente utenteControllato) {
                context.passwordModificataCorrettamente(utenteControllato);
            }

            @Override
            public void onModificaPasswordUtenteLicenziato() {
                PresenterLogin.getInstance().mostraAlertFinishActivity(context, "Errore!", "Sei stato licenziato, non puoi più accedere a questa applicazione!");
            }
        });
    }

    public boolean soddisfaRequisiti(String nuovaPassword) {
        boolean flagMaiuscola = false;
        boolean flagMinuscola = false;
        boolean flagNumero = false;
        boolean flagSimbolo = false;
        String stringaSimboli = "^ $ * . [ ] { } ( ) ? @ # % & / , > < : ; | _ ~ = + - !";
        if (nuovaPassword.length() < 8) return false;
        for (int i = 0; i < nuovaPassword.length(); i++) {
            char carattereCorrente = nuovaPassword.charAt(i);
            if (Character.isUpperCase(carattereCorrente)) flagMaiuscola = true;
            if (Character.isLowerCase(carattereCorrente)) flagMinuscola = true;
            if (Character.isDigit(carattereCorrente)) flagNumero = true;
            if (stringaSimboli.indexOf(carattereCorrente) >= 0) flagSimbolo = true;
        }

        return flagMaiuscola && flagMinuscola && flagNumero && flagSimbolo;
    }

    public void bottoneRichiediCodicePremuto(String email, PasswordRecoveryActivity context)
    {
        Utente utente = new Utente();
        utente.setEmail(email);
        daoUtente.recuperaPassword(utente, new DAOUtenteImpl.RecuperaPasswordCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getBaseContext(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getBaseContext());
            }

            @Override
            public void onRichiestaCodice()
            {
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

        daoUtente.confermaPassword(handle, new DAOUtenteImpl.RecuperaPasswordCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getBaseContext(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getBaseContext());
            }

            @Override
            public void onRichiestaCodice() { }

            @Override
            public void onConfermaCodice()
            {
                context.tornaAlLogin();
            }

            @Override
            public void onCodiceErrato() {
                PresenterLogin.getInstance().mostraAlert(context, "Errore!", "Il codice inserito è errato!");
            }

        });

    }

}
