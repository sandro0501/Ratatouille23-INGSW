package com.example.ratatouille23.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Preparazione;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.listaAllergeni;
import com.example.ratatouille23.Presenters.PresenterMenu;
import com.example.ratatouille23.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements RecyclerViewSezioneMenuInterface, RecyclerViewElementoMenuInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView iconaCestino;
    private Spinner spinnerSceltaAggiunta;
    private TextView textViewSpinner;

    private TextView titoloDialog;
    private EditText titoloPrincipaleElementoEditText;
    private EditText titoloSecondarioElementoEditText;
    private EditText descrizionePrincipaleElementoEditText;
    private EditText descrizioneSecondariaElementoEditText;
    private EditText prezzoElementoEditText;
    private Spinner sezioneElementoSpinner;
    private Button bottoneConferma;
    private Button bottoneAnnulla;


    private RecyclerView recyclerViewMenu;
    private SezioneMenuRecyclerViewAdapter adapterSezioni;
    private ArrayList<SezioneMenu> listaSezioni = new ArrayList<>();
    private ArrayList<Elemento> listaElementiSelezionati = new ArrayList<>();

    private ItemTouchHelper itemTouchHelper;
    private ArrayList<CardView> listaCardElementiSelezionati = new ArrayList<>();

    private AlertDialog.Builder builderDialogElemento;
    private Dialog dialogElemento;

    private boolean modalitaEliminazione = false;

    public MenuFragment() {
        // Required empty public constructor
    }

    public boolean isModalitaEliminazione() {
        return modalitaEliminazione;
    }

    public void setModalitaEliminazione(boolean modalitaEliminazione) {
        this.modalitaEliminazione = modalitaEliminazione;
    }

    public SezioneMenuRecyclerViewAdapter getAdapterSezioni() {
        return adapterSezioni;
    }

    public void setAdapterSezioni(SezioneMenuRecyclerViewAdapter adapterSezioni) {
        this.adapterSezioni = adapterSezioni;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return itemTouchHelper;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentCorrente = inflater.inflate(R.layout.fragment_menu, container, false);

        return fragmentCorrente;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerViewMenu = view.findViewById(R.id.recyclerViewSezioniMenu);
        spinnerSceltaAggiunta = view.findViewById(R.id.spinnerSceltaAggiuntaSezioneElementoMenu);
        iconaCestino = view.findViewById(R.id.iconaCestinoMenu);
        textViewSpinner = view.findViewById(R.id.textViewSceltaAggiunta);

        riempiSezioni();

        adapterSezioni = new SezioneMenuRecyclerViewAdapter(getContext(), listaSezioni, this);
        recyclerViewMenu.setAdapter(adapterSezioni);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSezioni.notifyDataSetChanged();

        String sceltaElementoSezione[] = {"Aggiungi una sezione", "Aggiungi un piatto", "Placeholder"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_menu_layout, sceltaElementoSezione) {
            @Override
            public int getCount() {
                return (2); // Truncate the list
            }
        };

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinnerSceltaAggiunta.setAdapter(spinnerAdapter);
        spinnerSceltaAggiunta.setSelection(2);

        iconaCestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaElementiSelezionati.size() == 0)
                    attivaDisattivaModalitaEliminazione();
                else
                    eliminaProdottiSelezionati();
            }
        });

        spinnerSceltaAggiunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerAdapter.getItem(i).equals("Aggiungi una sezione")) {
                    SezioneMenu nuovaSezione = new SezioneMenu("Nuova sezione", listaSezioni.size());
                    listaSezioni.add(nuovaSezione);
                    adapterSezioni.notifyDataSetChanged();
                    spinnerSceltaAggiunta.setSelection(2);
                }
                else if (spinnerAdapter.getItem(i).equals("Aggiungi un piatto")){
                    mostraDialogAggiuntaElemento();
                    spinnerSceltaAggiunta.setSelection(2);
                }
                else {
                    spinnerSceltaAggiunta.setSelection(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

         itemTouchHelper = new ItemTouchHelper(simpleCallbackSezioni);
         itemTouchHelper.attachToRecyclerView(recyclerViewMenu);

    }

    private void mostraDialogAggiuntaElemento() {
        final View viewAggiungiElemento = getLayoutInflater().inflate(R.layout.layout_aggiungi_elemento_dialog, null);

        builderDialogElemento = new AlertDialog.Builder(getContext());
        builderDialogElemento.setView(viewAggiungiElemento);
        builderDialogElemento.setCancelable(true);

        bottoneConferma = (Button) viewAggiungiElemento.findViewById(R.id.bottoneAggiungiModificaElemento);
        bottoneAnnulla = (Button) viewAggiungiElemento.findViewById(R.id.bottoneAnnullaElemento);

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PresenterMenu.getInstance().mostraAlert(getContext(), "Piatto aggiunto", "Piatto aggiunto correttamente al menù");
                dialogElemento.dismiss();
            }
        });

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogElemento.dismiss();
            }
        });

        dialogElemento = builderDialogElemento.create();
        dialogElemento.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogElemento.show();

    }

    private void eliminaProdottiSelezionati() {
        for (SezioneMenu sezioneCorrente : listaSezioni) {
            sezioneCorrente.getAppartenente().removeAll(listaElementiSelezionati);
        }
        ((SezioneMenuRecyclerViewAdapter)recyclerViewMenu.getAdapter()).notifyDataSetChanged();
        attivaDisattivaModalitaEliminazione();
    }

    private void attivaDisattivaModalitaEliminazione() {
        int width = iconaCestino.getWidth();
        int height = iconaCestino.getHeight();
        if (modalitaEliminazione) {

            modalitaEliminazione = false;
            iconaCestino.setImageResource(R.drawable.icon_rimuovi_elemento);
            iconaCestino.requestLayout();
            iconaCestino.getLayoutParams().height = height;
            iconaCestino.getLayoutParams().width = width;
            deselezionaTuttiElementi();
            spinnerSceltaAggiunta.setVisibility(View.VISIBLE);
            textViewSpinner.setVisibility(View.VISIBLE);
        }
        else {
            modalitaEliminazione = true;
            iconaCestino.setImageResource(R.drawable.icon_rimuovi_elemento_selected);
            iconaCestino.requestLayout();
            iconaCestino.getLayoutParams().height = height;
            iconaCestino.getLayoutParams().width = width;
            spinnerSceltaAggiunta.setVisibility(View.INVISIBLE);
            textViewSpinner.setVisibility(View.INVISIBLE);
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallbackSezioni = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

        SezioneMenuRecyclerViewAdapter.MyViewHolder holderCorrente;

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            holderCorrente = (SezioneMenuRecyclerViewAdapter.MyViewHolder)viewHolder;

            int posizioneIniziale = viewHolder.getAdapterPosition();
            int posizioneFinale = target.getAdapterPosition();

            Collections.swap(listaSezioni, posizioneIniziale, posizioneFinale);

            listaSezioni.get(posizioneIniziale).setPosizione(posizioneIniziale);
            listaSezioni.get(posizioneFinale).setPosizione(posizioneFinale);

            recyclerView.getAdapter().notifyItemMoved(posizioneIniziale, posizioneFinale);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
        @Override
        public boolean isLongPressDragEnabled()
        {
            return false;
        }
    };

    private void riempiSezioni() {
        Elemento el1 = new Elemento("Pasta al pesto", "Famosa pasta italiana", 5.15, 1);
        Elemento el2 = new Elemento("Pasta al pomodoro", "Famosa pasta italiana", 5.64, 2);

        Elemento el4 = new Elemento("Pasta alla siciliana", "Famosa pasta italiana", 8.64, 3);
        Elemento el3 = new Elemento("Agnello alla brace", "Famosa carne italiana", 25.15, 1);
        Elemento el5 = new Elemento("Fiorentina", "Famosa carne italiana", 25.15, 1);

        ArrayList<Allergene> l1 = new ArrayList<>();
        l1.add(new Allergene(listaAllergeni.Uova));
        l1.add(new Allergene(listaAllergeni.Glutine));
        ArrayList<Allergene> l2 = new ArrayList<>(l1);
        l2.add(new Allergene(listaAllergeni.Lattosio));
        el1.setPresenta(l1);
        el3.setPresenta(l2);

        Prodotto passata = new Prodotto("Passata di pomodoro", "Passata buona", "kg", "2.30", 10, 5);
        Prodotto pasta = new Prodotto("Maccheroni", "maccarun", "kg", "1.00", 20, 10);
        Double quantitaPassata = 2.00;
        Double quantitaPasta = 3.00;

        Preparazione preparazionePastaAlPomodoroUno = new Preparazione(passata, quantitaPassata);
        Preparazione preparazionePastaAlPomodoroDue = new Preparazione(pasta, quantitaPasta);

        ArrayList<Preparazione> preparazionePastaAlPomodoro = new ArrayList<Preparazione>();
        preparazionePastaAlPomodoro.add(preparazionePastaAlPomodoroUno);
        preparazionePastaAlPomodoro.add(preparazionePastaAlPomodoroDue);

        el2.setPreparazione(preparazionePastaAlPomodoro);

        ArrayList<Elemento> listaE1 = new ArrayList<>();
        listaE1.add(el1);
        listaE1.add(el2);
        listaE1.add(el4);
        ArrayList<Elemento> listaE2 = new ArrayList<>();
        listaE2.add(el3);
        listaE2.add(el5);
        SezioneMenu s1 = new SezioneMenu("Primi piatti",1, listaE1);
        SezioneMenu s2 = new SezioneMenu("Secondi piatti", 2, listaE2);

        listaSezioni.add(s1);
        listaSezioni.add(s2);
    }

    @Override
    public void onElementoClicked(Elemento elementoCliccato, View itemView) {
        if (modalitaEliminazione) {
            selezionaDeseleziona(elementoCliccato, itemView);
        }
        else {
            //modifica elemento
            Log.println(Log.VERBOSE, "aa", elementoCliccato.getDenominazionePrincipale());
            mostraDialogModificaElemento(elementoCliccato);


        }
    }

    private void mostraDialogModificaElemento(Elemento elementoDaModificare) {
        final View viewModificaElemento = getLayoutInflater().inflate(R.layout.layout_aggiungi_elemento_dialog, null);

        builderDialogElemento = new AlertDialog.Builder(getContext());
        builderDialogElemento.setView(viewModificaElemento);
        builderDialogElemento.setCancelable(true);

        titoloDialog = (TextView) viewModificaElemento.findViewById(R.id.textViewAggiungiElementoTitolo);
        titoloDialog.setText("Modifica piatto del menù");

        titoloPrincipaleElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.EditTextTitoloPrincipaleElemento);
        titoloPrincipaleElementoEditText.append(elementoDaModificare.getDenominazionePrincipale());

        titoloSecondarioElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.EditTextTitoloSecondarioElemento);
        if(elementoDaModificare.getDenominazioneSecondaria().isEmpty()){
            titoloSecondarioElementoEditText.setEnabled(false);
            titoloSecondarioElementoEditText.setHint("Il piatto non presenta titolo secondario");
        } else {
            titoloSecondarioElementoEditText.append(elementoDaModificare.getDenominazioneSecondaria());
        }

        descrizionePrincipaleElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.editTexDescrizionePrincipaleElemento);
        descrizionePrincipaleElementoEditText.append(elementoDaModificare.getDescrizionePrincipale());

        descrizioneSecondariaElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.editTextDescrizioneSecondariaElemento);
        if(elementoDaModificare.getDenominazioneSecondaria().isEmpty()){
            descrizioneSecondariaElementoEditText.setEnabled(false);
            descrizioneSecondariaElementoEditText.setHint("Il piatto non presenta descrizione secondaria");
        } else {
            descrizioneSecondariaElementoEditText.append(elementoDaModificare.getDenominazioneSecondaria());
        }

        prezzoElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.editTextCostoElemento);
        prezzoElementoEditText.append(String.valueOf(elementoDaModificare.getCosto()));

        sezioneElementoSpinner = (Spinner) viewModificaElemento.findViewById(R.id.spinnerSezioneElemento);
        //get sezione scelta

        //checkbox checckate?

        bottoneConferma = (Button) viewModificaElemento.findViewById(R.id.bottoneAggiungiModificaElemento);
        bottoneConferma.setText("Modifica");

        bottoneAnnulla = (Button) viewModificaElemento.findViewById(R.id.bottoneAnnullaElemento);

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //eventuali controlli sulle edittext
                PresenterMenu.getInstance().mostraAlert(getContext(), "Piatto modificato", "Piatto modificato correttamente");
                dialogElemento.dismiss();
            }
        });

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogElemento.dismiss();
            }
        });

        dialogElemento = builderDialogElemento.create();
        dialogElemento.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogElemento.show();
    }

    @Override
    public void onVediIngredientiElementoClicked(Elemento elemento, View view) {
        Intent i = new Intent(getContext(), VisualizzazioneIngredientiElementoActivity.class);
        i.putExtra("Elemento selezionato", (Serializable) elemento);
        startActivity(i);
    }

    private void selezionaDeseleziona(Elemento elementoCliccato, View itemView) {
        CardView cardElemento = itemView.findViewById(R.id.cardElementoMenu);
        if (listaElementiSelezionati.contains(elementoCliccato)) {
            listaElementiSelezionati.remove(elementoCliccato);
            listaCardElementiSelezionati.remove(cardElemento);
            cardElemento.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            listaElementiSelezionati.add(elementoCliccato);
            listaCardElementiSelezionati.add(cardElemento);
            cardElemento.setBackgroundColor(Color.parseColor("#F4B851"));
        }
    }

    @Override
    public void onStop() {
        modalitaEliminazione = true;
        attivaDisattivaModalitaEliminazione();
        super.onStop();
    }

    private void deselezionaTuttiElementi() {
        listaElementiSelezionati.clear();
        for (CardView card : listaCardElementiSelezionati) card.setBackgroundColor(Color.parseColor("#FFFFFF"));
        listaCardElementiSelezionati.clear();
    }


}