package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Utente;

public interface DAOUtente {

    void controllaDatiLogin(Utente utenteCorrente, String password, DAOUtenteImpl.LoginCallbacks callback);

    void modificaPasswordPrimoLogin(Utente utente, String session, String nuovaPassword, DAOUtenteImpl.ModificaPasswordPrimoLoginCallbacks callback);
}
