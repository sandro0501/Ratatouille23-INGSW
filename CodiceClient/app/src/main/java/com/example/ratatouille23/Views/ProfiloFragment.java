package com.example.ratatouille23.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfiloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfiloFragment extends Fragment {

    TextView textViewNome;
    TextView textViewEmail;
    TextView textViewRuolo;
    TextView textViewPassword;
    ImageView iconaModificaPassword;
    Button bottoneLogout;

    public ProfiloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentCorrente = inflater.inflate(R.layout.fragment_profilo, container, false);

        textViewNome = fragmentCorrente.findViewById(R.id.textViewNomeProfilo);
        textViewEmail = fragmentCorrente.findViewById(R.id.textViewEmailProfilo);
        textViewRuolo = fragmentCorrente.findViewById(R.id.textViewRuoloProfilo);
        textViewPassword = fragmentCorrente.findViewById(R.id.textViewPasswordProfilo);
        iconaModificaPassword = fragmentCorrente.findViewById(R.id.iconaModificaPasswordProfilo);
        bottoneLogout = fragmentCorrente.findViewById(R.id.bottoneLogout);

        textViewNome.setText(Html.fromHtml("<b>"+textViewNome.getText()+"</b>"));
        textViewEmail.setText(Html.fromHtml("<b>"+textViewNome.getText()+"</b>"));
        textViewRuolo.setText(Html.fromHtml("<b>"+textViewNome.getText()+"</b>"));
        textViewPassword.setText(Html.fromHtml("<b>"+textViewNome.getText()+"</b>"));

        iconaModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), ModificaPasswordActivity.class);
                startActivity(i);
            }
        });

        bottoneLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return fragmentCorrente;
    }
}