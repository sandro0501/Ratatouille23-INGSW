package com.example.ratatouille23;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Views.ProdottoRecyclerViewAdapter;

import java.util.ArrayList;

public class Controller extends AppCompatActivity {

    private static Controller instance;

    static Boolean bachecaAttiva = true;
    static Boolean isModalitaEliminazioneProdottoAttiva = false;

    private Controller() { };

    public static Controller getInstance(){

        if (instance==null) {
            instance = new Controller();
        }
        return instance;

    }

    public static Boolean getBachecaAttiva() {
        return bachecaAttiva;
    }

    public static void setBachecaAttiva(Boolean valore) {
       bachecaAttiva = valore;
    }


    public static Boolean getIsModalitaEliminazioneProdottoAttiva() {
        return isModalitaEliminazioneProdottoAttiva;
    }

    public static void setIsModalitaEliminazioneProdottoAttiva(Boolean isAttiva) {
        isModalitaEliminazioneProdottoAttiva = isAttiva;
    }



    public Utente bottoneLoginPremuto (String email, String password) {

        return new Utente();
    }

    public boolean bottoneRichiediCodicePremuto(String email) {

        return true;
    }


    public boolean bottoneResettaPasswordConCodicePremuto(String codice, String password) {
        return true;
    }

    public void mostraAlertErrore(Context context, String titolo, String messaggio) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle(titolo);
        dialog.setMessage(messaggio);
        dialog.setPositiveButton("Ok", null);
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public Boolean bottoneModificaPasswordPremuto(String vecchiaPassword, String nuovaPassword) {
        return true;
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
}
