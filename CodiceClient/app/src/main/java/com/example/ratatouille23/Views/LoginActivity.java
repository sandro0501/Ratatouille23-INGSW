package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.DAO.DAOBaseUrl;
import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.CaratteriIllecitiException;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LoginActivity extends AppCompatActivity {

    private Button bottoneLogin;
    private TextView benvenuto;
    private TextView passDimenticata;
    private EditText editTextPass;
    private EditText editTextEmail;

    private Button bottoneCRASH;

    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        analytics = FirebaseAnalytics.getInstance(this);

        downloadIP();

        benvenuto = findViewById(R.id.benvenuto);

        SpannableString testoBenvenuto = new SpannableString("Benvenuto!\nEffettua il login!");
        StyleSpan bold = new StyleSpan(Typeface.BOLD);

        testoBenvenuto.setSpan(bold,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        benvenuto.setText(testoBenvenuto);

        String passDim = "<strong><em>Password dimenticata?</em></strong>";
        passDimenticata = (TextView) findViewById(R.id.passDimenticata);
        passDimenticata.setText(HtmlCompat.fromHtml(passDim, HtmlCompat.FROM_HTML_MODE_LEGACY));


        editTextPass = (EditText) findViewById(R.id.editTextCittaRistoranteModifica);
        editTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        bottoneLogin = findViewById(R.id.bottoneResettaPassword);
        editTextEmail = findViewById(R.id.campoMail);

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPass.getText().toString();
                if (DAOBaseUrl.getBaseUrl() == null) {
                    PresenterLogin.mostraAlert(LoginActivity.this, "Errore di connessione!", "Verificare se la connessione è attiva e riprovare");
                    downloadIP();
                }
                else {

                    try {
                        PresenterLogin.getInstance().bottoneLoginPremuto(LoginActivity.this, email, password);
                    } catch (CampiVuotiException e) {
                        PresenterLogin.getInstance().mostraAlert(LoginActivity.this, "Attenzione!", e.getMessage());
                    } catch (CaratteriIllecitiException e) {
                        PresenterLogin.getInstance().mostraAlert(LoginActivity.this, "Errore!", e.getMessage());
                    }

                }

            }
        });

        passDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PasswordRecoveryActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStop() {
        editTextEmail.setText("");
        editTextPass.setText("");
        super.onStop();
    }

    public void occhioSchermataLoginpremuto(View v) {
        EditText pass = (EditText) findViewById(R.id.editTextCittaRistoranteModifica);
        if (pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            ((ImageView) (v)).setImageResource(R.drawable.occhiosbarrato);
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {

            ((ImageView) (v)).setImageResource(R.drawable.occhiopassword);
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }

    public void effettuaAccesso(Utente utente) {
        Intent i = new Intent(getApplicationContext(), BachecaActivity.class);
        i.putExtra("Utente", utente);
        analytics.setUserProperty("Ruolo", utente.getRuoloUtente());
        analytics.setUserProperty("Nome", utente.getNomeCompleto());
        analytics.setUserProperty("Ristorante", utente.getIdRistorante().getDenominazione());
        startActivity(i);
    }

    public void mostraAlertAccessoErrato() {
        PresenterLogin.getInstance().mostraAlert(LoginActivity.this, "Errore!", "Le credenziali sono errate!");
    }

    public void effettuaPrimoAccessoUtente(Utente utente, String session) {
        Intent i = new Intent(getApplicationContext(), PrimoLoginModificaPasswordActivity.class);
        i.putExtra("Utente", utente);
        i.putExtra("Session", session);
        startActivity(i);
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
                        setIndirizzoIP(indirizzoIP);
                        fileReader.close();
                    } catch (FileNotFoundException e) {
                    }
                },
                error -> Log.i("Controllo fatto altrove", "")

        );
    }

    private void setIndirizzoIP(String indirizzoIP) {
        Log.i("IP", indirizzoIP);
        DAOBaseUrl.setBaseUrl(indirizzoIP);
    }

}