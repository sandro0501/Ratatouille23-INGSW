package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Ristorante;

public interface DAORistorante {

    public void modificaRistorante(Ristorante ristorante, DAORistoranteImpl.RistoranteCallbacks callback);

}
