package com.example.ratatouille23.Views;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.ratatouille23.DAO.DAOBaseUrl;
import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.Presenters.PresenterBase;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.Presenters.PresenterRistorante;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class RatatouilleApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        configuraS3();

        downloadIP();

    }

    private void downloadIP() {
        Amplify.Storage.downloadFile(
                "INDIRIZZO_IP_SERVER.txt",
                new File((getFilesDir() + "/" + "INDIRIZZO_IP_SERVER.txt")),
                result -> {
                    File fileIP = result.getFile();
                    try {
                        Scanner fileReader = new Scanner(fileIP);
                        String indirizzoIP = fileReader.nextLine();
                        Log.i("STringa", indirizzoIP);
                        setIndirizzoIP(indirizzoIP);
                        fileReader.close();
                    } catch (FileNotFoundException e) {
                        PresenterLogin.getInstance().mostraAlertErroreConnessione(RatatouilleApp.this);
                    }
                },
                error -> PresenterLogin.getInstance().mostraAlertErroreConnessione(RatatouilleApp.this)

        );
    }

    private void setIndirizzoIP(String indirizzoIP) {
        DAOBaseUrl.setBaseUrl(indirizzoIP);
    }

    public void configuraS3() {
        try {
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            PresenterLogin.getInstance().mostraAlert(this, "Errore!", "C'è stato un errore nella configurazione dell'applicazione.\nSi prega di chiudere l'applicazione.");
        }
    }

}
