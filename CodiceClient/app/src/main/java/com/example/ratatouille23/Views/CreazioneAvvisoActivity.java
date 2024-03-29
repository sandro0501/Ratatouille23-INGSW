package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterBacheca;
import com.example.ratatouille23.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class CreazioneAvvisoActivity extends AppCompatActivity {

    private Button bottoneAnnullaCreazioneAvviso;
    private Button bottoneCreazioneAvviso;
    private Utente utenteCorrente;
    private CreazioneAvvisoActivity context = this;

    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        analytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_creazione_avviso);
        utenteCorrente = (Utente) getIntent().getSerializableExtra("Utente");
        bottoneAnnullaCreazioneAvvisoPremuto();
        bottoneCreazioneAvvisoPremuto();

    }

    private void bottoneAnnullaCreazioneAvvisoPremuto() {
        bottoneAnnullaCreazioneAvviso = findViewById(R.id.bottoneAnnullaCreazioneAvviso);
        bottoneAnnullaCreazioneAvviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Annulla");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bottone");
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                finish();
            }
        });
    }

    private void bottoneCreazioneAvvisoPremuto() {
        bottoneCreazioneAvviso = findViewById(R.id.bottoneCreazioneAvviso);
        bottoneCreazioneAvviso.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                InserisciAvvisoHandler handler = new InserisciAvvisoHandler();
                Ristorante ristorante = new Ristorante();
                ristorante.setIdRistorante(utenteCorrente.getIdRistorante().getIdRistorante());
                handler.autore = utenteCorrente;
                handler.ristorante = ristorante;
                handler.avviso = new Avviso();
                handler.avviso.setCorpo(((EditText) findViewById(R.id.EditTextCreazioneAvvisoCorpo)).getText().toString());
                handler.avviso.setOggetto(((EditText) findViewById(R.id.EditTextCreazioneAvvisoOggetto)).getText().toString());
                PresenterBacheca.getInstance().insertAvviso(context,handler);
            }
        });
    }

    public void mostraConfermaCreazioneAvvisoDialog() {
        PresenterBacheca.getInstance().mostraAlertFinishActivity(CreazioneAvvisoActivity.this, "Avviso creato!", "Avviso creato ed inviato correttamente");
    }

    public void mostraErroreCreazioneAvvisoDialog() {
        PresenterBacheca.getInstance().mostraAlert(CreazioneAvvisoActivity.this, "Errore!", "C'è stato un errore durante la creazione dell'avviso, si controlli che i campi non siano vuoti e si riprovi.");
    }
}