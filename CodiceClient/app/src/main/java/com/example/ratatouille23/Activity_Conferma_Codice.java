package com.example.ratatouille23;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Activity_Conferma_Codice extends AppCompatActivity {
    TextView tvPromptColorato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_codice);
        tvPromptColorato = findViewById(R.id.textViewPromptColorato);
        String testoPromptColorato ="<font color=#5F5959>Ora inserisci il </font><font color =#CE9B44>codice di verifica </font><font color=#5F5959>ricevuto e la </font><font color =#CE9B44>nuova password</font><font color=#5F5959>.</font>";
        tvPromptColorato.setText(Html.fromHtml(testoPromptColorato));

    }
}