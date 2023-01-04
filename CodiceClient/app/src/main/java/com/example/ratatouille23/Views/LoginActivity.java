package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.R;

public class LoginActivity extends AppCompatActivity {

    Button bottoneLogin;
    TextView benvenuto;
    TextView passDimenticata;
    EditText editTextPass;
    EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        benvenuto = findViewById(R.id.benvenuto);

        SpannableString testoBenvenuto = new SpannableString("Benvenuto!\nEffettua il login!");
        StyleSpan bold = new StyleSpan(Typeface.BOLD);

        testoBenvenuto.setSpan(bold,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        benvenuto.setText(testoBenvenuto);

        String passDim = "<strong><em>Password dimenticata?</em></strong>";
        passDimenticata = (TextView) findViewById(R.id.passDimenticata);
        passDimenticata.setText(HtmlCompat.fromHtml(passDim, HtmlCompat.FROM_HTML_MODE_LEGACY));


        editTextPass = (EditText) findViewById(R.id.campoPassword);
        editTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        bottoneLogin = findViewById(R.id.bottoneLogin);
        editTextEmail = findViewById(R.id.campoMail);

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPass.getText().toString();
                Utente utenteCorrente;
                Intent i = new Intent(getApplicationContext(), PasswordRecoveryActivity.class);
                if (!email.isEmpty() && !password.isEmpty()) {
                    utenteCorrente = Controller.getInstance().bottoneLoginPremuto(email, password);
                    if (utenteCorrente == null) {
                        Controller.getInstance().mostraAlertErrore(LoginActivity.this, "Errore!", "L'email o la password non sono corretti!");
                    }
                    else {
                        //Accedi alla home
                    }
                }
                else {
                    Controller.getInstance().mostraAlertErrore(LoginActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
                            "stati lasciati vuoti");
                }

            }
        });

        passDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PasswordRecoveryActivity.class);
                startActivity(i);
            }
        });



    }

    public void occhioSchermataLoginpremuto(View v) {
        EditText pass = (EditText) findViewById(R.id.campoPassword);
        if (pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            ((ImageView) (v)).setImageResource(R.drawable.occhiosbarrato);
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {

            ((ImageView) (v)).setImageResource(R.drawable.occhiopassword);
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
}