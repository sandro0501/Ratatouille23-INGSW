package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.Models.Prodotto;

import java.util.ArrayList;

public class PresenterDispensa extends PresenterBase {

    private static PresenterDispensa instance;

    private static Boolean isModalitaEliminazioneProdottoAttiva = false;

    private PresenterDispensa() { };

    public static PresenterDispensa getInstance(){

        if (instance==null) {
            instance = new PresenterDispensa();
        }
        return instance;

    }

    public boolean controllaSogliaProdotto(ArrayList<Prodotto> dispensa, int posizioneProdottoInDispensa) {
        double quantitaProdotto;
        double sogliaProdotto;
        boolean isProdottoSottoSogliaLimite = false;

        quantitaProdotto = dispensa.get(posizioneProdottoInDispensa).getQuantita();
        sogliaProdotto = dispensa.get(posizioneProdottoInDispensa).getSoglia();

        if(quantitaProdotto < sogliaProdotto){
            isProdottoSottoSogliaLimite = true;
        }
        return isProdottoSottoSogliaLimite;
    }


    public static Boolean getIsModalitaEliminazioneProdottoAttiva() {
        return isModalitaEliminazioneProdottoAttiva;
    }

    public static void setIsModalitaEliminazioneProdottoAttiva(Boolean isAttiva) {
        isModalitaEliminazioneProdottoAttiva = isAttiva;
    }

    public Prodotto ricavaProdottiDaIniziale(String stringaIniziale) {


        return null;
    }
}
