package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Exceptions.PasswordNonAdeguataException;
import com.example.ratatouille23.Exceptions.PasswordUgualeException;
import com.example.ratatouille23.Handlers.ModificaPasswordHandler;
import com.example.ratatouille23.Views.ModificaPasswordActivity;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterAreaPersonale extends PresenterBase {

    private static PresenterAreaPersonale instance;
    private DAOUtente daoUtente;

    private PresenterAreaPersonale() {
        daoUtente = DAOFactory.getInstance().getDAOUtente();
    };

    public static PresenterAreaPersonale getInstance(){

        if (instance==null) {
            instance = new PresenterAreaPersonale();
        }
        return instance;

    }

    public void modificaPasswordPremuto(ModificaPasswordActivity context, String accessKey, String vecchiaPassword, String nuovaPassword, String confermaPassword) throws ConfermaPasswordErrataException, CampiVuotiException, PasswordUgualeException, PasswordNonAdeguataException {

        if (vecchiaPassword.isEmpty() || nuovaPassword.isEmpty() || confermaPassword.isEmpty()) throw new PasswordUgualeException();
        if (!nuovaPassword.equals(confermaPassword)) throw new CampiVuotiException();
        if (vecchiaPassword.equals(nuovaPassword)) throw new PasswordUgualeException();
        if (!PresenterLogin.getInstance().soddisfaRequisiti(nuovaPassword)) throw new PasswordNonAdeguataException();

        ModificaPasswordHandler handler = new ModificaPasswordHandler();
        handler.accessToken = accessKey;
        handler.old = vecchiaPassword;
        handler.newp = nuovaPassword;

        daoUtente.modificaPassword(handler, new DAOUtenteImpl.ModificaPasswordCallbacks() {
            @Override
            public void onErroreDiHTTP(Response<ResponseBody> response) {
                mostraAlertErroreHTTP(context.getBaseContext(), response);
            }

            @Override
            public void onErroreConnessioneGenerico() {
                mostraAlertErroreConnessione(context.getBaseContext());
            }

            @Override
            public void onModificaPassword() {
                context.passwordModificataCorrettamente();
            }

            @Override
            public void onVecchiaPasswordErrata() {
                PresenterAreaPersonale.getInstance().mostraAlert(context, "Errore!", "La password inserita è errata!");
            }
        });

    }

}
