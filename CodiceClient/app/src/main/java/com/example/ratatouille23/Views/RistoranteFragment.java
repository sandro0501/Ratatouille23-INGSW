package com.example.ratatouille23.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterRistorante;
import com.example.ratatouille23.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RistoranteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RistoranteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button bottoneModifica;
    private TextView textViewNome;
    private TextView textViewTelefono;
    private TextView textViewIndirizzo;
    private TextView textViewCitta;
    private TextView textViewTuristico;
    private ImageView logoRistorante;

    private View fragmentCorrente;

    private File fileLogo;

    private Ristorante ristoranteCorrente;

    private static final int RICEZIONE_RISTORANTE = 2;

    private FirebaseAnalytics analytics;

    public RistoranteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RistoranteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RistoranteFragment newInstance(String param1, String param2) {
        RistoranteFragment fragment = new RistoranteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCorrente = inflater.inflate(R.layout.fragment_ristorante, container, false);

        analytics = FirebaseAnalytics.getInstance(getActivity());

        bottoneModifica = fragmentCorrente.findViewById(R.id.bottoneAnnullaModificaRistorante);
        textViewNome = fragmentCorrente.findViewById(R.id.textViewNomeRistoranteVisualizza);
        textViewTelefono = fragmentCorrente.findViewById(R.id.textViewNumeroRistoranteVisualizza);
        textViewIndirizzo = fragmentCorrente.findViewById(R.id.textViewIndirizzoRistoranteVisualizza);
        textViewCitta = fragmentCorrente.findViewById(R.id.textViewCittaRistoranteVisualizza);
        textViewTuristico = fragmentCorrente.findViewById(R.id.textViewTuristicoRistoranteVisualizza);
        logoRistorante = fragmentCorrente.findViewById(R.id.iconaLogoRistoranteVisualizza);

        Utente utenteCorrente = (Utente)getActivity().getIntent().getSerializableExtra("Utente");

        ristoranteCorrente = utenteCorrente.getIdRistorante();

        textViewNome.setText(ristoranteCorrente.getDenominazione());
        textViewTelefono.setText(ristoranteCorrente.getNumeroTelefono());
        textViewIndirizzo.setText(ristoranteCorrente.getIndirizzo());
        textViewCitta.setText(ristoranteCorrente.getCitta());
        textViewTuristico.setText((ristoranteCorrente.isTuristico() ? "Il tuo ristorante è in una località turistica!": "Il tuo ristorante non è in una località turistica!"));


        bottoneModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), ModificaDettagliRistoranteActivity.class);
                i.putExtra("FileLogo", fileLogo);
                i.putExtra("RistoranteCorrente", ristoranteCorrente);
                startActivityForResult(i, RICEZIONE_RISTORANTE);
            }
        });

        return fragmentCorrente;
    }



    @Override
    public void onStart() {
        aggiornaRistorante();
        super.onStart();
    }

    @Override
    public void onResume() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Fragment Ristorante");
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        super.onResume();
    }

    private void setImmagine(File file, String path) {
        fileLogo = file;
        Bitmap bitmapLogo  = BitmapFactory.decodeFile(file.getAbsolutePath());
        logoRistorante.setImageBitmap(bitmapLogo);
    }

    public void aggiornaRistorante() {
        PresenterRistorante.getInstance().riceviRistorante(RistoranteFragment.this, ristoranteCorrente.getIdRistorante());
    }

    public void setRistorante(Ristorante ristoranteRicevuto) {
        ristoranteCorrente = ristoranteRicevuto;
        textViewNome.setText(ristoranteCorrente.getDenominazione());
        textViewTelefono.setText(ristoranteCorrente.getNumeroTelefono());
        textViewIndirizzo.setText(ristoranteCorrente.getIndirizzo());
        textViewCitta.setText(ristoranteCorrente.getCitta());
        textViewTuristico.setText((ristoranteCorrente.isTuristico() ? "Il tuo ristorante è in una località turistica!": "Il tuo ristorante non è in una località turistica!"));
        if ((ristoranteCorrente.getUrlFoto() != null) && (!ristoranteCorrente.getUrlFoto().isEmpty())) {
            String pathFoto = ristoranteCorrente.getUrlFoto();

            Amplify.Storage.list(pathFoto,
                    result -> {
                        if (!result.getItems().isEmpty())
                            setLogoDownload(pathFoto);
                    },
                    error -> Log.e("MyAmplifyApp", "List failure", error)
            );
        }

        ((BachecaActivity)getActivity()).setNomeRistorante(ristoranteCorrente);
    }

    private void setLogoDownload(String path) {
        Amplify.Storage.downloadFile(
                path,
                new File(fragmentCorrente.getContext().getFilesDir() + "/" + path),
                result -> setImmagine(result.getFile(), path),
                error -> PresenterRistorante.getInstance().mostraAlert(RistoranteFragment.this.getActivity(), "Errore!", "Un'immagine non è stata scaricata correttamente, riprovare")

        );
    }

}