package com.example.ratatouille23.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;

import com.example.ratatouille23.Models.Avviso;
import java.util.ArrayList;


public class BachecaFragment extends Fragment implements RecyclerViewAvvisoInterface {

    private ArrayList<Avviso> avvisiUtente;
    private RecyclerView recyclerView;
    private AvvisoRecyclerViewAdapter avvisoAdapter;
    private ImageView bottoneCreazioneAvviso;

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

        View view = inflater.inflate(R.layout.fragment_bacheca, container, false);

        bottoneCreazioneAvviso = (ImageView) view.findViewById(R.id.imageViewIconAddAvviso);
        bottoneCreazioneAvviso.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentFromBachecaToVCreazioneAvviso = new Intent(getContext(), CreazioneAvvisoActivity.class);
                getContext().startActivity(intentFromBachecaToVCreazioneAvviso);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerViewAvvisi);
        setUpAvvisi();
        avvisoAdapter = new AvvisoRecyclerViewAdapter(getContext(),avvisiUtente,this);
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


    @Override
    public void onAvvisoClicked(int posizioneAvviso) {

        //cambiaAspettoAvvisoInVisiualizzato();
        visualizzaAvviso(posizioneAvviso);

    }

    private void visualizzaAvviso(int posizioneAvviso) {
        Intent intentFromBachecaToVisualizzazioneAvviso = new Intent(getContext(), VisualizzazioneAvvisoActivity.class);

        //passa i dati dal fragment all'activity
        intentFromBachecaToVisualizzazioneAvviso.putExtra("OGGETTO", avvisiUtente.get(posizioneAvviso).getOggetto());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("AUTORE", avvisiUtente.get(posizioneAvviso).getAutore());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("RUOLOAUOTORE", avvisiUtente.get(posizioneAvviso).getAutore());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("DATACREAZIONE", avvisiUtente.get(posizioneAvviso).getDataCreazione());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("CORPO", avvisiUtente.get(posizioneAvviso).getCorpo());

        getContext().startActivity(intentFromBachecaToVisualizzazioneAvviso);
    }

    private void cambiaAspettoAvvisoInVisiualizzato() {
        ImageView imageViewAvvisi, imageViewNascondiAvviso;
        TextView autoreAvviso, oggettoAvviso, corpoAvviso, dataAvviso, ruoloAutoreAvviso;

        imageViewAvvisi = getView().findViewById(R.id.imageViewNotifica);
        autoreAvviso = getView().findViewById(R.id.textViewNomeDipendenteCard);
        oggettoAvviso = getView().findViewById(R.id.textViewEmailDipendenteCard);
        corpoAvviso = getView().findViewById(R.id.textViewAnteprimaAvviso);
        dataAvviso = getView().findViewById(R.id.textViewDataCreazioneAvviso);
        ruoloAutoreAvviso = getView().findViewById(R.id.textViewRuoloDipendenteCard);
        imageViewNascondiAvviso = getView().findViewById(R.id.imageViewNascondiAvviso);

        //cambia aspetto testo
        Typeface verdanaFace = ResourcesCompat.getFont(getContext(), R.font.verdana);

        autoreAvviso.setTypeface(verdanaFace);
        ruoloAutoreAvviso.setTypeface(verdanaFace, Typeface.ITALIC);
        oggettoAvviso.setTypeface(verdanaFace);
        dataAvviso.setTypeface(verdanaFace);

        autoreAvviso.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        ruoloAutoreAvviso.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        oggettoAvviso.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        corpoAvviso.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        dataAvviso.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

        autoreAvviso.setTextColor(Color.parseColor("#5F5959"));
        ruoloAutoreAvviso.setTextColor(Color.parseColor("#5F5959"));
        oggettoAvviso.setTextColor(Color.parseColor("#5F5959"));
        corpoAvviso.setTextColor(Color.parseColor("#5F5959"));
        dataAvviso.setTextColor(Color.parseColor("#5F5959"));

        //cambia icona notifica
        imageViewAvvisi.setImageResource(R.drawable.icon_avviso_visto);
        imageViewNascondiAvviso.setVisibility(View.VISIBLE);
    }



}