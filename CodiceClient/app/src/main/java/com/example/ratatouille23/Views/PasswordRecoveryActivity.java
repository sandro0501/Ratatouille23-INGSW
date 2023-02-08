package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.DAO.DAOBaseUrl;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private Button bottoneAnnulla;
    private Button bottoneRichiediCodice;
    private EditText editTextEmail;
    private PasswordRecoveryActivity context;

    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        analytics = FirebaseAnalytics.getInstance(this);

        context = this;
        bottoneAnnulla = findViewById(R.id.annullaButton);
        bottoneRichiediCodice = findViewById(R.id.richiediCodiceButton);
        editTextEmail = findViewById(R.id.emailEditText);

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Annulla");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bottone");
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                finish();
            }
        });

        bottoneRichiediCodice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                if (DAOBaseUrl.getBaseUrl() == null) {
                    PresenterLogin.mostraAlert(PasswordRecoveryActivity.this, "Errore di connessione!", "Verificare se la connessione è attiva e riprovare");
                    downloadIP();
                }
                else {
                    if (!email.isEmpty())
                        PresenterLogin.getInstance().bottoneRichiediCodicePremuto(email, context);
                    else
                        PresenterLogin.getInstance().mostraAlert(PasswordRecoveryActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                                "stati lasciati vuoti");
                }
            }
        });

    }

    public void avviaConfermaCodice(String email)
    {
        Intent i = new Intent(getApplicationContext(), ConfermaCodiceActivity.class);
        i.putExtra("email",email);
        startActivity(i);
    }

    private void downloadIP() {
        try {
            Amplify.Storage.downloadFile(
                    "INDIRIZZO_IP_SERVER.txt",
                    new File((getFilesDir() + "/" + "INDIRIZZO_IP_SERVER.txt")),
                    result -> {
                        File fileIP = result.getFile();
                        try {
                            Scanner fileReader = new Scanner(fileIP);
                            String indirizzoIP = fileReader.nextLine();
                            setIndirizzoIP(indirizzoIP);
                            fileReader.close();
                        } catch (FileNotFoundException e) {
                        }
                    },
                    error -> Log.i("Controllo fatto altrove", "")

            );
        }
            catch (IllegalStateException e) {
            PresenterLogin.mostraAlert(this, "Errore!", "C'è stato un errore nella configurazione dell'applicazione.\nSi prega di chiudere l'applicazione.");
        }
    }

    private void setIndirizzoIP(String indirizzoIP) {
        Log.i("IP", indirizzoIP);
        DAOBaseUrl.setBaseUrl(indirizzoIP);
    }

}