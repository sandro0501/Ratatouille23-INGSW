package com.example.ratatouille23.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterBacheca;
import com.example.ratatouille23.R;

import com.example.ratatouille23.Models.Avviso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class BachecaFragment extends Fragment implements RecyclerViewAvvisoInterface {

    private ArrayList<Bacheca> avvisiVisibili = new ArrayList<>();
    private ArrayList<Bacheca> tuttiAvvisi = new ArrayList<>();
    private RecyclerView recyclerView;
    private AvvisoRecyclerViewAdapter avvisiVisibiliAdapter;
    private AvvisoRecyclerViewAdapter tuttiAvvisiAdapter;
    private ImageView bottoneCreazioneAvviso;
    private Utente utenteCorrente;
    private TextView textViewNumeroAvvisi;

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
        //Devi estrarre gli avvisi dell'utente
        PresenterBacheca.getInstance().setAvvisi(this, utenteCorrente.getIdUtente());
        PresenterBacheca.getInstance().setBachecaAttiva(true);
        setNumeroAvvisiDaLeggere();
    }

    @Override
    public void onStop () {
        PresenterBacheca.getInstance().setBachecaAttiva(false);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bacheca, container, false);
        textViewNumeroAvvisi = view.findViewById(R.id.textViewNumeroAvvisi);
        bottoneCreazioneAvviso = (ImageView) view.findViewById(R.id.imageViewIconAddAvviso);
        bottoneCreazioneAvviso.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentFromBachecaToVCreazioneAvviso = new Intent(getContext(), CreazioneAvvisoActivity.class);
                startActivity(intentFromBachecaToVCreazioneAvviso);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        utenteCorrente = (Utente)getActivity().getIntent().getSerializableExtra("Utente");
        recyclerView = view.findViewById(R.id.recyclerViewAvvisi);
        avvisiVisibiliAdapter = new AvvisoRecyclerViewAdapter(getContext(), avvisiVisibili,this);
        tuttiAvvisiAdapter = new AvvisoRecyclerViewAdapter(getContext(), tuttiAvvisi, this);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(avvisiVisibiliAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        avvisiVisibiliAdapter.notifyDataSetChanged();
        tuttiAvvisiAdapter.notifyDataSetChanged();


    }

    @Override
    public void onAvvisoClicked(int posizioneAvviso) {

        //cambiaAspettoAvvisoInVisiualizzato();
        visualizzaAvviso(posizioneAvviso);

    }

    private void visualizzaAvviso(int posizioneAvviso) {
        Intent intentFromBachecaToVisualizzazioneAvviso = new Intent(getContext(), VisualizzazioneAvvisoActivity.class);

        //passa i dati dal fragment all'activity
        intentFromBachecaToVisualizzazioneAvviso.putExtra("OGGETTO", avvisiVisibili.get(posizioneAvviso).getAvvisoAssociato().getOggetto());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("AUTORE", avvisiVisibili.get(posizioneAvviso).getAvvisoAssociato().getAutore().getNomeCompleto());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("RUOLOAUOTORE", avvisiVisibili.get(posizioneAvviso).getAvvisoAssociato().getAutore().getRuoloUtente());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("DATACREAZIONE", avvisiVisibili.get(posizioneAvviso).getAvvisoAssociato().getDataCreazione());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("CORPO", avvisiVisibili.get(posizioneAvviso).getAvvisoAssociato().getCorpo());

        if(avvisiVisibili.get(posizioneAvviso).isVisualizzato())
        {
            getContext().startActivity(intentFromBachecaToVisualizzazioneAvviso);
        }
        else
        {
            avvisiVisibili.get(posizioneAvviso).setVisualizzato(true);
            AggiornaAvvisoHandler handle = new AggiornaAvvisoHandler();
            handle.utente = utenteCorrente;
            handle.avviso = avvisiVisibili.get(posizioneAvviso).getAvvisoAssociato();
            PresenterBacheca.getInstance().visualizzaAvviso(this,intentFromBachecaToVisualizzazioneAvviso,handle);
        }
    }

    private void cambiaAspettoAvvisoInVisiualizzato() {
        ImageView imageViewAvvisi, imageViewNascondiAvviso;
        TextView autoreAvviso, oggettoAvviso, corpoAvviso, dataAvviso, ruoloAutoreAvviso;

        imageViewAvvisi = getView().findViewById(R.id.imageViewImmagineElementoMenuCard);
        autoreAvviso = getView().findViewById(R.id.textViewTitoloPrincipaleElementoMenuCard);
        oggettoAvviso = getView().findViewById(R.id.textViewDescrizionePrincipaleElementoMenuCard);
        corpoAvviso = getView().findViewById(R.id.textViewDescrizioneSecondariaElementoMenuCard);
        dataAvviso = getView().findViewById(R.id.textViewCostoElementoMenuCard);
        ruoloAutoreAvviso = getView().findViewById(R.id.textViewTitoloSecondarioElementoMenuCard);
        imageViewNascondiAvviso = getView().findViewById(R.id.imageViewVediIngredientiElementoMenuCard);

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

    public void setAvvisiUtente(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiUtenteNascosti)
    {
        avvisiVisibili.clear();

        for (Avviso avviso : avvisiUtenteNuovi){
            avvisiVisibili.add(new Bacheca(avviso, true, false));
        }

        for (Avviso avviso : avvisiUtenteLetti){
            avvisiVisibili.add(new Bacheca(avviso, true, true));
        }

        tuttiAvvisi.addAll(avvisiVisibili);

        for (Avviso avviso : avvisiUtenteNascosti){
            tuttiAvvisi.add(new Bacheca(avviso, false, true));
        }

        Collections.sort(avvisiVisibili,new Comparator<Bacheca>() {
            public int compare (Bacheca a1, Bacheca a2)
            {
                return a1.getAvvisoAssociato().getDataCreazione().compareTo(a2.getAvvisoAssociato().getDataCreazione());
            }
        });

        Collections.sort(tuttiAvvisi,new Comparator<Bacheca>() {
            public int compare (Bacheca a1, Bacheca a2)
            {
                return a1.getAvvisoAssociato().getDataCreazione().compareTo(a2.getAvvisoAssociato().getDataCreazione());
            }
        });

        avvisiVisibiliAdapter.notifyDataSetChanged();
        tuttiAvvisiAdapter.notifyDataSetChanged();
    }


    private void setNumeroAvvisiDaLeggere() {
        int numeroAvvisi = 0;
        for (Bacheca avviso : avvisiVisibili) {
            if (!avviso.isVisualizzato()) numeroAvvisi++;
        }
        if (numeroAvvisi == 0) {
            textViewNumeroAvvisi.setVisibility(View.INVISIBLE);
        }
        else {
            textViewNumeroAvvisi.setVisibility(View.VISIBLE);
            textViewNumeroAvvisi.setText(((Integer)numeroAvvisi).toString());
        }
    }

}