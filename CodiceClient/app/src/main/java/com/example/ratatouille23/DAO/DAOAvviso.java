package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;

public interface DAOAvviso {

    public void getAvvisi(int uid, DAOAvvisoImpl.BachecaCallbacks callback);

    public void insertAvviso(InserisciAvvisoHandler handler, DAOAvvisoImpl.BachecaCallbacks callback);

    public void visualizzaAvviso(AggiornaAvvisoHandler handler, DAOAvvisoImpl.BachecaCallbacks callback);

    public void nascondiAvviso(AggiornaAvvisoHandler handler, DAOAvvisoImpl.BachecaCallbacks callback);
}
