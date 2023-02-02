package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.EliminaSezioniHandler;
import com.example.ratatouille23.Models.SezioneMenu;

public interface DAOSezioneMenu {
    void estraiMenu(int idrist, DAOSezioneMenuImpl.EstraiMenuCallbacks callback);

    void aggiungiSezioneMenu(SezioneMenu sezione, DAOSezioneMenuImpl.AggiungiSezioneCallbacks callback);

    void modificaSezioneMenu(SezioneMenu sezione, DAOSezioneMenuImpl.ModificaSezioneCallbacks callback);

    void rimuoviSezioneMenu(EliminaSezioniHandler handler, DAOSezioneMenuImpl.RimuoviSezioneCallbacks callback);

}
