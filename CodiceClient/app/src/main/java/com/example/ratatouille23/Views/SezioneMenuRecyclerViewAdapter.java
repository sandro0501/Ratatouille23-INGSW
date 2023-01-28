package com.example.ratatouille23.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.R;

import java.util.ArrayList;
import java.util.Collections;

public class SezioneMenuRecyclerViewAdapter extends RecyclerView.Adapter<SezioneMenuRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni;

    private Context context;
    private ArrayList<SezioneMenu> listaSezioni;
    private ElementoMenuRecyclerViewAdapter elementoMenuAdapter;

    private MyViewHolder holder;

    public ElementoMenuRecyclerViewAdapter getElementoMenuAdapter() {
        return elementoMenuAdapter;
    }

    public void setElementoMenuAdapter(ElementoMenuRecyclerViewAdapter elementoMenuAdapter) {
        this.elementoMenuAdapter = elementoMenuAdapter;
    }

    public SezioneMenuRecyclerViewAdapter(Context context, ArrayList<SezioneMenu> sezioni, RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni){
        this.context = context;
        this.listaSezioni = sezioni;
        this.recyclerViewInterfaceSezioni = recyclerViewInterfaceSezioni;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_sezione_menu, parent, false);
        return new SezioneMenuRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceSezioni);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sezioneCorrente = listaSezioni.get(position);
        holder.editTextTitoloSezione.setText(holder.sezioneCorrente.getTitolo());
        elementoMenuAdapter = new ElementoMenuRecyclerViewAdapter(context, holder.sezioneCorrente.getAppartenente(), (RecyclerViewElementoMenuInterface) recyclerViewInterfaceSezioni);
        holder.recyclerViewElementiMenu.setAdapter(elementoMenuAdapter);
        holder.recyclerViewElementiMenu.setLayoutManager(new LinearLayoutManager(((MenuFragment)(recyclerViewInterfaceSezioni)).getContext()));
        elementoMenuAdapter.notifyDataSetChanged();
        holder.titoloCorrente = holder.editTextTitoloSezione.getText().toString();

        this.holder = holder;

        impostaGraficamenteModalitaModifica(holder, holder.sezioneCorrente);

        holder.iconaMatitaModificaSezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((MenuFragment)recyclerViewInterfaceSezioni).isModalitaEliminazione()) {
                    holder.sezioneCorrente.setInModifica(!holder.sezioneCorrente.isInModifica());
                    impostaGraficamenteModalitaModifica(holder, holder.sezioneCorrente);
                }
            }
        });

        holder.iconaEliminaSezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaSezioni.remove(holder.getAdapterPosition());
                ((MenuFragment)recyclerViewInterfaceSezioni).getAdapterSezioni().notifyItemRemoved(holder.getAdapterPosition());
            }
        });

        holder.iconaDragNDrop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (!((MenuFragment)recyclerViewInterfaceSezioni).isModalitaEliminazione()) {
                    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        ((MenuFragment) recyclerViewInterfaceSezioni).getItemTouchHelper().startDrag(holder);
                    }
                }
                return false;
            }

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallbackElementi);
        itemTouchHelper.attachToRecyclerView(holder.recyclerViewElementiMenu);

    }

    private void impostaGraficamenteModalitaModifica(MyViewHolder holder, SezioneMenu sezioneMenu) {
        if (!sezioneMenu.isInModifica()) {
            holder.iconaMatitaModificaSezione.setImageResource(R.drawable.icona_matita);
            holder.editTextTitoloSezione.setEnabled(false);
            holder.titoloCorrente = holder.editTextTitoloSezione.getText().toString();
            holder.editTextTitoloSezione.setText(holder.titoloCorrente);
            holder.iconaEliminaSezione.setVisibility(View.INVISIBLE);
            sezioneMenu.setInModifica(false);
        }
        else {
            holder.iconaMatitaModificaSezione.setImageResource(R.drawable.icona_spunta_modifica);
            holder.editTextTitoloSezione.setEnabled(true);
            holder.editTextTitoloSezione.requestFocus();
            holder.editTextTitoloSezione.setSelection(holder.editTextTitoloSezione.getText().length());
            holder.iconaEliminaSezione.setVisibility(View.VISIBLE);
            sezioneMenu.setInModifica(true);
        }
    }

    @Override
    public int getItemCount() {
        return listaSezioni.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //una specie di metodo onCreate che prende gli elementi di cui sono formate le card dal file xml

        private CardView viewSezioneMenu;
        private EditText editTextTitoloSezione;
        private ImageView iconaMatitaModificaSezione;
        private RecyclerView recyclerViewElementiMenu;
        private ImageView iconaEliminaSezione;
        private String titoloCorrente;
        private SezioneMenu sezioneCorrente;
        private ImageView iconaDragNDrop;

        public MyViewHolder(@NonNull View itemView, RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni) {
            super(itemView);

            editTextTitoloSezione = itemView.findViewById(R.id.textViewTitoloPrincipaleElementoMenuCard);
            iconaMatitaModificaSezione = itemView.findViewById(R.id.iconaModificaTitoloSezione);
            recyclerViewElementiMenu = itemView.findViewById(R.id.recyclerViewElementiMenu);
            viewSezioneMenu = itemView.findViewById(R.id.cardViewSezione);
            iconaEliminaSezione = itemView.findViewById(R.id.iconaEliminaSezione);
            iconaDragNDrop = itemView.findViewById(R.id.iconaDragNDropSezione);

            iconaEliminaSezione.setVisibility(View.INVISIBLE);

        }

    }

    ItemTouchHelper.SimpleCallback simpleCallbackElementi = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int posizioneIniziale = viewHolder.getAdapterPosition();
            int posizioneFinale = target.getAdapterPosition();

            SezioneMenu sezioneCorrente = ((ElementoMenuRecyclerViewAdapter.MyViewHolder)viewHolder).sezioneCorrente;

            ArrayList<Elemento> listaElementiSezioneCorrente = sezioneCorrente.getAppartenente();

            Collections.swap(listaElementiSezioneCorrente, posizioneIniziale, posizioneFinale);

            listaElementiSezioneCorrente.get(posizioneIniziale).setPosizione(posizioneIniziale);
            listaElementiSezioneCorrente.get(posizioneFinale).setPosizione(posizioneFinale);

            sezioneCorrente.setAppartenente(listaElementiSezioneCorrente);

            recyclerView.getAdapter().notifyDataSetChanged();

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };


}
