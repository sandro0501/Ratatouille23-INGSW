package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.CaratteriIllecitiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.PrimoLoginModificaPasswordActivity;
import com.example.ratatouille23.Views.LoginActivity;

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
        });

    }

    public void modificaPasswordPrimoLoginPremuto(PrimoLoginModificaPasswordActivity context, Utente utente, String sessione, String nuovaPassword, String nuovaPasswordConferma) throws CampiVuotiException, ConfermaPasswordErrataException {
        if (nuovaPassword.isEmpty() || nuovaPasswordConferma.isEmpty()) throw new CampiVuotiException();
        else if (!nuovaPassword.equals(nuovaPasswordConferma)) throw new ConfermaPasswordErrataException();
        daoUtente.modificaPasswordPrimoLogin(utente, sessione, nuovaPassword, new DAOUtenteImpl.ModificaPasswordPrimoLoginCallbacks() {
            @Override
            public void onModificaPasswordUtente(Utente utenteControllato) {
                context.passwordModificataCorrettamente(utenteControllato);
            }
        });
    }

    public boolean bottoneRichiediCodicePremuto(String email) {

        return true;
    }


    public boolean bottoneResettaPasswordConCodicePremuto(String codice, String password) {
        return true;
    }

}
