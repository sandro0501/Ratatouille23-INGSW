package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;

public class PrimoLoginModificaPasswordActivity extends AppCompatActivity {

    private EditText editTextVecchiaPassword;
    private EditText editTextNuovaPassword;
    private EditText editTextConfermaNuovaPassword;
    private TextView textViewVecchiaPassword;
    private ImageView iconaOcchioVecchiaPassword;
    private ImageView iconaVecchiaPassword;
    private Button bottoneModificaPassword;
    private Button bottoneAnnulla;
    private ConstraintLayout constraintLayoutVecchiaPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        editTextVecchiaPassword = findViewById(R.id.editTextCittaRistoranteModifica);
        editTextNuovaPassword = findViewById(R.id.editTextNuovaPassword);
        editTextConfermaNuovaPassword = findViewById(R.id.editTextConfermaNuovaPassword);
        bottoneModificaPassword = findViewById(R.id.bottoneResettaPassword);
        bottoneAnnulla = findViewById(R.id.bottoneAnnullaModificaPassword);
        iconaOcchioVecchiaPassword = findViewById(R.id.iconaOcchioVecchiaPassword);
        iconaVecchiaPassword = findViewById(R.id.iconaVecchiaPassword);
        textViewVecchiaPassword = findViewById(R.id.textViewVecchiaPassword);
        constraintLayoutVecchiaPassword = findViewById(R.id.constraintLayout6);

        iconaVecchiaPassword.setVisibility(View.INVISIBLE);
        iconaOcchioVecchiaPassword.setVisibility(View.INVISIBLE);
        textViewVecchiaPassword.setVisibility(View.INVISIBLE);
        editTextVecchiaPassword.setVisibility(View.INVISIBLE);
        constraintLayoutVecchiaPassword.setVisibility(View.INVISIBLE);

        Utente utenteCorrente = (Utente)getIntent().getSerializableExtra("Utente");
        String sessione = getIntent().getStringExtra("Session");

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        bottoneModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PresenterLogin.getInstance().modificaPasswordPrimoLoginPremuto(PrimoLoginModificaPasswordActivity.this, utenteCorrente, sessione,
                            editTextNuovaPassword.getText().toString(), editTextConfermaNuovaPassword.getText().toString());
                } catch (Exception e) {
                    PresenterLogin.getInstance().mostraAlert(PrimoLoginModificaPasswordActivity.this, "Attenzione!", e.getMessage());
                }
            }
        });

    }

    public void passwordModificataCorrettamente(Utente utenteControllato) {
        Intent i = new Intent(getApplicationContext(), BachecaActivity.class);
        i.putExtra("Utente", utenteControllato);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Successo!");
        dialog.setMessage("La password è stata modificata correttamente!");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                startActivity(i);
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

    public void occhioSchermataModificaPasswordPremuto(View view) {

        EditText editTextAssociata;

         if (view.getId() == R.id.iconaOcchioNuovaPassword)
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