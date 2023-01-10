package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ratatouille23.R;

public class AggiuntaDipendenteActivity extends AppCompatActivity {

    private Spinner sceltaRuoli;
    private String ruoli [];
    private Button bottoneAnnulla;
    private Button bottoneRegistraDipendente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_dipendente);

        sceltaRuoli = (Spinner)findViewById(R.id.spinnerRuoliDipendente);
        bottoneAnnulla = findViewById(R.id.buttonAnnullaRegistraDipendente);
        bottoneRegistraDipendente = findViewById(R.id.buttonConfermaRegistraDipendente);

        ruoli = new String[] {"Amministratore", "Supervisore", "Addetto alla cucina", "Addetto al servizio"};
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
                finish();
            }
        });

    }
}