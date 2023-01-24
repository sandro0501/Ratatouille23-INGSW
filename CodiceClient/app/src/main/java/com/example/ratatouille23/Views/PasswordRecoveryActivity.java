package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private Button bottoneAnnulla;
    private Button bottoneRichiediCodice;
    private EditText editTextEmail;

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
                finish();
            }
        });

        bottoneRichiediCodice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                Intent i = new Intent(getApplicationContext(), ConfermaCodiceActivity.class);
                boolean emailCorretta;
                if (!email.isEmpty()) {
                    emailCorretta = PresenterLogin.getInstance().bottoneRichiediCodicePremuto(email);
                    if (emailCorretta)
                        startActivity(i);
                    else
                        PresenterLogin.getInstance().mostraAlert(PasswordRecoveryActivity.this, "Attenzione!", "L'email inserita è errata!");
                }
                else PresenterLogin.getInstance().mostraAlert(PasswordRecoveryActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                        "stati lasciati vuoti");
            }
        });

    }

}