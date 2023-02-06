package com.example.ratatouille23.Views;

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
    private AvvisoRecyclerViewAdapter   avvisiVisibiliAdapter;
    private AvvisoRecyclerViewAdapter tuttiAvvisiAdapter;
    private ImageView bottoneCreazioneAvviso;
    private ImageView bottoneReloadAvvisi;
    private ImageView bottoneOcchioAvvisi;
    private Utente utenteCorrente;
    private TextView textViewNumeroAvvisi;
    private BachecaFragment context = this;
    private ArrayList<Utente> utentiCorrenti = new ArrayList<Utente>();

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
    public void setUtentiCorrenti(ArrayList<Utente> utenti)
    {
        utentiCorrenti.clear();
        utentiCorrenti.addAll(utenti);
    }

    public ArrayList<Utente> getUtentiCorrenti()
    {
        return utentiCorrenti;
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
        PresenterBacheca.getInstance().setUtentiCorrenti(utenteCorrente.getIdRistorante(), context, utenteCorrente);
        //Devi estrarre gli avvisi dell'utente
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
        bottoneReloadAvvisi = view.findViewById(R.id.imageViewIconReloadAvvisi);
        bottoneOcchioAvvisi = view.findViewById(R.id.imageViewIconViewAvvisiNascosti);

        bottoneCreazioneAvviso.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentFromBachecaToVCreazioneAvviso = new Intent(getContext(), CreazioneAvvisoActivity.class);
                intentFromBachecaToVCreazioneAvviso.putExtra("Utente",utenteCorrente);
                getContext().startActivity(intentFromBachecaToVCreazioneAvviso);

            }
        });

        bottoneReloadAvvisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PresenterBacheca.getInstance().setAvvisi(BachecaFragment.this, utenteCorrente);
            }
        });

        bottoneOcchioAvvisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(tuttiAvvisiAdapter);
                bottoneOcchioAvvisi.setVisibility(View.INVISIBLE);
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
        recyclerView.setAdapter(avvisiVisibiliAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (utenteCorrente.getRuoloUtente().equals("Addetto alla cucina") || utenteCorrente.getRuoloUtente().equals("Addetto al servizio"))
            bottoneCreazioneAvviso.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onAvvisoClicked(Bacheca avvisoCliccato) {

        visualizzaAvviso(avvisoCliccato);

    }

    @Override
    public void onOcchioAvvisoClicked(Bacheca avvisoScelto) {

        AggiornaAvvisoHandler handler = new AggiornaAvvisoHandler();
        handler.avviso = avvisoScelto.getAvvisoAssociato();
        handler.utente = utenteCorrente;
        PresenterBacheca.getInstance().nascondiAvviso(BachecaFragment.this, handler);
        Log.i("PROVA", "PROVA NASCOSTO");

    }

    private void visualizzaAvviso(Bacheca avvisoCliccato) {
        Intent intentFromBachecaToVisualizzazioneAvviso = new Intent(getContext(), VisualizzazioneAvvisoActivity.class);

        //passa i dati dal fragment all'activity
        intentFromBachecaToVisualizzazioneAvviso.putExtra("OGGETTO", avvisoCliccato.getAvvisoAssociato().getOggetto());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("AUTORE", avvisoCliccato.getAvvisoAssociato().getAutore().getNomeCompleto());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("RUOLOAUOTORE", avvisoCliccato.getAvvisoAssociato().getAutore().getRuoloUtente());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("DATACREAZIONE", avvisoCliccato.getAvvisoAssociato().getDataCreazione());
        intentFromBachecaToVisualizzazioneAvviso.putExtra("CORPO", avvisoCliccato.getAvvisoAssociato().getCorpo());

        if(avvisoCliccato.isVisualizzato())
        {
            getContext().startActivity(intentFromBachecaToVisualizzazioneAvviso);
        }
        else
        {
            avvisoCliccato.setVisualizzato(true);
            AggiornaAvvisoHandler handle = new AggiornaAvvisoHandler();
            handle.utente = utenteCorrente;
            handle.avviso = avvisoCliccato.getAvvisoAssociato();
            PresenterBacheca.getInstance().visualizzaAvviso(this,intentFromBachecaToVisualizzazioneAvviso,handle);
        }
    }

    public void setAvvisiUtente(ArrayList<Avviso> avvisiUtenteNuovi, ArrayList<Avviso> avvisiUtenteLetti, ArrayList<Avviso> avvisiUtenteNascosti)
    {
        avvisiVisibili.clear();
        tuttiAvvisi.clear();

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

        Collections.reverse(avvisiVisibili);
        Collections.reverse(tuttiAvvisi);

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