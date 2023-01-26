package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Utente;

public interface DAOAvviso {

    public void getAvvisi(Utente utente, DAOAvvisoImpl.BachecaCallbacks callback);

}
