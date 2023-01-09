package com.example.ratatouille23.Views;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private ImageView iconaModificaEmail;
    private EditText editTextEmail;
    private TextView textViewNomeContenuto;
    private TextView textViewRuoloContenuto;
    private String emailCorrente;


    public ProfiloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentCorrente = inflater.inflate(R.layout.fragment_profilo, container, false);

        textViewNome = fragmentCorrente.findViewById(R.id.textViewNomeProfiloPrompt);
        textViewEmail = fragmentCorrente.findViewById(R.id.textViewEmailProfiloPrompt);
        textViewRuolo = fragmentCorrente.findViewById(R.id.textViewRuoloProfiloPrompt);
        textViewPassword = fragmentCorrente.findViewById(R.id.textViewPasswordProfiloPrompt);
        iconaModificaPassword = fragmentCorrente.findViewById(R.id.iconaModificaPasswordProfilo);
        bottoneLogout = fragmentCorrente.findViewById(R.id.bottoneLogout);
        iconaModificaEmail = fragmentCorrente.findViewById(R.id.iconaModificaEmailProfilo);
        editTextEmail = fragmentCorrente.findViewById(R.id.editTextEmailProfilo);
        textViewNomeContenuto = fragmentCorrente.findViewById(R.id.textViewNomeProfilo);
        textViewRuoloContenuto = fragmentCorrente.findViewById(R.id.textViewRuoloProfilo);

        editTextEmail.setEnabled(false);

        iconaModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), ModificaPasswordActivity.class);
                startActivity(i);
            }
        });

        iconaModificaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextEmail.isEnabled()) {
                    iconaModificaEmail.setImageResource(R.drawable.icona_matita);
                    editTextEmail.setEnabled(false);
                    emailCorrente = editTextEmail.getText().toString();
                    editTextEmail.setText(emailCorrente);
                }
                else {
                    iconaModificaEmail.setImageResource(R.drawable.icona_spunta_modifica);
                    editTextEmail.setEnabled(true);
                    editTextEmail.requestFocus();
                    editTextEmail.setSelection(editTextEmail.getText().length());
                }
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

    public void onStart() {
        super.onStart();
        emailCorrente = editTextEmail.getText().toString();
    }

    @Override
    public void onStop() {
        if (editTextEmail.isEnabled()) {
            iconaModificaEmail.setImageResource(R.drawable.icona_matita);
            editTextEmail.setEnabled(false);
            editTextEmail.setText(emailCorrente);
        }
        super.onStop();
    }
}