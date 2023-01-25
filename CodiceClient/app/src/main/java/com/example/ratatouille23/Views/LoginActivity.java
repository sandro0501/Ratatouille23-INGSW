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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.R;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;



public class LoginActivity extends AppCompatActivity {

    private Button bottoneLogin;
    private TextView benvenuto;
    private TextView passDimenticata;
    private EditText editTextPass;
    private EditText editTextEmail;

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


        editTextPass = (EditText) findViewById(R.id.editTextCittaRistoranteModifica);
        editTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        bottoneLogin = findViewById(R.id.bottoneResettaPassword);
        editTextEmail = findViewById(R.id.campoMail);

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPass.getText().toString();
                Utente utenteCorrente;
                Intent i = new Intent(getApplicationContext(), BachecaActivity.class);
                if (!email.isEmpty() && !password.isEmpty()) {
                    utenteCorrente = PresenterLogin.getInstance().bottoneLoginPremuto(email, password);
                    if (utenteCorrente == null) {
                        PresenterLogin.getInstance().mostraAlert(LoginActivity.this, "Errore!", "L'email o la password non sono corretti!");
                    }
                    else {
                        startActivity(i);
                    }
                }
                else {
                    PresenterLogin.getInstance().mostraAlert(LoginActivity.this, "Attenzione!", "Uno o più campi obbligatori sono " +
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

    @Override
    protected void onStop() {
        editTextEmail.setText("");
        editTextPass.setText("");
        super.onStop();
    }

    public void occhioSchermataLoginpremuto(View v) {
        EditText pass = (EditText) findViewById(R.id.editTextCittaRistoranteModifica);
        if (pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

            ((ImageView) (v)).setImageResource(R.drawable.occhiosbarrato);
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {

            ((ImageView) (v)).setImageResource(R.drawable.occhiopassword);
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
}