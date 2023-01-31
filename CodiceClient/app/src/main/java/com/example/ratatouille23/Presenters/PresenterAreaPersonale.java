package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOUtente;
import com.example.ratatouille23.DAO.DAOUtenteImpl;
import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Exceptions.PasswordUgualeException;
import com.example.ratatouille23.Handlers.ModificaPasswordHandler;
import com.example.ratatouille23.Views.ModificaPasswordActivity;

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

    public void modificaPasswordPremuto(ModificaPasswordActivity context, String accessKey, String vecchiaPassword, String nuovaPassword, String confermaPassword) throws ConfermaPasswordErrataException, CampiVuotiException, PasswordUgualeException {

        if (!vecchiaPassword.isEmpty() && !nuovaPassword.isEmpty() && !confermaPassword.isEmpty()) {
            if (nuovaPassword.equals(confermaPassword)){
                if (!vecchiaPassword.equals(nuovaPassword)) {

                    ModificaPasswordHandler handler = new ModificaPasswordHandler();
                    handler.accessToken = accessKey;
                    handler.old = vecchiaPassword;
                    handler.newp = nuovaPassword;

                    daoUtente.modificaPassword(handler, new DAOUtenteImpl.ModificaPasswordCallbacks() {
                        @Override
                        public void onModificaPassword() {
                            context.passwordModificataCorrettamente();
                        }
                    });

                }
                else throw new PasswordUgualeException();
            }
            else throw new ConfermaPasswordErrataException();
        }
        else throw new CampiVuotiException();


    }

}
