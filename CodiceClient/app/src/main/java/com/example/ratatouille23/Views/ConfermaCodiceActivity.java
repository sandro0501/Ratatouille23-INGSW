package com.example.ratatouille23.Views;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;

public class ConfermaCodiceActivity extends AppCompatActivity {

    private TextView textViewPromptColorato;
    private EditText editTextPassword;
    private EditText editTextConfermaPassword;
    private EditText editTextCodice;
    private Button bottoneAnnulla;
    private Button bottoneResettaPassword;
    private String email;
    private ConfermaCodiceActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_codice);
        context = this;
        email = (String) getIntent().getSerializableExtra("email");
        textViewPromptColorato = findViewById(R.id.textViewPromptColorato);
        bottoneAnnulla = findViewById(R.id.buttonAnnulla);
        bottoneResettaPassword = findViewById(R.id.buttonResettaPassword);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfermaPassword = findViewById(R.id.editTextConfermaPassword);
        editTextCodice = findViewById(R.id.editTextCodice);

        String testoPromptColorato ="<font color=#5F5959>Ora inserisci il </font><font color =#CE9B44>codice di verifica </font><font color=#5F5959>ricevuto e la </font><font color =#CE9B44>nuova password</font><font color=#5F5959>.</font>";
        textViewPromptColorato.setText(Html.fromHtml(testoPromptColorato));

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bottoneResettaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codice = editTextCodice.getText().toString();
                String password = editTextPassword.getText().toString();
                String confermaPassword = editTextConfermaPassword.getText().toString();
                if (!codice.isEmpty() && !password.isEmpty() && !confermaPassword.isEmpty()) {
                    if (password.equals(confermaPassword)){
                        PresenterLogin.getInstance().bottoneResettaPasswordConCodicePremuto(email, codice, password,context);
                    }
                    else PresenterLogin.getInstance().mostraAlert(ConfermaCodiceActivity.this, "Attenzione!", "Le password inserite non coincidono!");
                }
                else PresenterLogin.getInstance().mostraAlert(ConfermaCodiceActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                        "stati lasciati vuoti");
            }
        });

    }

    public void mostraNascondiPassword(View view) {

        EditText editTextAssociata;

        if (view.getId()==R.id.iconaOcchioPassword)
            editTextAssociata = editTextPassword;
        else
            editTextAssociata = editTextConfermaPassword;

        if (editTextAssociata.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            ((ImageView)(view)).setImageResource(R.drawable.occhiosbarrato);
            editTextAssociata.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {

            ((ImageView)(view)).setImageResource(R.drawable.occhiopassword);
            editTextAssociata.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }

    }

    public void tornaAlLogin()
    {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}