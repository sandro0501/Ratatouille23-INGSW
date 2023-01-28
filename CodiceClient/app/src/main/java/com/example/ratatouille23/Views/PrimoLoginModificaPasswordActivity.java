package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        iconaVecchiaPassword.setVisibility(View.INVISIBLE);
        iconaOcchioVecchiaPassword.setVisibility(View.INVISIBLE);
        textViewVecchiaPassword.setVisibility(View.INVISIBLE);
        editTextVecchiaPassword.setVisibility(View.INVISIBLE);

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
        PresenterLogin.getInstance().mostraAlert(PrimoLoginModificaPasswordActivity.this, "Successo!", "La password è stata modificata correttamente!\nEffettuare nuovamente il login.");
        Intent i = new Intent(getApplicationContext(), BachecaActivity.class);
        i.putExtra("Utente", utenteControllato);
        startActivity(i);
    }
}