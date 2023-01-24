package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Utente;

public interface DAOUtente {

    Utente controllaDatiLogin(String email, String password);


}
