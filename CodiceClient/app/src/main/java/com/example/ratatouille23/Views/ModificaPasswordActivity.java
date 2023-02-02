package com.example.ratatouille23.Views;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterAreaPersonale;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;

public class ModificaPasswordActivity extends AppCompatActivity {

    private EditText editTextVecchiaPassword;
    private EditText editTextNuovaPassword;
    private EditText editTextConfermaNuovaPassword;
    private Button bottoneModificaPassword;
    private Button bottoneAnnulla;
    private Utente utenteCorrente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        editTextVecchiaPassword = findViewById(R.id.editTextCittaRistoranteModifica);
        editTextNuovaPassword = findViewById(R.id.editTextNuovaPassword);
        editTextConfermaNuovaPassword = findViewById(R.id.editTextConfermaNuovaPassword);
        bottoneModificaPassword = findViewById(R.id.bottoneResettaPassword);
        bottoneAnnulla = findViewById(R.id.bottoneAnnullaModificaPassword);

        utenteCorrente = ((Utente)getIntent().getSerializableExtra("Utente"));

        bottoneModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String vecchiaPassword = editTextVecchiaPassword.getText().toString();
                String nuovaPassword = editTextNuovaPassword.getText().toString();
                String confermaPassword = editTextConfermaNuovaPassword.getText().toString();
                try {
                    PresenterAreaPersonale.getInstance().modificaPasswordPremuto(
                            ModificaPasswordActivity.this,
                            utenteCorrente.getAccessToken(),
                            vecchiaPassword,
                            nuovaPassword,
                            confermaPassword);
                }
                catch (Exception e){
                    PresenterAreaPersonale.getInstance().mostraAlert(ModificaPasswordActivity.this, "Attenzione!", e.getMessage());
                }
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

    public void passwordModificataCorrettamente() {
        finish();
    }
}