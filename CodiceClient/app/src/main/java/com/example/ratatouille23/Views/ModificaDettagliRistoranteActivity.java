package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Presenters.PresenterRistorante;
import com.example.ratatouille23.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    private File fileLogo;

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
        fileLogo = (File)getIntent().getSerializableExtra("FileLogo");

        textViewNome.setText(ristoranteCorrente.getDenominazione());
        textViewTelefono.setText(ristoranteCorrente.getNumeroTelefono());
        textViewIndirizzo.setText(ristoranteCorrente.getIndirizzo());
        textViewCitta.setText(ristoranteCorrente.getCitta());
        textViewTuristico.setText((ristoranteCorrente.isTuristico() ? "Il tuo ristorante è in una località turistica!": "Il tuo ristorante non è in una località turistica!"));
        checkBoxTuristico.setChecked(ristoranteCorrente.isTuristico());

        if (fileLogo != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(fileLogo.getAbsolutePath());
            imageViewLogoRistorante.setImageBitmap(bitmap);
        }

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
            InputStream streamLogo = null;
            try {
                streamLogo = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            uploadS3(streamLogo, uri);

        }
    }

    private void uploadS3(InputStream streamLogo, Uri uri) {
        Amplify.Storage.uploadInputStream(
                ((Integer)ristoranteCorrente.getIdRistorante()).toString()+"_LogoRistorante.jpg",
                streamLogo,
                result -> imageViewLogoRistorante.setImageURI(uri),
                storageFailure -> PresenterRistorante.getInstance().mostraAlert(getBaseContext(), "Errore!", "L'immagine non è stata caricata correttamente, riprovare")
        );
    }

}