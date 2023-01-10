package com.example.ratatouille23.Views;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private AlertDialog.Builder builderDialogAggiungiProdotto;
    private Dialog dialogAggiungiProdotto;
    private AlertDialog.Builder builderDialogVisualizzaProdotto;
    private Dialog dialogVisualizzaProdotto;
    private EditText editTextNomeProdotto;
    private EditText editTextDescrizioneProdotto;
    private EditText editTextQuantitaProdotto;
    private EditText editTextUnitaMisuraProdotto;
    private EditText editTextCostoAcquistoProdotto;
    private EditText editTextSogliaProdotto;
    private TextView textViewVisualizzazioneNomeProdotto;
    private TextView textViewVisualizzazioneDescrizioneProdotto;
    private TextView textViewVisualizzazioneQuantitaProdotto;
    private TextView textViewVisualizzazioneUnitaMisuraProdotto;
    private TextView textViewVisualizzazioneCostoAcquistoProdotto;
    private TextView textViewVisualizzazioneSogliaProdotto;
    private Button bottoneConfermaAggiungiProdotto;
    private Button bottoneAnnullaAggiungiProdotto;
    private Button bottoneAnnullaVisualizzazioneProdotto;



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
    public void onProdottoClicked(int posizioneProdotto) {
        Log.println(Log.INFO, "ciao","ciao msg");
        final View viewVisualizzaProdotto = getLayoutInflater().inflate(R.layout.layout_modifica_prodotto_dialog, null);

        builderDialogVisualizzaProdotto = new AlertDialog.Builder(getContext());
        builderDialogVisualizzaProdotto.setView(viewVisualizzaProdotto);
        builderDialogVisualizzaProdotto.setCancelable(true);

        textViewVisualizzazioneNomeProdotto = (TextView) viewVisualizzaProdotto.findViewById(R.id.textViewNomeProdottoLabelVisualizzazione);
        textViewVisualizzazioneDescrizioneProdotto = (TextView) viewVisualizzaProdotto.findViewById(R.id.textViewDescrizioneProdottoLabelVisualizzazione);
        textViewVisualizzazioneUnitaMisuraProdotto = (TextView) viewVisualizzaProdotto.findViewById(R.id.textViewUnitaProdottoLabelVisualizzazione);
        textViewVisualizzazioneCostoAcquistoProdotto = (TextView) viewVisualizzaProdotto.findViewById(R.id.textViewCostoAcquistoProdottoLabelVisualizzazione);
        textViewVisualizzazioneQuantitaProdotto = (TextView) viewVisualizzaProdotto.findViewById(R.id.textViewQuantitaProdottoProdottoLabelVisualizzazione);
        textViewVisualizzazioneSogliaProdotto = (TextView) viewVisualizzaProdotto.findViewById(R.id.textViewSogliaLimiteProdottoLabelVisualizzazione);

        textViewVisualizzazioneNomeProdotto.append(" "+dispensa.get(posizioneProdotto).getNome());
        textViewVisualizzazioneDescrizioneProdotto.append(" "+dispensa.get(posizioneProdotto).getDescrizione());
        textViewVisualizzazioneUnitaMisuraProdotto.append(" "+dispensa.get(posizioneProdotto).getUnita());
        textViewVisualizzazioneCostoAcquistoProdotto.append(" "+dispensa.get(posizioneProdotto).getCostoAcquisto());
        textViewVisualizzazioneQuantitaProdotto.append(" "+dispensa.get(posizioneProdotto).getQuantita());
        textViewVisualizzazioneSogliaProdotto.append(" "+dispensa.get(posizioneProdotto).getSoglia());

        bottoneAnnullaVisualizzazioneProdotto = (Button) viewVisualizzaProdotto.findViewById(R.id.bottoneAnnullaVisualizzaProdotto);
        bottoneAnnullaVisualizzazioneProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogVisualizzaProdotto.dismiss();
            }
        });

        dialogVisualizzaProdotto = builderDialogVisualizzaProdotto.create();
        dialogVisualizzaProdotto.show();

    }


}