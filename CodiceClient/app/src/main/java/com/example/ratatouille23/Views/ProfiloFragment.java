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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterAreaPersonale;
import com.example.ratatouille23.R;
import com.google.firebase.analytics.FirebaseAnalytics;

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
    private TextView textViewEmailContenuto;
    private TextView textViewNomeContenuto;
    private TextView textViewRuoloContenuto;
    private String emailCorrente;
    private Utente utenteCorrente;

    private FirebaseAnalytics analytics;


    public ProfiloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentCorrente = inflater.inflate(R.layout.fragment_profilo, container, false);

        analytics = FirebaseAnalytics.getInstance(this.getActivity());

        textViewNome = fragmentCorrente.findViewById(R.id.textViewNomeRistorantePromptModifica);
        textViewEmail = fragmentCorrente.findViewById(R.id.textViewIndirizzoRistorantePromptModifica);
        textViewRuolo = fragmentCorrente.findViewById(R.id.textViewNumeroRistorantePromptModifica);
        textViewPassword = fragmentCorrente.findViewById(R.id.textViewTuristicoRistoranteModifica);
        iconaModificaPassword = fragmentCorrente.findViewById(R.id.iconaModificaPasswordProfilo);
        bottoneLogout = fragmentCorrente.findViewById(R.id.bottoneAnnullaModificaRistorante);
        textViewEmailContenuto = fragmentCorrente.findViewById(R.id.textViewIndirizzoRistoranteVisualizza);
        textViewNomeContenuto = fragmentCorrente.findViewById(R.id.textViewNomeRistoranteVisualizza);
        textViewRuoloContenuto = fragmentCorrente.findViewById(R.id.textViewNumeroRistoranteVisualizza);

        utenteCorrente = (Utente) getActivity().getIntent().getSerializableExtra("Utente");
        PresenterAreaPersonale.getInstance().aggiornaRistorante(this, utenteCorrente.getIdRistorante().getIdRistorante());

        textViewNomeContenuto.setText(utenteCorrente.getNomeCompleto());
        textViewRuoloContenuto.setText(utenteCorrente.getRuoloUtente());
        textViewEmailContenuto.setText(utenteCorrente.getEmail());

        iconaModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), ModificaPasswordActivity.class);
                i.putExtra("Utente", utenteCorrente);
                startActivity(i);
            }
        });


        bottoneLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), LoginActivity.class);
                i.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);


                analytics.setUserProperty("Ruolo", null);
                analytics.setUserProperty("Nome", null);
                analytics.setUserProperty("Ristorante", null);

                startActivity(i);
            }
        });

        return fragmentCorrente;
    }

    @Override
    public void onResume() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Fragment Profilo");
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        super.onResume();
    }

    public void setRistoranteCorrente(Ristorante ristorante) {
        utenteCorrente.setIdRistorante(ristorante);
    }
}