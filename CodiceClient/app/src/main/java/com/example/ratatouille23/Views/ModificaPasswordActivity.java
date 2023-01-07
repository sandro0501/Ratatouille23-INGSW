package com.example.ratatouille23.Views;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;

public class ModificaPasswordActivity extends AppCompatActivity {

    private EditText editTextVecchiaPassword;
    private EditText editTextNuovaPassword;
    private EditText editTextConfermaNuovaPassword;
    private ImageView iconaFrecciaIndietro;
    private Button bottoneModificaPassword;
    private Button bottoneAnnulla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        editTextVecchiaPassword = findViewById(R.id.editTextVecchiaPassword);
        editTextNuovaPassword = findViewById(R.id.editTextNuovaPassword);
        editTextConfermaNuovaPassword = findViewById(R.id.editTextConfermaNuovaPassword);
        bottoneModificaPassword = findViewById(R.id.bottoneResettaPassword);
        bottoneAnnulla = findViewById(R.id.bottoneAnnullaModificaPassword);

        bottoneModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String vecchiaPassword = editTextVecchiaPassword.getText().toString();
                String nuovaPassword = editTextNuovaPassword.getText().toString();
                String confermaPassword = editTextConfermaNuovaPassword.getText().toString();
                Boolean passwordModificata;

                if (!vecchiaPassword.isEmpty() && !nuovaPassword.isEmpty() && !confermaPassword.isEmpty()) {
                    if (nuovaPassword.equals(confermaPassword)){
                        if (!vecchiaPassword.equals(nuovaPassword)) {
                            passwordModificata = Controller.getInstance().bottoneModificaPasswordPremuto(vecchiaPassword, nuovaPassword);
                            if (passwordModificata) {
                                finish();
                            } else
                                Controller.getInstance().mostraAlertErrore(ModificaPasswordActivity.this, "Errore!", "La password non è stata " +
                                        "modificata correttamente!");

                        }
                        else Controller.getInstance().mostraAlertErrore(ModificaPasswordActivity.this, "Attenzione!", "La nuova password scelta " +
                                "deve differire da quella attuale");
                    }
                    else Controller.getInstance().mostraAlertErrore(ModificaPasswordActivity.this, "Attenzione!", "Le password inserite non coincidono!");
                }
                else Controller.getInstance().mostraAlertErrore(ModificaPasswordActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                        "stati lasciati vuoti");
            }
        });

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void occhioSchermataModificaPasswordPremuto(View view) {

        EditText editTextAssociata;

        if (view.getId()==R.id.iconaOcchioVecchiaPassword)
            editTextAssociata = editTextVecchiaPassword;
        else if (view.getId() == R.id.iconaOcchioNuovaPassword)
            editTextAssociata = editTextNuovaPassword;
        else
            editTextAssociata = editTextConfermaNuovaPassword;

        if (editTextAssociata.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            ((ImageView)(view)).setImageResource(R.drawable.occhiosbarrato);
            editTextAssociata.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {

            ((ImageView)(view)).setImageResource(R.drawable.occhiopassword);
            editTextAssociata.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }

    }
}