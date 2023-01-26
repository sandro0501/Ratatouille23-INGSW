package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Utente;

public interface DAOBacheca {

    public void getAvvisiUtenteCorrente(Utente utente, DAOBachecaImpl.BachecaCallbacks callback);

}
