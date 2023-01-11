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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.Models.Prodotto;
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
    private EditText editTextNomeProdotto;
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
    private Button bottoneAnullaEliminazioneProdotto;
    int counterProdotti = 0;

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
                Controller.setIsModalitaEliminazioneProdottoAttiva(true);
                bottoneAggiungiProdotto.setEnabled(false);
                bottoneEliminaProdotto.setImageResource(R.drawable.icon_modalita_elimina_prodotto_attiva);
                Toast.makeText(getContext(),"Modalità eliminazione attiva",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void mostraDialogInserimentoProdotto() {
        final View viewAggiungiProdotto = getLayoutInflater().inflate(R.layout.layuot_aggiungi_prodotto_dialog, null);

        builderDialogAggiungiProdotto = new AlertDialog.Builder(getContext());
        builderDialogAggiungiProdotto.setView(viewAggiungiProdotto);
        builderDialogAggiungiProdotto.setCancelable(true);

        editTextNomeProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextNomeProdotto);
        editTextDescrizioneProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextDescrizioneProdotto);
        editTextQuantitaProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextQuantitaProdotto);
        editTextUnitaMisuraProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextUnitaMisuraProdotto);
        editTextCostoAcquistoProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextCostoAcquistoProdotto);
        editTextSogliaProdotto = (EditText) viewAggiungiProdotto.findViewById(R.id.EditTextSogliaLimiteProdotto);

        bottoneConfermaAggiungiProdotto = (Button) viewAggiungiProdotto.findViewById(R.id.bottoneAggiungiProdotto);
        bottoneAnnullaAggiungiProdotto = (Button) viewAggiungiProdotto.findViewById(R.id.bottoneAnnullaAggiungiProdotto);

        bottoneConfermaAggiungiProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //metodo controller aggiungi prodotto che interagisce col DB
                Log.println(Log.VERBOSE,"ADD","AGGIUNGI");
                Controller.getInstance().mostraAlertErrore(getContext(),"Prodotto aggiunto", "Prodotto aggiunto correttamente alla dispensa!");
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

        if(Controller.getIsModalitaEliminazioneProdottoAttiva() == false){
            mostraDialogModificaProdotto(posizioneProdotto);
        } else {
            attivaModalitaEliminazioneProdotto(posizioneProdotto, itemView);
        }
    }

    private void attivaModalitaEliminazioneProdotto(int posizioneProdotto, View itemView) {
        Prodotto prodottoCorrente = dispensa.get(posizioneProdotto);
        prodottoCorrente.setSelected(!prodottoCorrente.isSelected());

        if(prodottoCorrente.isSelected()){
            counterProdotti++;
            CardView cardProdotto = itemView.findViewById(R.id.cardViewProdotto);
            cardProdotto.setCardBackgroundColor(Color.parseColor("#F2A726"));

            bottoneEliminaProdotto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final View viewEliminaProdotto = getLayoutInflater().inflate(R.layout.layout_elimina_prodotto_dialog, null);
                    builderDialogEliminaProdotto = new AlertDialog.Builder(getContext());
                    builderDialogEliminaProdotto.setView(viewEliminaProdotto);
                    builderDialogEliminaProdotto.setCancelable(true);

                    textViewEliminazioneProdotto = (TextView) viewEliminaProdotto.findViewById(R.id.textViewEliminaProdottoDescrizioneDialog);
                    if(counterProdotti==1){
                        textViewEliminazioneProdotto.setText("Sei sicuro di voler eliminare il prodotto selezionato?");
                    } else {
                        textViewEliminazioneProdotto.setText("Sei sicuro di voler eliminare i "+counterProdotti+" prodotti selezionati?");
                    }

                    bottoneAnullaEliminazioneProdotto = (Button) viewEliminaProdotto.findViewById(R.id.bottoneAnnullaEliminaProdotto);
                    bottoneAnullaEliminazioneProdotto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Controller.setIsModalitaEliminazioneProdottoAttiva(false);
                            bottoneAggiungiProdotto.setEnabled(true);
                            bottoneEliminaProdotto.setImageResource(R.drawable.icon_elimina_prodotto_dispensa);
                            cardProdotto.setCardBackgroundColor(Color.WHITE);
                            prodottoCorrente.setSelected(false);
                            dialogEliminaProdotto.dismiss();
                        }
                    });

                    bottoneConfermaEliminazioneProdotto = (Button) viewEliminaProdotto.findViewById(R.id.bottoneEliminaProdotto);
                    bottoneConfermaEliminazioneProdotto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //codice db
                            Controller.getInstance().mostraAlertErrore(getContext(),"Eliminazione effettuata", "Eliminazione dei prodotti selezionati effettuata correttamente!");
                            Controller.setIsModalitaEliminazioneProdottoAttiva(false);
                            bottoneAggiungiProdotto.setEnabled(true);
                            bottoneEliminaProdotto.setImageResource(R.drawable.icon_elimina_prodotto_dispensa);
                            prodottoCorrente.setSelected(false);
                            cardProdotto.setCardBackgroundColor(Color.WHITE);
                            dialogEliminaProdotto.dismiss();
                        }
                    });

                    dialogEliminaProdotto = builderDialogEliminaProdotto.create();
                    dialogEliminaProdotto.show();
                }
            });
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

        String costoAcqusitoProdotto = dispensa.get(posizioneProdotto).getCostoAcquisto().replaceAll(",",".");

        editTextModificaCostoAcquistoProdotto.append(costoAcqusitoProdotto.substring(costoAcqusitoProdotto.indexOf("€") , costoAcqusitoProdotto.indexOf("/")));
        editTextModificaUnitaMisuraCostoAcquistoProdotto.append(costoAcqusitoProdotto.substring(costoAcqusitoProdotto.lastIndexOf("/")+1));


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
                Controller.getInstance().mostraAlertErrore(getContext(),"Prodotto modificato", "Informazioni del prodotto modificate correttamente!");
                dialogModificaProdotto.dismiss();
            }
        });

        dialogModificaProdotto = builderDialogModificaProdotto.create();
        dialogModificaProdotto.show();
    }


}