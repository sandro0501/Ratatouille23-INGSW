package com.example.ratatouille23.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Presenters.PresenterDispensa;
import com.example.ratatouille23.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DispensaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispensaFragment extends Fragment implements RecyclerViewProdottoInterface{

    private ArrayList<Prodotto> dispensa;
    private ProdottoRecyclerViewAdapter prodottoAdapter;
    private RecyclerView recyclerView;
    private ImageView bottoneAggiungiProdotto;
    private ImageView bottoneEliminaProdotto;
    private AlertDialog.Builder builderDialogAggiungiProdotto;
    private Dialog dialogAggiungiProdotto;
    private AlertDialog.Builder builderDialogModificaProdotto;
    private Dialog dialogModificaProdotto;
    private AlertDialog.Builder builderDialogEliminaProdotto;
    private Dialog dialogEliminaProdotto;
    private TextView textViewEliminazioneProdotto;
    private AutoCompleteTextView editTextNomeProdotto;
    private EditText editTextDescrizioneProdotto;
    private EditText editTextQuantitaProdotto;
    private EditText editTextUnitaMisuraProdotto;
    private EditText editTextCostoAcquistoProdotto;
    private EditText editTextSogliaProdotto;
    private EditText editTextModificaNomeProdotto;
    private EditText editTextModificaDescrizioneProdotto;
    private EditText editTextModificaQuantitaProdotto;
    private EditText editTextModificaUnitaMisuraProdotto;
    private EditText editTextModificaCostoAcquistoProdotto;
    private EditText editTextModificaUnitaMisuraCostoAcquistoProdotto;
    private EditText editTextModificaSogliaProdotto;
    private Button bottoneConfermaAggiungiProdotto;
    private Button bottoneAnnullaAggiungiProdotto;
    private Button bottoneAnnullaModificaProdotto;
    private Button bottoneConfermaModificaProdotto;
    private Button bottoneConfermaEliminazioneProdotto;
    private Button bottoneAnnullaEliminazioneProdotto;

    private ArrayList<Prodotto> listaProdottiSelezionati = new ArrayList<>();
    private ArrayList<CardView> listaCardProdottiSelezionati = new ArrayList<>();

    private ArrayAdapter<Prodotto> adapterAutoComplete;
    private ArrayList<Prodotto> listaAutoComplete = new ArrayList<>();

    private boolean modalitaEliminazione = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DispensaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InventarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DispensaFragment newInstance(String param1, String param2) {
        DispensaFragment fragment = new DispensaFragment();
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
        //return inflater.inflate(R.layout.fragment_dispensa, container, false);

        View view = inflater.inflate(R.layout.fragment_dispensa, container, false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapterAutoComplete = new ArrayAdapter<Prodotto>(getContext(), R.layout.spinner_layout, listaAutoComplete);
        adapterAutoComplete.setNotifyOnChange(true);

        recyclerView = view.findViewById(R.id.recyclerViewDispensa);
        riempiDispensa();
        prodottoAdapter = new ProdottoRecyclerViewAdapter(getContext(),dispensa,this);
        recyclerView.setAdapter(prodottoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        prodottoAdapter.notifyDataSetChanged();

        bottoneAggiungiProdotto = (ImageView) view.findViewById(R.id.imageViewIconAddProdotto);
        bottoneAggiungiProdotto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mostraDialogInserimentoProdotto();
            }
        });

        bottoneEliminaProdotto = (ImageView) view.findViewById(R.id.imageViewIconEliminaProdotto);
        bottoneEliminaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modalitaEliminazione) {
                    if (listaProdottiSelezionati.size() == 0) disattivaModalitaEliminazione();
                    else {
                        final View viewEliminaProdotto = getLayoutInflater().inflate(R.layout.layout_elimina_prodotto_dialog, null);
                        builderDialogEliminaProdotto = new AlertDialog.Builder(getContext());
                        builderDialogEliminaProdotto.setView(viewEliminaProdotto);
                        builderDialogEliminaProdotto.setCancelable(true);

                        textViewEliminazioneProdotto = (TextView) viewEliminaProdotto.findViewById(R.id.textViewEliminaProdottoDescrizioneDialog);
                        if (listaProdottiSelezionati.size() == 1) {
                            textViewEliminazioneProdotto.setText("Sei sicuro di voler eliminare il prodotto selezionato?");
                        } else {
                            textViewEliminazioneProdotto.setText("Sei sicuro di voler eliminare i " + listaProdottiSelezionati.size() + " prodotti selezionati?");
                        }

                        bottoneAnnullaEliminazioneProdotto = (Button) viewEliminaProdotto.findViewById(R.id.bottoneAnnullaEliminaProdotto);
                        bottoneAnnullaEliminazioneProdotto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                disattivaModalitaEliminazione();
                                deselezionaTuttiProdotti();
                                dialogEliminaProdotto.dismiss();
                            }
                        });

                        bottoneConfermaEliminazioneProdotto = (Button) viewEliminaProdotto.findViewById(R.id.bottoneEliminaProdotto);
                        bottoneConfermaEliminazioneProdotto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //codice db
                                PresenterDispensa.getInstance().mostraAlert(getContext(), "Eliminazione effettuata", "Eliminazione dei prodotti selezionati effettuata correttamente!");
                                disattivaModalitaEliminazione();
                                deselezionaTuttiProdotti();
                                dialogEliminaProdotto.dismiss();
                            }
                        });

                        dialogEliminaProdotto = builderDialogEliminaProdotto.create();
                        dialogEliminaProdotto.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                        dialogEliminaProdotto.show();
                    }
                }
                else {
                    modalitaEliminazione = true;
                    bottoneAggiungiProdotto.setEnabled(false);
                    bottoneEliminaProdotto.setImageResource(R.drawable.icon_modalita_elimina_prodotto_attiva);
                    Toast.makeText(getContext(), "Modalità eliminazione attiva", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void disattivaModalitaEliminazione() {
        modalitaEliminazione = false;
        bottoneAggiungiProdotto.setEnabled(true);
        bottoneEliminaProdotto.setImageResource(R.drawable.icon_elimina_prodotto_dispensa);
    }

    private void deselezionaTuttiProdotti() {
        modalitaEliminazione = false;
        for (CardView card : listaCardProdottiSelezionati) card.setCardBackgroundColor(Color.WHITE);
        listaCardProdottiSelezionati.clear();
        listaProdottiSelezionati.clear();
    }

    private void mostraDialogInserimentoProdotto() {
        final View viewAggiungiProdotto = getLayoutInflater().inflate(R.layout.layout_aggiungi_prodotto_dialog, null);

        builderDialogAggiungiProdotto = new AlertDialog.Builder(getContext());
        builderDialogAggiungiProdotto.setView(viewAggiungiProdotto);
        builderDialogAggiungiProdotto.setCancelable(true);

        editTextNomeProdotto = (AutoCompleteTextView) viewAggiungiProdotto.findViewById(R.id.EditTextNomeProdotto);
        editTextDescrizioneProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextDescrizioneProdotto);
        editTextQuantitaProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextQuantitaProdotto);
        editTextUnitaMisuraProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextUnitaMisuraProdotto);
        editTextCostoAcquistoProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextCostoAcquistoProdotto);
        editTextSogliaProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextSogliaLimiteProdotto);

        bottoneConfermaAggiungiProdotto = (Button) viewAggiungiProdotto.findViewById(R.id.bottoneAggiungiProdotto);
        bottoneAnnullaAggiungiProdotto = (Button) viewAggiungiProdotto.findViewById(R.id.bottoneAnnullaAggiungiProdotto);

        editTextNomeProdotto.setAdapter(adapterAutoComplete);

        editTextNomeProdotto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextNomeProdotto.getText().toString().length() >= editTextNomeProdotto.getThreshold()) {
                    PresenterDispensa.getInstance().settaProdottiDaIniziale(DispensaFragment.this, editTextNomeProdotto.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapterAutoComplete.clear();
            }
        });

        editTextNomeProdotto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prodotto prodottoScelto = adapterAutoComplete.getItem(i);
                editTextDescrizioneProdotto.setText(prodottoScelto.getDescrizione());

            }
        });

        bottoneConfermaAggiungiProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //metodo controller aggiungi prodotto che interagisce col DB
                Log.println(Log.VERBOSE,"ADD","AGGIUNGI");
                PresenterDispensa.getInstance().mostraAlert(getContext(),"Prodotto aggiunto", "Prodotto aggiunto correttamente alla dispensa!");
                dialogAggiungiProdotto.dismiss();

            }
        });

        bottoneAnnullaAggiungiProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAggiungiProdotto.dismiss();
            }
        });

        dialogAggiungiProdotto = builderDialogAggiungiProdotto.create();
        dialogAggiungiProdotto.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogAggiungiProdotto.show();
    }

    private void riempiDispensa(){
        dispensa = new ArrayList<>();

        String[] nomeProdotto = getResources().getStringArray(R.array.nome_prodotto);
        String[] descrizoneProdotto = getResources().getStringArray(R.array.descrizione_prodotto);
        String[] unitaProdotto = getResources().getStringArray(R.array.unita_prodotto);
        String[] costoAcquistoProdotto = getResources().getStringArray(R.array.costoacq_prodotto);
        String[] quantitaProdotto = getResources().getStringArray(R.array.quantita_prodotto);
        String[] sogliaProdotto = getResources().getStringArray(R.array.soglia_prodotto);


        for(int i=0; i<nomeProdotto.length; i++){
            Prodotto prodotto = new Prodotto(nomeProdotto[i],descrizoneProdotto[i],unitaProdotto[i],costoAcquistoProdotto[i],
                    Double.parseDouble(quantitaProdotto[i]),Double.parseDouble(sogliaProdotto[i]));
            dispensa.add(prodotto);
        }
    }

    @Override
    public void onProdottoClicked(int posizioneProdotto, View itemView) {

        if(!modalitaEliminazione){
            mostraDialogModificaProdotto(posizioneProdotto);
        } else {
            selezionaDeselezionaProdotto(posizioneProdotto, itemView);
        }
    }

    @Override
    public void onStop() {
        if (modalitaEliminazione){
            deselezionaTuttiProdotti();
            disattivaModalitaEliminazione();
        }
        super.onStop();
    }

    private void selezionaDeselezionaProdotto(int posizioneProdotto, View itemView) {
        Prodotto prodottoCorrente = dispensa.get(posizioneProdotto);
        if (!listaProdottiSelezionati.contains(prodottoCorrente)) {
            listaProdottiSelezionati.add(prodottoCorrente);
            CardView cardProdotto = itemView.findViewById(R.id.cardViewProdotto);
            cardProdotto.setCardBackgroundColor(Color.parseColor("#F4B851"));
            listaCardProdottiSelezionati.add(cardProdotto);
        }
        else {
            listaProdottiSelezionati.remove(prodottoCorrente);
            CardView cardProdotto = itemView.findViewById(R.id.cardViewProdotto);
            cardProdotto.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            listaCardProdottiSelezionati.remove(cardProdotto);
        }
    }

    private void mostraDialogModificaProdotto(int posizioneProdotto) {
        final View viewModificaProdotto = getLayoutInflater().inflate(R.layout.layout_modifica_prodotto_dialog, null);

        builderDialogModificaProdotto = new AlertDialog.Builder(getContext());
        builderDialogModificaProdotto.setView(viewModificaProdotto);
        builderDialogModificaProdotto.setCancelable(true);

        editTextModificaNomeProdotto = (EditText) viewModificaProdotto.findViewById(R.id.EditTextNomeProdottoModifica);
        editTextModificaDescrizioneProdotto = (EditText) viewModificaProdotto.findViewById(R.id.EditTextDescrizioneProdottoModifica);
        editTextModificaQuantitaProdotto = (EditText) viewModificaProdotto.findViewById(R.id.EditTextQuantitaProdottoModifica);
        editTextModificaUnitaMisuraProdotto = (EditText) viewModificaProdotto.findViewById(R.id.EditTextUnitaMisuraProdottoModifica);
        editTextModificaCostoAcquistoProdotto = (EditText) viewModificaProdotto.findViewById(R.id.EditTextCostoAcquistoProdottoModifica);
        editTextModificaUnitaMisuraCostoAcquistoProdotto = (EditText) viewModificaProdotto.findViewById(R.id.EditTextUnitaMisuraCostoAcquistoModifica);
        editTextModificaSogliaProdotto = (EditText) (EditText) viewModificaProdotto.findViewById(R.id.EditTextSogliaLimiteProdottoModifica);

        editTextModificaNomeProdotto.append(dispensa.get(posizioneProdotto).getNome());
        editTextModificaDescrizioneProdotto.append(dispensa.get(posizioneProdotto).getDescrizione());
        editTextModificaQuantitaProdotto.append(Double.toString(dispensa.get(posizioneProdotto).getQuantita()));
        editTextModificaUnitaMisuraProdotto.append(dispensa.get(posizioneProdotto).getUnita());
        editTextModificaSogliaProdotto.append(Double.toString(dispensa.get(posizioneProdotto).getSoglia()));

        String costoAcquistoProdotto = dispensa.get(posizioneProdotto).getCostoAcquisto().replaceAll(",",".");

        editTextModificaCostoAcquistoProdotto.append(costoAcquistoProdotto.substring(costoAcquistoProdotto.indexOf("€") , costoAcquistoProdotto.indexOf("/")));
        editTextModificaUnitaMisuraCostoAcquistoProdotto.append(costoAcquistoProdotto.substring(costoAcquistoProdotto.lastIndexOf("/")+1));


        bottoneAnnullaModificaProdotto = (Button) viewModificaProdotto.findViewById(R.id.bottoneAnnullaModificaProdotto);
        bottoneAnnullaModificaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogModificaProdotto.dismiss();
            }
        });

        bottoneConfermaModificaProdotto = (Button) viewModificaProdotto.findViewById(R.id.bottoneModificaProdotto);
        bottoneConfermaModificaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.VERBOSE,"MOD","MODIFICA");
                PresenterDispensa.getInstance().mostraAlert(getContext(),"Prodotto modificato", "Informazioni del prodotto modificate correttamente!");
                dialogModificaProdotto.dismiss();
            }
        });

        dialogModificaProdotto = builderDialogModificaProdotto.create();
        dialogModificaProdotto.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogModificaProdotto.show();
    }

    public void setupListaProdottiOpenFoodFacts(ArrayList<Prodotto> lista){
        adapterAutoComplete.addAll(lista);
        adapterAutoComplete.getFilter().filter(editTextNomeProdotto.getText(), editTextNomeProdotto);
    }

}