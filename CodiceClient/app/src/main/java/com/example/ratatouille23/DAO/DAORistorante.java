package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Models.Ristorante;

public interface DAORistorante {

    public void modificaRistorante(Ristorante ristorante, DAORistoranteImpl.RistoranteModificaCallbacks callback);

    void getRistorante(int idRistorante, DAORistoranteImpl.RistoranteRiceviCallbacks callback);
}
