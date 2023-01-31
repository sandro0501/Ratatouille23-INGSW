package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.AggiornaRuoloHandler;
import com.example.ratatouille23.Handlers.ModificaPasswordHandler;
import com.example.ratatouille23.Handlers.RecoverHandler;
import com.example.ratatouille23.Handlers.RegistraUtenteHandler;
import com.example.ratatouille23.Handlers.UtenteHandler;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;

public interface DAOUtente {

    void controllaDatiLogin(Utente utenteCorrente, String password, DAOUtenteImpl.LoginCallbacks callback);

    void modificaPasswordPrimoLogin(Utente utente, String session, String nuovaPassword, DAOUtenteImpl.ModificaPasswordPrimoLoginCallbacks callback);

    void recuperaPassword(Utente utente, DAOUtenteImpl.RecuperaPasswordCallbacks callback);

    void confermaPassword(RecoverHandler handle, DAOUtenteImpl.RecuperaPasswordCallbacks callback);

    void ottieniDipendenti(Ristorante ristorante, DAOUtenteImpl.DipendentiCallbacks callback);

    void rimuoviDipendente(UtenteHandler utente, DAOUtenteImpl.RimuoviDipendenteCallbacks callback);

    void aggiungiDipendente(RegistraUtenteHandler handler, DAOUtenteImpl.AggiungiDipendenteCallbacks callback);

    void modificaDipendente(AggiornaRuoloHandler handler, DAOUtenteImpl.ModificaDipendenteCallbacks callback);

    void modificaPassword(ModificaPasswordHandler handler, DAOUtenteImpl.ModificaPasswordCallbacks callback);

    }
