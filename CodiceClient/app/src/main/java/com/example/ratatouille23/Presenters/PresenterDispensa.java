package com.example.ratatouille23.Presenters;

import com.example.ratatouille23.Models.Prodotto;

import java.util.ArrayList;

public class PresenterDispensa extends PresenterBase {

    private static PresenterDispensa instance;

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

    public ArrayList<Prodotto> ricavaProdottiDaIniziale(String stringaIniziale) {

        ArrayList<Prodotto> al = new ArrayList<>();
        al.add(new Prodotto("Fusilli", "Pasta mista", "grammi", "5.30/kg", 500, 150));
        al.add(new Prodotto("Penne", "Pasta mista per pomodoro", "grammi", "5.30/kg", 500, 150));
        al.add(new Prodotto("Cavoli", "Verdura", "grammi", "5.30/kg", 500, 150));
        al.add(new Prodotto("Carote", "Ortaggio", "grammi", "5.30/kg", 500, 150));
        return al;
    }
}
