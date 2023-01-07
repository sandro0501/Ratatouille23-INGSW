package com.example.ratatouille23.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ratatouille23.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DipendenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DipendenteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner sceltaRuoli;
    private String ruoli [];
    private Button bottoneAnnulla;
    private Button bottoneRegistraDipendente;


    public DipendenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DipendenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DipendenteFragment newInstance(String param1, String param2) {
        DipendenteFragment fragment = new DipendenteFragment();
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
        View fragmentCorrente = inflater.inflate(R.layout.fragment_dipendente, container, false);
        sceltaRuoli = (Spinner)fragmentCorrente.findViewById(R.id.spinnerRuoliDipendente);
        bottoneAnnulla = fragmentCorrente.findViewById(R.id.buttonAnnullaRegistraDipendente);
        bottoneRegistraDipendente = fragmentCorrente.findViewById(R.id.buttonConfermaRegistraDipendente);

        ruoli = new String[] {"Amministratore", "Supervisore", "Addetto alla cucina", "Addetto al servizio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragmentCorrente.getContext(), R.layout.spinner_layout, ruoli);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        sceltaRuoli.setAdapter(adapter);


        return fragmentCorrente;
    }

}