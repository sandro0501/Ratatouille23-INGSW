package com.example.ratatouille23.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.R;

import java.util.ArrayList;

public class SezioneMenuRecyclerViewAdapter extends RecyclerView.Adapter<SezioneMenuRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni;

    private Context context;
    private ArrayList<SezioneMenu> listaSezioni;
    private ElementoMenuRecyclerViewAdapter elementoMenuAdapter;

    private MyViewHolder holder;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.editTextTitoloSezione.setText(listaSezioni.get(position).getTitolo());
        elementoMenuAdapter = new ElementoMenuRecyclerViewAdapter(context, listaSezioni.get(position).getAppartenente(), (RecyclerViewElementoMenuInterface) recyclerViewInterfaceSezioni);
        holder.recyclerViewElementiMenu.setAdapter(elementoMenuAdapter);
        holder.recyclerViewElementiMenu.setLayoutManager(new LinearLayoutManager(((MenuFragment)(recyclerViewInterfaceSezioni)).getContext()));
        elementoMenuAdapter.notifyDataSetChanged();
        holder.titoloCorrente = holder.editTextTitoloSezione.getText().toString();

        this.holder = holder;

        if (position == listaSezioni.size()-1) holder.viewSezioneMenu.setBackgroundResource(R.drawable.ultima_sezione_menu_bg);
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
        private String titoloCorrente;

        public MyViewHolder(@NonNull View itemView, RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni) {
            super(itemView);

            editTextTitoloSezione = itemView.findViewById(R.id.textViewTitoloPrincipaleElementoMenuCard);
            iconaMatitaModificaSezione = itemView.findViewById(R.id.iconaModificaTitoloSezione);
            recyclerViewElementiMenu = itemView.findViewById(R.id.recyclerViewElementiMenu);
            viewSezioneMenu = itemView.findViewById(R.id.cardViewSezione);

            iconaMatitaModificaSezione.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editTextTitoloSezione.isEnabled()) {
                        iconaMatitaModificaSezione.setImageResource(R.drawable.icona_matita);
                        editTextTitoloSezione.setEnabled(false);
                        titoloCorrente = editTextTitoloSezione.getText().toString();
                        editTextTitoloSezione.setText(titoloCorrente);
                    }
                    else {
                        iconaMatitaModificaSezione.setImageResource(R.drawable.icona_spunta_modifica);
                        editTextTitoloSezione.setEnabled(true);
                        editTextTitoloSezione.requestFocus();
                        editTextTitoloSezione.setSelection(editTextTitoloSezione.getText().length());
                    }
                }
            });

        }
    }

    public void ripristinaTitoli() {
        holder.iconaMatitaModificaSezione.setImageResource(R.drawable.icona_matita);
        holder.editTextTitoloSezione.setEnabled(false);
        holder.editTextTitoloSezione.setText(holder.titoloCorrente);
    }

    public void impostaTitoli() {
        holder.titoloCorrente = holder.editTextTitoloSezione.getText().toString();
    }

}
