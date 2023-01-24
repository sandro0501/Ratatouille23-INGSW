package com.example.ratatouille23.Presenters;

import android.app.AlertDialog;
import android.content.Context;

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

}
