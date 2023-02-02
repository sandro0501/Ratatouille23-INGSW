package com.example.ratatouille23.Presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterBase {

    public void mostraAlert(Context context, String titolo, String messaggio) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle(titolo);
        dialog.setMessage(messaggio);
        dialog.setPositiveButton("Ok", null);
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public void mostraAlertErroreHTTP(Context context, Response<ResponseBody> body) {
        mostraAlert(context, "Errore di comunicazione con il server!", body.message() + "\nCodice: " + body.code());
    }

    public void mostraAlertFinishActivity(Activity context, String titolo, String messaggio) {
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

    public void mostraAlertErroreConnessione(Context context) {
        mostraAlert(context, "Errore di connessione!", "Verificare la propria connessione alla rete e riprovare");
    }
}
