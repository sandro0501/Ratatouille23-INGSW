package com.example.ratatouille23;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfermaCodiceActivity extends AppCompatActivity {
    TextView textViewPromptColorato;
    EditText editTextPassword;
    EditText editTextConfermaPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_codice);
        textViewPromptColorato = findViewById(R.id.textViewPromptColorato);
        String testoPromptColorato ="<font color=#5F5959>Ora inserisci il </font><font color =#CE9B44>codice di verifica </font><font color=#5F5959>ricevuto e la </font><font color =#CE9B44>nuova password</font><font color=#5F5959>.</font>";
        textViewPromptColorato.setText(Html.fromHtml(testoPromptColorato));

    }

    public void mostraNascondiPassword(View view) {

        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfermaPassword = findViewById(R.id.editTextConfermaPassword);

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