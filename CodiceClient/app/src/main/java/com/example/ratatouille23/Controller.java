package com.example.ratatouille23;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille23.Activities.ConfermaCodiceActivity;
import com.example.ratatouille23.Activities.LoginActivity;
import com.example.ratatouille23.Activities.PasswordRecoveryActivity;
import com.example.ratatouille23.Modelli.Utente;

public class Controller extends AppCompatActivity {

    private static Controller instance;

    private Controller() { };

    public static Controller getInstance(){

        if (instance==null) {
            instance = new Controller();
        }
        return instance;

    }

    public Utente bottoneLoginPremuto (String email, String password) {

        return null;
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

}
