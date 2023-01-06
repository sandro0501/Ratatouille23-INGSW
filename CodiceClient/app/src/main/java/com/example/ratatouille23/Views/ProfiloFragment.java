package com.example.ratatouille23.Views;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

    private TextView textViewNome;
    private TextView textViewEmail;
    private TextView textViewRuolo;
    private TextView textViewPassword;
    private ImageView iconaModificaPassword;
    private Button bottoneLogout;

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
        textViewEmail.setText(Html.fromHtml("<b>"+textViewEmail.getText()+"</b>"));
        textViewRuolo.setText(Html.fromHtml("<b>"+textViewRuolo.getText()+"</b>"));
        textViewPassword.setText(Html.fromHtml("<b>"+textViewPassword.getText()+"</b>"));

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
                i.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        return fragmentCorrente;
    }
}