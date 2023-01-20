package com.example.ratatouille23.DAO;

public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {};

    public static DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public DAOSezioneMenu getDAOMenu() {
        return new DAOSezioneMenuImpl();
    }

    public DAOElemento getDAOLogin() {
        return new DAOElementoImpl();
    }

    public DAOProdotto getDAODipendenti() {
        return new DAOProdottoImpl();
    }

    public DAORistorante getDAORistorante() {
        return new DAORistoranteImpl();
    }

    public DAOAvviso getDAOBacheca() {
        return new DAOAvvisoImpl();
    }

    public DAOUtente getDAOAreaPrivata() {
        return new DAOUtenteImpl();
    }
}
