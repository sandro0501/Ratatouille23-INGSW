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

import com.example.ratatouille23.Handlers.EliminaProdottiHandler;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterBacheca;
import com.example.ratatouille23.Presenters.PresenterDispensa;
import com.example.ratatouille23.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DispensaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispensaFragment extends Fragment implements RecyclerViewProdottoInterface{

    private ArrayList<Prodotto> dispensa = new ArrayList<>();
    private Ristorante ristoranteCorrente;

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
    private EditText editTextUnitaMisuraCostoProdotto;
    private AutoCompleteTextView editTextModificaNomeProdotto;
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

    private boolean modalitaEliminazione = false;

    private FirebaseAnalytics analytics;

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

        analytics = FirebaseAnalytics.getInstance(this.getActivity());

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utente utenteCorrente = (Utente)getActivity().getIntent().getSerializableExtra("Utente");
        ristoranteCorrente = utenteCorrente.getIdRistorante();

        adapterAutoComplete = new ArrayAdapter<Prodotto>(getContext(), R.layout.spinner_layout, new ArrayList<>());
        adapterAutoComplete.setNotifyOnChange(true);

        recyclerView = view.findViewById(R.id.recyclerViewDispensa);
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
                            textViewEliminazioneProdotto.setText("Si è sicuri di voler eliminare il prodotto selezionato?");
                        } else {
                            textViewEliminazioneProdotto.setText("Si è sicuri di voler eliminare i " + listaProdottiSelezionati.size() + " prodotti selezionati?");
                        }

                        bottoneAnnullaEliminazioneProdotto = (Button) viewEliminaProdotto.findViewById(R.id.bottoneAnnullaEliminaProdotto);
                        bottoneAnnullaEliminazioneProdotto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Annulla");
                                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bottone");
                                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

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
                                PresenterDispensa.getInstance().EliminaProdottoInDispensa(DispensaFragment.this, new EliminaProdottiHandler(listaProdottiSelezionati));



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

    @Override
    public void onResume() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Fragment Dispensa");
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        super.onResume();
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

        builderDialogAggiungiProdotto = new AlertDialog.Builder(getContext(), R.style.WrapContentDialog);
        builderDialogAggiungiProdotto.setView(viewAggiungiProdotto);
        builderDialogAggiungiProdotto.setCancelable(true);

        editTextNomeProdotto = (AutoCompleteTextView) viewAggiungiProdotto.findViewById(R.id.EditTextNomeProdotto);
        editTextDescrizioneProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextDescrizioneProdotto);
        editTextQuantitaProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextQuantitaProdotto);
        editTextUnitaMisuraProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextUnitaMisuraProdotto);
        editTextUnitaMisuraCostoProdotto = viewAggiungiProdotto.findViewById(R.id.EditTextUnitaMisuraCostoAcquisto);
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

                    if (areEditTextInserimentoEmpty()) {
                        mostraDialogErroreInserimentoProdotto();
                    } else {
                        Prodotto prodotto = new Prodotto();
                        prodotto.setNome(editTextNomeProdotto.getText().toString());
                        prodotto.setDescrizione(editTextDescrizioneProdotto.getText().toString());
                        prodotto.setUnita(editTextUnitaMisuraProdotto.getText().toString());
                        String costo = "€" + editTextCostoAcquistoProdotto.getText().toString() + "/" + editTextUnitaMisuraCostoProdotto.getText().toString();
                        prodotto.setCostoAcquisto(costo);
                        prodotto.setQuantita(Double.parseDouble(editTextQuantitaProdotto.getText().toString()));
                        prodotto.setSoglia(Double.parseDouble(editTextSogliaProdotto.getText().toString()));
                        prodotto.setUtilizzatoDa(ristoranteCorrente);
                        PresenterDispensa.getInstance().AggiungiProdottoInDispensa(DispensaFragment.this, prodotto);
                    }
            }
        });

        bottoneAnnullaAggiungiProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Annulla");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bottone");
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                dialogAggiungiProdotto.dismiss();
            }
        });

        dialogAggiungiProdotto = builderDialogAggiungiProdotto.create();
        dialogAggiungiProdotto.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogAggiungiProdotto.show();
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

        builderDialogModificaProdotto = new AlertDialog.Builder(getContext(), R.style.WrapContentDialog);
        builderDialogModificaProdotto.setView(viewModificaProdotto);
        builderDialogModificaProdotto.setCancelable(true);

        editTextModificaNomeProdotto = (AutoCompleteTextView) viewModificaProdotto.findViewById(R.id.EditTextNomeProdottoModifica);
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

        editTextModificaNomeProdotto.setAdapter(adapterAutoComplete);

        editTextModificaNomeProdotto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextModificaNomeProdotto.getText().toString().length() >= editTextModificaNomeProdotto.getThreshold()) {
                    PresenterDispensa.getInstance().settaProdottiDaInizialeModifica(DispensaFragment.this, editTextModificaNomeProdotto.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapterAutoComplete.clear();
            }
        });

        editTextModificaNomeProdotto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prodotto prodottoScelto = adapterAutoComplete.getItem(i);
                editTextModificaDescrizioneProdotto.setText(prodottoScelto.getDescrizione());

            }
        });

        bottoneAnnullaModificaProdotto = (Button) viewModificaProdotto.findViewById(R.id.bottoneAnnullaModificaProdotto);
        bottoneAnnullaModificaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Annulla");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bottone");
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                dialogModificaProdotto.dismiss();
            }
        });

        bottoneConfermaModificaProdotto = (Button) viewModificaProdotto.findViewById(R.id.bottoneModificaProdotto);
        bottoneConfermaModificaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.VERBOSE,"MOD","MODIFICA");

                if(areEditTextModificaEmpty()){
                  mostraDialogErroreModificaProdotto();
                } else {
                    Prodotto prodottoDaModificare = dispensa.get(posizioneProdotto);
                    prodottoDaModificare.setNome(editTextModificaNomeProdotto.getText().toString());
                    prodottoDaModificare.setDescrizione(editTextModificaDescrizioneProdotto.getText().toString());
                    prodottoDaModificare.setUnita(editTextModificaUnitaMisuraProdotto.getText().toString());
                    String costoModificato = "€"+editTextModificaCostoAcquistoProdotto.getText().toString()+"/"+editTextModificaUnitaMisuraCostoAcquistoProdotto.getText().toString();
                    prodottoDaModificare.setCostoAcquisto(costoModificato);
                    prodottoDaModificare.setQuantita(Double.parseDouble(editTextModificaQuantitaProdotto.getText().toString()));
                    prodottoDaModificare.setSoglia(Double.parseDouble(editTextModificaSogliaProdotto.getText().toString()));
                    prodottoDaModificare.setUtilizzatoDa(ristoranteCorrente);

                    PresenterDispensa.getInstance().ModificaProdottoInDispensa(DispensaFragment.this, prodottoDaModificare);
                }
            }
        });

        dialogModificaProdotto = builderDialogModificaProdotto.create();
        dialogModificaProdotto.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogModificaProdotto.show();
    }

    @Override
    public void onStart() {
        PresenterDispensa.getInstance().ottieniDispensaDaRistorante(this, ristoranteCorrente);

        super.onStart();
    }

    public void setupListaProdottiOpenFoodFacts(ArrayList<Prodotto> lista){
        adapterAutoComplete.addAll(lista);
        adapterAutoComplete.getFilter().filter(null);
    }

    public void riempiDispensa(ArrayList<Prodotto> listaProdotti){
        dispensa.clear();
        Collections.sort(listaProdotti, new Comparator<Prodotto>() {
            @Override
            public int compare(Prodotto prodottoUno, Prodotto prodottoDue) {
                return prodottoUno.getNome().compareTo(prodottoDue.getNome());
            }
        });
        dispensa.addAll(listaProdotti);
        prodottoAdapter.notifyDataSetChanged();
    }

    public void mostraDialogConfermaInserimentoProdotto() {
        dialogAggiungiProdotto.dismiss();
        PresenterDispensa.getInstance().mostraAlert(getContext(), "Prodotto aggiunto!", "Prodotto aggiunto correttamente alla dispensa");
        PresenterDispensa.getInstance().ottieniDispensaDaRistorante(this, ristoranteCorrente);
    }

    public void mostraDialogErroreInserimentoProdotto() {
        PresenterBacheca.getInstance().mostraAlert(getContext(), "Attenzione!", "C'è stato un errore durante l'inserimento del prodotto.\nSi controlli che i campi contrassegnati dall'asterisco non siano vuoti e si riprovi.");
    }

    public void mostraDialogErroreModificaProdotto() {
        PresenterBacheca.getInstance().mostraAlert(getContext(), "Attenzione!", "C'è stato un errore durante la modifica del prodotto.\nSi controlli che i campi contrassegnati dall'asterisco non siano vuoti e si riprovi.");
    }

    public void setupListaProdottiOpenFoodFactsModifica(ArrayList<Prodotto> listaProdottiOttenuta) {
        adapterAutoComplete.addAll(listaProdottiOttenuta);
        adapterAutoComplete.getFilter().filter(null);
    }

    public void prodottoInDispensaModificato() {
        PresenterDispensa.getInstance().mostraAlert(getContext(),"Prodotto modificato", "Informazioni del prodotto modificate correttamente!");
        dialogModificaProdotto.dismiss();
        PresenterDispensa.getInstance().ottieniDispensaDaRistorante(DispensaFragment.this, ristoranteCorrente);
    }

    public void prodottoInDispensaEliminato() {
        PresenterDispensa.getInstance().mostraAlert(getContext(), "Eliminazione effettuata", "Eliminazione dei prodotti selezionati effettuata correttamente!");
        disattivaModalitaEliminazione();
        deselezionaTuttiProdotti();
        dialogEliminaProdotto.dismiss();
        PresenterDispensa.getInstance().ottieniDispensaDaRistorante(DispensaFragment.this, ristoranteCorrente);
    }

    public Boolean areEditTextInserimentoEmpty() {
        if (editTextNomeProdotto.getText().toString().matches("") ||
                editTextUnitaMisuraProdotto.getText().toString().matches("") ||
                editTextCostoAcquistoProdotto.getText().toString().matches("") ||
                editTextUnitaMisuraCostoProdotto.getText().toString().matches("") ||
                editTextQuantitaProdotto.getText().toString().matches("") ||
                editTextSogliaProdotto.getText().toString().matches("")){
            return  true;
        }
        return false;
    }

    public Boolean areEditTextModificaEmpty() {
        if(editTextModificaNomeProdotto.getText().toString().matches("")||
                editTextModificaUnitaMisuraProdotto.getText().toString().matches("") ||
                editTextModificaSogliaProdotto.getText().toString().matches("") ||
                editTextModificaCostoAcquistoProdotto.getText().toString().matches("") ||
                editTextModificaUnitaMisuraCostoAcquistoProdotto.getText().toString().matches("") ||
                editTextModificaQuantitaProdotto.getText().toString().matches("")){
            return true;
        }
        return false;
    }


}