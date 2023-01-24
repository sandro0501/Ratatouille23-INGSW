package com.example.ratatouille23.Presenters;

public class PresenterBacheca extends PresenterBase {

    private static PresenterBacheca instance;

    static Boolean bachecaAttiva = true;

    private PresenterBacheca() { };

    public static PresenterBacheca getInstance(){

        if (instance==null) {
            instance = new PresenterBacheca();
        }
        return instance;

    }

    public Boolean getBachecaAttiva() {
        return bachecaAttiva;
    }

    public void setBachecaAttiva(Boolean valore) {
        bachecaAttiva = valore;
    }

}
