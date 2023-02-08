package com.example.ratatouille23.Views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Presenters.PresenterMenu;
import com.example.ratatouille23.R;

import java.util.ArrayList;
import java.util.Collections;

public class SezioneMenuRecyclerViewAdapter extends RecyclerView.Adapter<SezioneMenuRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni;

    private Context context;
    private ArrayList<SezioneMenu> listaSezioni;
    private ElementoMenuRecyclerViewAdapter elementoMenuAdapter;
    private Utente utenteCorrente;
    private AlertDialog.Builder builderDialogEliminaSezione;
    private Dialog dialogEliminaSezione;
    private TextView textViewEliminaSezione;
    private Button bottoneConfermaEliminazioneSezione;
    private Button bottoneAnnullaEliminazioneSezione;

    private MyViewHolder holder;

    public ElementoMenuRecyclerViewAdapter getElementoMenuAdapter() {
        return elementoMenuAdapter;
    }

    public void setElementoMenuAdapter(ElementoMenuRecyclerViewAdapter elementoMenuAdapter) {
        this.elementoMenuAdapter = elementoMenuAdapter;
    }

    public SezioneMenuRecyclerViewAdapter(Context context, ArrayList<SezioneMenu> sezioni, RecyclerViewSezioneMenuInterface recyclerViewInterfaceSezioni, Utente utenteCorrente){
        this.context = context;
        this.listaSezioni = sezioni;
        this.recyclerViewInterfaceSezioni = recyclerViewInterfaceSezioni;
        this.utenteCorrente = utenteCorrente;
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
        elementoMenuAdapter = new ElementoMenuRecyclerViewAdapter(context, holder.sezioneCorrente.getAppartenente(), (RecyclerViewElementoMenuInterface) recyclerViewInterfaceSezioni, utenteCorrente);
        holder.recyclerViewElementiMenu.setAdapter(elementoMenuAdapter);
        holder.recyclerViewElementiMenu.setLayoutManager(new LinearLayoutManager(((MenuFragment)(recyclerViewInterfaceSezioni)).getContext()));
        elementoMenuAdapter.notifyDataSetChanged();
        holder.titoloCorrente = holder.editTextTitoloSezione.getText().toString();

        this.holder = holder;

        if (utenteCorrente.getRuoloUtente().equals("Addetto alla cucina") || utenteCorrente.getRuoloUtente().equals("Addetto al servizio")) {
            holder.iconaMatitaModificaSezione.setVisibility(View.INVISIBLE);
            holder.iconaDragNDrop.setVisibility(View.INVISIBLE);
        }
        else {
            holder.iconaMatitaModificaSezione.setVisibility(View.VISIBLE);
            holder.iconaDragNDrop.setVisibility(View.VISIBLE);
        }

        impostaGraficamenteModalitaModifica(holder, holder.sezioneCorrente);

        holder.iconaMatitaModificaSezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((MenuFragment)recyclerViewInterfaceSezioni).isModalitaEliminazione()) {
                    if(holder.sezioneCorrente.isInModifica())
                    {
                        holder.sezioneCorrente.setTitolo(holder.editTextTitoloSezione.getText().toString());
                        PresenterMenu.getInstance().modificaSezione(((MenuFragment)recyclerViewInterfaceSezioni), holder.sezioneCorrente);
                    }
                    holder.sezioneCorrente.setInModifica(!holder.sezioneCorrente.isInModifica());
                    impostaGraficamenteModalitaModifica(holder, holder.sezioneCorrente);
                }
            }
        });

        holder.iconaEliminaSezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewEliminaSezione = ((MenuFragment) recyclerViewInterfaceSezioni).getLayoutInflater().inflate(R.layout.layout_elimina_prodotto_dialog, null);
                builderDialogEliminaSezione = new AlertDialog.Builder(context);
                builderDialogEliminaSezione.setView(viewEliminaSezione);
                builderDialogEliminaSezione.setCancelable(true);
                textViewEliminaSezione = (TextView) viewEliminaSezione.findViewById(R.id.textViewEliminaProdottoDescrizioneDialog);
                textViewEliminaSezione.setText("Si è sicuri di voler eliminare questa sezione del menù?\nVerranno eliminati tutti gli elementi associati");
                bottoneAnnullaEliminazioneSezione = (Button) viewEliminaSezione.findViewById(R.id.bottoneAnnullaEliminaProdotto);
                bottoneConfermaEliminazioneSezione = (Button) viewEliminaSezione.findViewById(R.id.bottoneEliminaProdotto);

                bottoneAnnullaEliminazioneSezione.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEliminaSezione.dismiss();
                    }
                });

                bottoneConfermaEliminazioneSezione.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PresenterMenu.getInstance().rimuoviSezione(((MenuFragment)recyclerViewInterfaceSezioni), listaSezioni.get(holder.getAdapterPosition()));
                        dialogEliminaSezione.dismiss();
                    }
                });

                dialogEliminaSezione = builderDialogEliminaSezione.create();
                dialogEliminaSezione.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                dialogEliminaSezione.show();
            }
        });

        holder.iconaDragNDrop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (!((MenuFragment)recyclerViewInterfaceSezioni).isModalitaEliminazione()) {
                    if (utenteCorrente.getRuoloUtente().equals("Amministratore") || utenteCorrente.getRuoloUtente().equals("Supervisore")) {
                        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                            ((MenuFragment) recyclerViewInterfaceSezioni).getItemTouchHelper().startDrag(holder);
                        }
                    }
                }
                return false;
            }

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallbackElementi);
        if (utenteCorrente.getRuoloUtente().equals("Amministratore") || utenteCorrente.getRuoloUtente().equals("Supervisore"))
            itemTouchHelper.attachToRecyclerView(holder.recyclerViewElementiMenu);
        else
            itemTouchHelper.attachToRecyclerView(null);

    }

    private void impostaGraficamenteModalitaModifica(MyViewHolder holder, SezioneMenu sezioneMenu) {
        if (!sezioneMenu.isInModifica()) {
            holder.iconaMatitaModificaSezione.setImageResource(R.drawable.icona_matita);
            holder.editTextTitoloSezione.setEnabled(false);
            holder.titoloCorrente = holder.editTextTitoloSezione.getText().toString();
            holder.editTextTitoloSezione.setText(holder.titoloCorrente);
            holder.iconaEliminaSezione.setVisibility(View.INVISIBLE);
        }
        else {
            holder.iconaMatitaModificaSezione.setImageResource(R.drawable.icona_spunta_modifica);
            holder.editTextTitoloSezione.setEnabled(true);
            holder.editTextTitoloSezione.requestFocus();
            holder.editTextTitoloSezione.setSelection(holder.editTextTitoloSezione.getText().length());
            holder.iconaEliminaSezione.setVisibility(View.VISIBLE);
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
