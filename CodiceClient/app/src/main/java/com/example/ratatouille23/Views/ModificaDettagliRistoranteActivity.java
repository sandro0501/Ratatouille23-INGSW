package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.R;

import java.io.File;
import java.io.IOException;

public class ModificaDettagliRistoranteActivity extends AppCompatActivity {

    private Button bottoneAnnulla;
    private Button bottoneConferma;
    private TextView textViewNome;
    private TextView textViewTelefono;
    private TextView textViewIndirizzo;
    private TextView textViewCitta;
    private TextView textViewTuristico;
    private CheckBox checkBoxTuristico;
    private ImageButton buttonSceltaFoto;
    private ImageView imageViewLogoRistorante;

    private Ristorante ristoranteCorrente;

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_dettagli_ristorante);

        bottoneAnnulla = findViewById(R.id.bottoneAnnullaModificaRistorante);
        bottoneConferma = findViewById(R.id.bottoneConfermaModificaRistorante);
        textViewNome = findViewById(R.id.editTextNomeRistoranteModifica);
        textViewTelefono = findViewById(R.id.editTextTelefonoRistoranteModifica);
        textViewIndirizzo = findViewById(R.id.editTextIndirizzoRistoranteModifica);
        textViewCitta = findViewById(R.id.editTextCittaRistoranteModifica);
        textViewTuristico = findViewById(R.id.textViewTuristicoRistoranteModifica);
        checkBoxTuristico = findViewById(R.id.checkBoxRistoranteTuristico);
        buttonSceltaFoto = findViewById(R.id.imageButtonSceltaFoto);
        imageViewLogoRistorante = findViewById(R.id.iconaLogoRistoranteModifica);

        ristoranteCorrente = (Ristorante)getIntent().getSerializableExtra("RistoranteCorrente");

        textViewNome.setText(ristoranteCorrente.getNome());
        textViewTelefono.setText(ristoranteCorrente.getNumeroDiTelefono());
        textViewIndirizzo.setText(ristoranteCorrente.getIndirizzo());
        textViewCitta.setText(ristoranteCorrente.getCitta());
        textViewTuristico.setText((ristoranteCorrente.isTuristico() ? "Il tuo ristorante è in una località turistica!": "Il tuo ristorante non è in una località turistica!"));
        checkBoxTuristico.setChecked(ristoranteCorrente.isTuristico());

        checkBoxTuristico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                textViewTuristico.setText((b ? "Il tuo ristorante è in una località turistica!": "Il tuo ristorante non è in una località turistica!"));
            }
        });

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSceltaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //error
                return;
            }

            Uri uri = data.getData();
            imageViewLogoRistorante.setImageURI(uri);
        }
    }

}