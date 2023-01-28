package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;
import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.BachecaFragment;

public interface DAOAvviso {

    public void getAvvisi(Utente utente, BachecaFragment context, DAOAvvisoImpl.BachecaCallbacks callback);

    public void insertAvviso(InserisciAvvisoHandler handler, DAOAvvisoImpl.BachecaCallbacks callback);

    public void visualizzaAvviso(AggiornaAvvisoHandler handler, DAOAvvisoImpl.BachecaCallbacks callback);

    public void nascondiAvviso(AggiornaAvvisoHandler handler, DAOAvvisoImpl.BachecaCallbacks callback);
}
