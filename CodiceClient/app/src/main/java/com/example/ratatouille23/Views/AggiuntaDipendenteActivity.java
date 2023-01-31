package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ratatouille23.Handlers.RegistraUtenteHandler;
import com.example.ratatouille23.Handlers.UtenteHandler;
import com.example.ratatouille23.Models.Amministratore;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;
import com.example.ratatouille23.Presenters.PresenterDipendenti;
import com.example.ratatouille23.R;

public class AggiuntaDipendenteActivity extends AppCompatActivity {

    private Spinner sceltaRuoli;
    private String ruoli [];
    private Button bottoneAnnulla;
    private Button bottoneRegistraDipendente;
    private EditText editTextNome;
    private EditText editTextCognome;
    private EditText editTextEmail;

    private Utente utenteCorrente;
    private Ristorante ristoranteCorrente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_dipendente);

        sceltaRuoli = (Spinner)findViewById(R.id.spinnerRuoliDipendenteVisualizza);
        bottoneAnnulla = findViewById(R.id.buttonAnnullaRegistraDipendente);
        bottoneRegistraDipendente = findViewById(R.id.buttonLicenziaDipendente);
        editTextNome = findViewById(R.id.editTextCodice);
        editTextCognome = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextConfermaPassword);

        utenteCorrente = (Utente)getIntent().getSerializableExtra("Utente");
        ristoranteCorrente = utenteCorrente.getIdRistorante();

        if (((Amministratore)utenteCorrente).isSuperA())
            ruoli = new String[] {"Amministratore", "Supervisore", "Addetto alla cucina", "Addetto al servizio"};
        else
            ruoli = new String[] {"Supervisore", "Addetto alla cucina", "Addetto al servizio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_layout, ruoli);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        sceltaRuoli.setAdapter(adapter);

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bottoneRegistraDipendente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utente utenteInCostruzione = UtenteFactory.getInstance().getNuovoUtente(
                        editTextNome.getText().toString(),
                        editTextCognome.getText().toString(),
                        editTextEmail.getText().toString(),
                        sceltaRuoli.getSelectedItem().toString(),
                        false);

                RegistraUtenteHandler handler = new RegistraUtenteHandler();
                handler.utente = new UtenteHandler(utenteInCostruzione);
                handler.ristorante = ristoranteCorrente;
                handler.password = "Password" + utenteInCostruzione.getCognome() + "123!@";
                PresenterDipendenti.getInstance().aggiungiDipendente(AggiuntaDipendenteActivity.this, handler);
            }
        });

    }

    public void dipendenteAggiunto() {
        finish();
    }
}