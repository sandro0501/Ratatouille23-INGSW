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

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;

import com.example.ratatouille23.Models.Avviso;
import java.util.ArrayList;

public class BachecaFragment extends Fragment {

    private ArrayList<Avviso> avvisiUtente;
    private RecyclerView recyclerView;
    private AvvisoRecyclerViewAdapter avvisoAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BachecaFragment() {
        // Required empty public constructor
    }

    public static BachecaFragment newInstance(String param1, String param2) {
        BachecaFragment fragment = new BachecaFragment();
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
    public void onStart() {
        super.onStart();
        Controller.setBachecaAttiva(true);
    }

    @Override
    public void onStop () {
        Controller.setBachecaAttiva(false);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bacheca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewAvvisi);
        setUpAvvisi();
        avvisoAdapter = new AvvisoRecyclerViewAdapter(getContext(),avvisiUtente);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(avvisoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        avvisoAdapter.notifyDataSetChanged();

    }

    private void setUpAvvisi(){
        avvisiUtente = new ArrayList<>();

        String[] autoriAvvisi = getResources().getStringArray(R.array.autore_avviso_xml);
        String[] ruoliAutoriAvvisi = getResources().getStringArray(R.array.ruolo_autore_avviso_xml);
        String[] oggettiAvvisi = getResources().getStringArray(R.array.oggetto_avviso_xml);
        String[] corpoAvvisi = getResources().getStringArray(R.array.corpo_avviso_xml);
        String[] dateAvvisi = getResources().getStringArray(R.array.data_avviso_xml);
        int iconaAvvisoDaVedere = R.drawable.icon_avviso_da_vedere;

        for(int i=0; i<oggettiAvvisi.length; i++){
            Avviso avviso = new Avviso(oggettiAvvisi[i],corpoAvvisi[i],dateAvvisi[i],autoriAvvisi[i],iconaAvvisoDaVedere,ruoliAutoriAvvisi[i]);
            avvisiUtente.add(avviso);
        }
    }


}