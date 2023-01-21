package com.example.ratatouille23.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.R;

import java.util.ArrayList;

public class AggiuntaIngredientiRecyclerViewAdapter extends RecyclerView.Adapter<AggiuntaIngredientiRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewAggiuntaIngredientiElementoInterface recyclerViewInterfaceIngredienteElemento;
    private Context context;
    private ArrayList<Prodotto> dispensa;

    public AggiuntaIngredientiRecyclerViewAdapter(Context context, ArrayList<Prodotto> dispensa, RecyclerViewAggiuntaIngredientiElementoInterface recyclerViewInterfaceIngredienteElemento){
        this.context = context;
        this.dispensa = dispensa;
        this.recyclerViewInterfaceIngredienteElemento = recyclerViewInterfaceIngredienteElemento;
    }

    public void setDispensaFiltrata(ArrayList<Prodotto> dispensaFiltrata){
        this.dispensa = dispensaFiltrata;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AggiuntaIngredientiRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_prodotto, parent, false);
        return new AggiuntaIngredientiRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceIngredienteElemento);
    }

    @Override
    public void onBindViewHolder(@NonNull AggiuntaIngredientiRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.nomeProdotto.setText(dispensa.get(position).getNome());
        holder.nomeProdotto.setTextColor(Color.BLACK);

        holder.descrizioneProdotto.setText(dispensa.get(position).getDescrizione());
        holder.descrizioneProdotto.setTextColor(Color.BLACK);

        holder.quantitaProdotto.setText(Double.toString(dispensa.get(position).getQuantita()));
        holder.quantitaProdotto.setTextColor(Color.BLACK);

        holder.unitaProdotto.setText(dispensa.get(position).getUnita());
        holder.quantitaProdotto.setTextColor(Color.BLACK);

        holder.costoAcquistoProdotto.setText(dispensa.get(position).getCostoAcquisto());
        holder.costoAcquistoProdotto.setTextColor(Color.BLACK);

    }

    @Override
    public int getItemCount() {
        return dispensa.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeProdotto;
        TextView descrizioneProdotto;
        TextView quantitaProdotto;
        TextView unitaProdotto;
        TextView costoAcquistoProdotto;

        public MyViewHolder(@NonNull View itemView, RecyclerViewAggiuntaIngredientiElementoInterface recyclerViewInterfaceIngredienteElemento) {
            super(itemView);

            nomeProdotto = itemView.findViewById(R.id.textViewNomeProdottoLabel);
            descrizioneProdotto = itemView.findViewById(R.id.textViewDescrizioneProdottoLabel);
            quantitaProdotto = itemView.findViewById(R.id.textViewQuantitaProdotto);
            unitaProdotto = itemView.findViewById(R.id.textViewMisuraProdotto);
            costoAcquistoProdotto = itemView.findViewById(R.id.textViewCostoAcquitoProdotto);

            itemView.findViewById(R.id.cardViewProdotto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterfaceIngredienteElemento != null){
                        int posizioneProdotto = getAdapterPosition();
                        if(posizioneProdotto != RecyclerView.NO_POSITION){
                            recyclerViewInterfaceIngredienteElemento.onProdottoClicked(posizioneProdotto, itemView);

                        }
                    }
                }
            });

        }
    }
}