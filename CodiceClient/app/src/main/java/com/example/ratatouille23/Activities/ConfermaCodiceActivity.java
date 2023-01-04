package com.example.ratatouille23.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;

public class ConfermaCodiceActivity extends AppCompatActivity {

    TextView textViewPromptColorato;
    EditText editTextPassword;
    EditText editTextConfermaPassword;
    EditText editTextCodice;
    Button bottoneAnnulla;
    Button bottoneResettaPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_codice);

        textViewPromptColorato = findViewById(R.id.textViewPromptColorato);
        bottoneAnnulla = findViewById(R.id.buttonAnnulla);
        bottoneResettaPassword = findViewById(R.id.buttonResettaPassword);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfermaPassword = findViewById(R.id.editTextConfermaPassword);
        editTextCodice = findViewById(R.id.editTextCodice);

        String testoPromptColorato ="<font color=#5F5959>Ora inserisci il </font><font color =#CE9B44>codice di verifica </font><font color=#5F5959>ricevuto e la </font><font color =#CE9B44>nuova password</font><font color=#5F5959>.</font>";
        textViewPromptColorato.setText(Html.fromHtml(testoPromptColorato));

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PasswordRecoveryActivity.class);
                startActivity(i);
            }
        });

        bottoneResettaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codice = editTextCodice.getText().toString();
                String password = editTextPassword.getText().toString();
                String confermaPassword = editTextConfermaPassword.getText().toString();
                Intent i;
                boolean codiceCorretto;
                if (!codice.isEmpty() && !password.isEmpty() && !confermaPassword.isEmpty()) {
                    if (password.equals(confermaPassword)){
                        codiceCorretto = Controller.getInstance().bottoneResettaPasswordConCodicePremuto(codice, password);
                        if (codiceCorretto) {
                            i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                        else Controller.getInstance().mostraAlertErrore(ConfermaCodiceActivity.this, "Errore!", "Il codice inserito è errato!");
                    }
                    else Controller.getInstance().mostraAlertErrore(ConfermaCodiceActivity.this, "Attenzione!", "Le password inserite non coincidono!");
                }
                else Controller.getInstance().mostraAlertErrore(ConfermaCodiceActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                        "stati lasciati vuoti");
            }
        });

    }

    public void mostraNascondiPassword(View view) {


        if (view.getId()==R.id.iconaOcchioPassword) {
            if (editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

                ((ImageView)(view)).setImageResource(R.drawable.occhiosbarrato);
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else {

                ((ImageView)(view)).setImageResource(R.drawable.occhiopassword);
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
        else {
            if (editTextConfermaPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

                ((ImageView)(view)).setImageResource(R.drawable.occhiosbarrato);
                editTextConfermaPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else {

                ((ImageView)(view)).setImageResource(R.drawable.occhiopassword);
                editTextConfermaPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

}