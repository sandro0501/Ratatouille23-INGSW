package com.example.ratatouille23.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;

public class CreazioneAvvisoActivity extends AppCompatActivity {

    private Button bottoneAnnullaCreazioneAvviso;
    private Button bottoneCreazioneAvviso;
    private Controller c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creazione_avviso);

        bottoneAnnullaCreazioneAvvisoPremuto();
        bottoneCreazioneAvvisoPremuto();


    }

    private void bottoneAnnullaCreazioneAvvisoPremuto() {
        bottoneAnnullaCreazioneAvviso = findViewById(R.id.bottoneAnnullaCreazioneAvviso);
        bottoneAnnullaCreazioneAvviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFromCreazioneAvvisoToBacheca = new Intent(getApplicationContext(), BachecaActivity.class);
                startActivity(intentFromCreazioneAvvisoToBacheca);
            }
        });
    }

    private void bottoneCreazioneAvvisoPremuto() {
        bottoneCreazioneAvviso = findViewById(R.id.bottoneCreazioneAvviso);
        bottoneCreazioneAvviso.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                mostraConfermaCreazioneAvvisoDialog();
            }
        });
    }

    private void mostraConfermaCreazioneAvvisoDialog() {
        /*
        AlertDialog.Builder alertConfermaCreazioneAvvisoBuilder = new AlertDialog.Builder(CreazioneAvvisoActivity.this);
        alertConfermaCreazioneAvvisoBuilder.setMessage("Avviso creato e inviato correttamente!");
        alertConfermaCreazioneAvvisoBuilder.setCancelable(true);
        AlertDialog alertConfermaCreazioneAvviso = alertConfermaCreazioneAvvisoBuilder.create();
        alertConfermaCreazioneAvviso.show(); */
        c.getInstance().mostraAlertErrore(CreazioneAvvisoActivity.this, "Avviso creato!", "Avviso creato ed inviato correttamente");

    }
}