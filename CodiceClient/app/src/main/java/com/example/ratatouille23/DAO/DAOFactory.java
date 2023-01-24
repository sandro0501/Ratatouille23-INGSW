package com.example.ratatouille23.DAO;

public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {};

    public static DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public DAOSezioneMenu getDAOSezioneMenu() {
        return new DAOSezioneMenuImpl();
    }

    public DAOElemento getDAOElemento() {
        return new DAOElementoImpl();
    }

    public DAOProdotto getDAOProdotto() {
        return new DAOProdottoImpl();
    }

    public DAORistorante getDAORistorante() {
        return new DAORistoranteImpl();
    }

    public DAOAvviso getDAOAvviso() {
        return new DAOAvvisoImpl();
    }

    public DAOUtente getDAOUtente() {
        return new DAOUtenteImpl();
    }
}
