package com.example.ratatouille23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.app.Notification;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView benvenuto = findViewById(R.id.benvenuto);

        SpannableString testoBenvenuto = new SpannableString("Benvenuto!\nEffettua il login!");
        StyleSpan bold = new StyleSpan(Typeface.BOLD);

        testoBenvenuto.setSpan(bold,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        benvenuto.setText(testoBenvenuto);

        String passDim = "<strong><em>Password dimenticata?</em></strong>";
        TextView passDimenticata = (TextView) findViewById(R.id.passDimenticata);
        passDimenticata.setText(HtmlCompat.fromHtml(passDim, HtmlCompat.FROM_HTML_MODE_LEGACY));


        EditText pass = (EditText) findViewById(R.id.campoPassword);
        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


    }

    public void occhioSchermataLoginpremuto(View v)
    {
        EditText pass =(EditText) findViewById(R.id.campoPassword);
        if(pass.getInputType() == (InputType.TYPE_CLASS_TEXT |  InputType.TYPE_TEXT_VARIATION_PASSWORD))
        {
            pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }
        else
            pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

}