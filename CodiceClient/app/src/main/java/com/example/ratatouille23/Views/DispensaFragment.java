package com.example.ratatouille23.Views;

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

import com.example.ratatouille23.Models.Avviso;
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
        return inflater.inflate(R.layout.fragment_dispensa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewDispensa);
        riempiDispensa();
        prodottoAdapter = new ProdottoRecyclerViewAdapter(getContext(),dispensa,this);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(prodottoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        prodottoAdapter.notifyDataSetChanged();

    }

    private void riempiDispensa(){
        dispensa = new ArrayList<>();

        String[] nomeProdotto = getResources().getStringArray(R.array.nome_prodotto);
        String[] descrizoneProdotto = getResources().getStringArray(R.array.descrizione_prodotto);
        String[] unitaProdotto = getResources().getStringArray(R.array.unita_prodotto);
        String[] costoAcquistoProdotto = getResources().getStringArray(R.array.costoacq_prodotto);
        String[] quantitaProdotto = getResources().getStringArray(R.array.quantita_prodotto);


        for(int i=0; i<nomeProdotto.length; i++){
            Prodotto prodotto = new Prodotto(nomeProdotto[i],descrizoneProdotto[i],unitaProdotto[i],costoAcquistoProdotto[i],Double.parseDouble(quantitaProdotto[i]));
            dispensa.add(prodotto);
        }
    }

    @Override
    public void onProdottoClicked(int posizioneProdotto) {
        Log.println(Log.INFO, "ciao","ciao msg");

    }
}