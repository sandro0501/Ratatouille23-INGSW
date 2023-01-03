package com.example.ratatouille23.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;

public class PasswordRecoveryActivity extends AppCompatActivity {

    Button bottoneAnnulla;
    Button bottoneRichiediCodice;
    EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        bottoneAnnulla = findViewById(R.id.annullaButton);
        bottoneRichiediCodice = findViewById(R.id.richiediCodiceButton);
        editTextEmail = findViewById(R.id.emailEditText);

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        bottoneRichiediCodice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                Intent i = new Intent(getApplicationContext(), ConfermaCodiceActivity.class);
                boolean emailCorretta;
                if (!email.isEmpty()) {
                    emailCorretta = Controller.getInstance().bottoneRichiediCodicePremuto(email);
                    if (emailCorretta)
                        startActivity(i);
                    else
                        Controller.getInstance().mostraAlertErrore(PasswordRecoveryActivity.this, "Attenzione!", "L'email inserita è errata!");
                }
                else Controller.getInstance().mostraAlertErrore(PasswordRecoveryActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                        "stati lasciati vuoti");
            }
        });

    }

}