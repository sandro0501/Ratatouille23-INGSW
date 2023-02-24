package com.example.ratatouille23.Presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterBase {

    private Dialog dialogAttesa;

    public static void mostraAlert(Context context, String titolo, String messaggio) {
        if (context != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setTitle(titolo);
            dialog.setMessage(messaggio);
            dialog.setPositiveButton("Ok", null);
            final AlertDialog alert = dialog.create();
            alert.show();
        }
    }

    public static void mostraAlertErroreHTTP(Context context, Response<ResponseBody> body) {
        mostraAlert(context, "Errore di comunicazione con il server!", body.message() + "\nCodice: " + body.code());
    }

    public static void mostraAlertFinishActivity(Activity context, String titolo, String messaggio) {
        if (context != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setTitle(titolo);
            dialog.setMessage(messaggio);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.finish();
                }
            });
            final AlertDialog alert = dialog.create();
            alert.show();
        }
    }

    public static void mostraAlertErroreConnessione(Context context) {
        mostraAlert(context, "Errore di connessione!", "Verificare la propria connessione alla rete e riprovare");
    }

    public void mostraAlertAttesaCaricamento(Context context) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Caricamento...");
            builder.setMessage("Attendere prego");
            builder.setCancelable(false);
            dialogAttesa = builder.create();
            dialogAttesa.show();
        }
    }

    public void nascondiAlertAttesaCaricamento() {
        dialogAttesa.dismiss();
    }

    public boolean soddisfaRequisiti(String nuovaPassword) {
        boolean flagMaiuscola = false;
        boolean flagMinuscola = false;
        boolean flagNumero = false;
        boolean flagSimbolo = false;
        String stringaSimboli = "^ $ * . [ ] { } ( ) ? @ # % & / , > < : ; | _ ~ = + - !";
        if (nuovaPassword.length() < 8)
            return false;
        for (int i = 0; i < nuovaPassword.length(); i++) {
            char carattereCorrente = nuovaPassword.charAt(i);
            if (Character.isUpperCase(carattereCorrente))
                flagMaiuscola = true;
            if (Character.isLowerCase(carattereCorrente))
                flagMinuscola = true;
            if (Character.isDigit(carattereCorrente))
                flagNumero = true;
            if (stringaSimboli.indexOf(carattereCorrente) >= 0)
                flagSimbolo = true;
        }
        return flagMaiuscola && flagMinuscola && flagNumero && flagSimbolo;
    }

}
