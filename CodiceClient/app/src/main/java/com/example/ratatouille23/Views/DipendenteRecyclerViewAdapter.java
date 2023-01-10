package com.example.ratatouille23.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.R;

import java.util.ArrayList;

public class DipendenteRecyclerViewAdapter extends RecyclerView.Adapter<DipendenteRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewDipendenteInterface recyclerViewInterfaceDipendente;

    private Context context;
    private ArrayList<Utente> dipendenti;

    public DipendenteRecyclerViewAdapter(Context context, ArrayList<Utente> dipendenti, RecyclerViewDipendenteInterface recyclerViewInterfaceDipendente){
        this.context = context;
        this.dipendenti = dipendenti;
        this.recyclerViewInterfaceDipendente = recyclerViewInterfaceDipendente;
    }

    @NonNull
    @Override
    public DipendenteRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //fornisce lo stile grafico alle diverse "cardView"
        //LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_dipendente, parent, false);

        return new DipendenteRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceDipendente);
    }

    @Override
    public void onBindViewHolder(@NonNull DipendenteRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assegna valori alle "card" che abbiamo creato in dipendenza anche della loro posizione nella lista recycler view

        holder.textViewNome.setText((dipendenti.get(position).getNomeCompleto()));
        holder.textViewEmail.setText(dipendenti.get(position).getEmail());
        holder.textViewRuolo.setText(dipendenti.get(position).getRuoloUtente());

    }

    @Override
    public int getItemCount() {
        //serve al recyclerView per conoscere quante "card" devono essere mostrate in totale sul display
        return dipendenti.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //una specie di metodo onCreate che prende gli elementi di cui sono formate le card dal file xml
        private ImageView imageViewAvvisi;
        private TextView textViewNome, textViewEmail, textViewRuolo;

        public MyViewHolder(@NonNull View itemView, RecyclerViewDipendenteInterface recyclerViewInterfaceDipendente) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.textViewNomeDipendenteCard);
            textViewEmail = itemView.findViewById(R.id.textViewEmailDipendenteCard);
            textViewRuolo = itemView.findViewById(R.id.textViewRuoloDipendenteCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterfaceDipendente != null) {
                        int posizioneDipendente = getAdapterPosition();
                        if(posizioneDipendente!=RecyclerView.NO_POSITION){
                            recyclerViewInterfaceDipendente.onDipendenteClicked(posizioneDipendente);
                        }

                    }
                }
            });
        }
    }
}
