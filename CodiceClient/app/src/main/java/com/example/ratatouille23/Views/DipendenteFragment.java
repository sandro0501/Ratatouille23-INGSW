package com.example.ratatouille23.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ratatouille23.Handlers.AggiornaRuoloHandler;
import com.example.ratatouille23.Handlers.EliminaProdottiHandler;
import com.example.ratatouille23.Handlers.UtenteHandler;
import com.example.ratatouille23.Models.Amministratore;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;
import com.example.ratatouille23.Presenters.PresenterDipendenti;
import com.example.ratatouille23.Presenters.PresenterDispensa;
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
    private ArrayList<Utente> dipendenti = new ArrayList<>();
    private AlertDialog.Builder builderDialogVisualizzaDipendente;
    private Dialog dialogVisualizzaDipendente;

    private TextView textViewNomeDipendente;
    private TextView textViewEmailDipendente;
    private Spinner spinnerRuoloDipendente;
    private Button bottoneLicenzia;

    private AlertDialog.Builder builderDialogLicenziaDipendente;
    private Dialog dialogLicenziaDipendente;
    private TextView textViewLicenziaDipendente;
    private Button bottoneAnnullaLicenziaDipendente;
    private Button bottoneConfermaLicenziaDipendente;

    private Utente utenteCorrente;
    private Ristorante ristoranteCorrente;

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
                i.putExtra("Utente", utenteCorrente);
                startActivity(i);
            }
        });

        return fragmentCorrente;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewDipendenti);
        dipendenteAdapter = new DipendenteRecyclerViewAdapter(getContext(),dipendenti,this);
        recyclerView.setAdapter(dipendenteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dipendenteAdapter.notifyDataSetChanged();

        utenteCorrente = (Utente)getActivity().getIntent().getSerializableExtra("Utente");
        ristoranteCorrente = utenteCorrente.getIdRistorante();

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

        String ruoli [];

        if (((Amministratore)utenteCorrente).isSuperA())
            ruoli = new String[] {"Amministratore", "Supervisore", "Addetto alla cucina", "Addetto al servizio"};
        else
            ruoli = new String[] {"Supervisore", "Addetto alla cucina", "Addetto al servizio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(viewVisualizzaDipendente.getContext(), R.layout.spinner_layout, ruoli);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinnerRuoloDipendente.setAdapter(adapter);

        textViewNomeDipendente.setText(dipendenteScelto.getNomeCompleto());
        textViewEmailDipendente.setText(dipendenteScelto.getEmail());

        int posizioneRuoloCorrente = adapter.getPosition(dipendenteScelto.getRuoloUtente());
        spinnerRuoloDipendente.setSelection(posizioneRuoloCorrente);

        dialogVisualizzaDipendente = builderDialogVisualizzaDipendente.create();
        dialogVisualizzaDipendente.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogVisualizzaDipendente.show();


        dialogVisualizzaDipendente.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                UtenteHandler handlerUtente = new UtenteHandler(dipendenteScelto);
                handlerUtente.ruolo = ruoli[spinnerRuoloDipendente.getSelectedItemPosition()];
                AggiornaRuoloHandler handler = new AggiornaRuoloHandler();
                handler.utente = handlerUtente;
                handler.ristorante = utenteCorrente.getIdRistorante();
                PresenterDipendenti.getInstance().modificaDipendente(DipendenteFragment.this, handler);
            }
        });


        bottoneLicenzia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewLicenziaDipendente = getLayoutInflater().inflate(R.layout.layout_elimina_prodotto_dialog, null);
                builderDialogLicenziaDipendente= new AlertDialog.Builder(getContext());
                builderDialogLicenziaDipendente.setView(viewLicenziaDipendente);
                builderDialogLicenziaDipendente.setCancelable(true);
                textViewLicenziaDipendente = (TextView) viewLicenziaDipendente.findViewById(R.id.textViewEliminaProdottoDescrizioneDialog);
                textViewLicenziaDipendente.setText("Si è sicuri di voler licenziare il dipendente selezionato?");

                bottoneAnnullaLicenziaDipendente = (Button) viewLicenziaDipendente.findViewById(R.id.bottoneAnnullaEliminaProdotto);
                bottoneAnnullaLicenziaDipendente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogLicenziaDipendente.dismiss();
                    }
                });

                bottoneConfermaLicenziaDipendente = (Button) viewLicenziaDipendente.findViewById(R.id.bottoneEliminaProdotto);
                bottoneConfermaLicenziaDipendente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PresenterDipendenti.getInstance().rimuoviDipendente(DipendenteFragment.this, new UtenteHandler(dipendenteScelto));
                    }
                });

                dialogLicenziaDipendente = builderDialogLicenziaDipendente.create();
                dialogLicenziaDipendente.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                dialogLicenziaDipendente.show();

            }
        });

    }

    @Override
    public void onStart() {
        PresenterDipendenti.getInstance().recuperaDipendentiDaRistorante(this, ristoranteCorrente);
        super.onStart();
    }

    public void setListaDipendenti(ArrayList<Utente> listaDipendenti) {
        dipendenti.clear();

        for (Utente dipendente : listaDipendenti) {
            if (dipendente.getRuoloUtente().equals("Sistema")) continue;
            if (dipendente.getRuoloUtente().equals("Amministratore")) {
                if (!((Amministratore)dipendente).isSuperA()) {
                    if (utenteCorrente.getRuoloUtente().equals("Amministratore") && ((Amministratore)utenteCorrente).isSuperA())
                        dipendenti.add(dipendente);
                }
            }
            else if (dipendente.getRuoloUtente().equals("Supervisore")) {
                if (utenteCorrente.getRuoloUtente().equals("Amministratore"))
                    dipendenti.add(dipendente);
            }
            else
                dipendenti.add(dipendente);
        }

        dipendenteAdapter.notifyDataSetChanged();
    }

    public void dipendenteLicenziato() {
        PresenterDipendenti.getInstance().mostraAlert(getContext(), "Eliminazione effettuata", "Dipendente licenziato correttamente");
        dialogLicenziaDipendente.dismiss();
        dialogVisualizzaDipendente.dismiss();
        PresenterDipendenti.getInstance().recuperaDipendentiDaRistorante(this, ristoranteCorrente);
    }

    public void ruoloDipendenteModificato() {
        PresenterDipendenti.getInstance().recuperaDipendentiDaRistorante(this, ristoranteCorrente);
    }


}