package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ratatouille23.R;

public class VisualizzazioneAvvisoActivity extends AppCompatActivity {
    Toolbar toolbarNavigazione;
    String oggettoAvviso;
    String autoreAvviso;
    String ruoloAutoreAvviso;
    String corpoAvviso;
    String dataCreazioneAvviso;
    TextView textViewOggettoAvviso;
    TextView textViewAutoreAvviso;
    TextView textViewRuoloAutoreAvviso;
    TextView textViewCorpoAvviso;
    TextView textViewDataCreazioneAvviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_avviso);

        inizializzaToolbarNavigazione();

        oggettoAvviso = getIntent().getStringExtra("OGGETTO");
        autoreAvviso = getIntent().getStringExtra("AUTORE");
        ruoloAutoreAvviso = getIntent().getStringExtra("RUOLOAUTORE");
        dataCreazioneAvviso = getIntent().getStringExtra("DATACREAZIONE");
        corpoAvviso = getIntent().getStringExtra("CORPO");

        textViewOggettoAvviso = findViewById(R.id.textViewVisualizzazioneAvvisoOggetto);
        textViewAutoreAvviso = findViewById(R.id.textViewVisualizzazioneAvvisoAutore);
        textViewRuoloAutoreAvviso = findViewById(R.id.textViewVisualizzazioneAvvisoRuolo);
        textViewCorpoAvviso = findViewById(R.id.textViewVisualizzazioneAvvisoCorpo);
        textViewDataCreazioneAvviso = findViewById(R.id.textViewVisualizzazioneAvvisoData);

        textViewOggettoAvviso.setText(oggettoAvviso);
        textViewAutoreAvviso.setText(autoreAvviso);
        textViewRuoloAutoreAvviso.setText(ruoloAutoreAvviso);
        textViewCorpoAvviso.setText(corpoAvviso);
        textViewDataCreazioneAvviso.setText(dataCreazioneAvviso);


    }

    private void inizializzaToolbarNavigazione() {
        toolbarNavigazione = (Toolbar) findViewById(R.id.toolbarCreazioneAvviso);
        toolbarNavigazione.setNavigationIcon(R.drawable.icon_back_arrow);
        toolbarNavigazione.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed(); // Implemented by activity
            }
        });
    }
}