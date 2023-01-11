package com.example.ratatouille23.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DipendenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DipendenteFragment extends Fragment implements RecyclerViewDipendenteInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView iconaAggiungiDipendente;
    private RecyclerView recyclerView;

    private DipendenteRecyclerViewAdapter dipendenteAdapter;
    private ArrayList<Utente> dipendenti;
    private AlertDialog.Builder builderDialogVisualizzaDipendente;
    private Dialog dialogVisualizzaDipendente;

    private TextView textViewNomeDipendente;
    private TextView textViewEmailDipendente;
    private Spinner spinnerRuoloDipendente;
    private Button bottoneLicenzia;


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
        iconaAggiungiDipendente = fragmentCorrente.findViewById(R.id.iconaAggiungiDipendente);

        iconaAggiungiDipendente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragmentCorrente.getContext(), AggiuntaDipendenteActivity.class);
                startActivity(i);
            }
        });

        return fragmentCorrente;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerViewDipendenti);
        setUpDipendenti();
        dipendenteAdapter = new DipendenteRecyclerViewAdapter(getContext(),dipendenti,this);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dipendenteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dipendenteAdapter.notifyDataSetChanged();



    }

    private void setUpDipendenti(){
        dipendenti = new ArrayList<>();

        String[] nomiDipendenti = getResources().getStringArray(R.array.nome_dipendente_xml);
        String[] cognomiDipendenti = getResources().getStringArray(R.array.cognome_dipendente_xml);
        String[] emailDipendenti = getResources().getStringArray(R.array.email_dipendente_xml);
        String[] ruoliDipendenti = getResources().getStringArray(R.array.ruolo_dipendente_xml);

        for(int i=0; i<nomiDipendenti.length; i++){

            dipendenti.add(Utente.creaUtenteConRuolo(nomiDipendenti[i], cognomiDipendenti[i], emailDipendenti[i], ruoliDipendenti[i], false));

        }
    }

    @Override
    public void onDipendenteClicked(int posizioneDipendente) {

        visualizzaInfoDipendente(dipendenti.get(posizioneDipendente));
    }

    public void visualizzaInfoDipendente(Utente dipendenteScelto) {

        final View viewVisualizzaDipendente = getLayoutInflater().inflate(R.layout.layout_visualizza_dipendente_dialog, null);

        builderDialogVisualizzaDipendente = new AlertDialog.Builder(getContext());
        builderDialogVisualizzaDipendente.setView(viewVisualizzaDipendente);
        builderDialogVisualizzaDipendente.setCancelable(true);

        textViewNomeDipendente = viewVisualizzaDipendente.findViewById(R.id.textViewNomeRistoranteVisualizza);
        textViewEmailDipendente = viewVisualizzaDipendente.findViewById(R.id.textViewIndirizzoRistoranteVisualizza);
        spinnerRuoloDipendente = viewVisualizzaDipendente.findViewById(R.id.spinnerRuoliDipendenteVisualizza);
        bottoneLicenzia = viewVisualizzaDipendente.findViewById(R.id.buttonLicenziaDipendente);

        String ruoli [] = new String[] {"Amministratore", "Supervisore", "Addetto alla cucina", "Addetto al servizio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(viewVisualizzaDipendente.getContext(), R.layout.spinner_layout, ruoli);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinnerRuoloDipendente.setAdapter(adapter);

        textViewNomeDipendente.setText(dipendenteScelto.getNomeCompleto());
        textViewEmailDipendente.setText(dipendenteScelto.getEmail());

        int posizioneRuoloCorrente = adapter.getPosition(dipendenteScelto.getRuoloUtente());
        spinnerRuoloDipendente.setSelection(posizioneRuoloCorrente);

        bottoneLicenzia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogVisualizzaDipendente.dismiss();
            }
        });

        dialogVisualizzaDipendente = builderDialogVisualizzaDipendente.create();
        dialogVisualizzaDipendente.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogVisualizzaDipendente.show();


    }
}