package com.example.ratatouille23.Views;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.listaAllergeni;
import com.example.ratatouille23.R;

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

    private RecyclerView recyclerViewMenu;
    private SezioneMenuRecyclerViewAdapter adapterSezioni;
    private ArrayList<SezioneMenu> listaSezioni = new ArrayList<>();

    private ItemTouchHelper itemTouchHelper;

    public MenuFragment() {
        // Required empty public constructor
    }

    public SezioneMenuRecyclerViewAdapter getAdapterSezioni() {
        return adapterSezioni;
    }

    public void setAdapterSezioni(SezioneMenuRecyclerViewAdapter adapterSezioni) {
        this.adapterSezioni = adapterSezioni;
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

        spinnerSceltaAggiunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerAdapter.getItem(i).equals("Aggiungi una sezione")) {
                    SezioneMenu nuovaSezione = new SezioneMenu("Nuova sezione", listaSezioni.size());
                    listaSezioni.add(nuovaSezione);
                    adapterSezioni.notifyDataSetChanged();
                    spinnerSceltaAggiunta.setSelection(2);
                } else {
                    spinnerSceltaAggiunta.setSelection(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

         itemTouchHelper = new ItemTouchHelper(simpleCallbackSezioni);

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
            Log.i("draggable", ((Boolean)holderCorrente.isDraggable()).toString());
            return holderCorrente.isDraggable();
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

}