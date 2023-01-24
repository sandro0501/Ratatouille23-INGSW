package com.example.ratatouille23.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.R;

public class IngredientiElementoRecyclerViewAdapter extends RecyclerView.Adapter<IngredientiElementoRecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewProdottoInterface recyclerViewInterfaceProdotto;
    private Context context;
    private Elemento elemento;

    public IngredientiElementoRecyclerViewAdapter(Context context, Elemento elemento, RecyclerViewProdottoInterface recyclerViewInterfaceProdotto){
        this.context = context;
        this.elemento = elemento;
        this.recyclerViewInterfaceProdotto = recyclerViewInterfaceProdotto;
    }

    @NonNull
    @Override
    public IngredientiElementoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_prodotto, parent, false);
        return new IngredientiElementoRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceProdotto);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.costoAcquistoProdotto.setVisibility(View.INVISIBLE);
        holder.costoAcquistoProdottoLabel.setVisibility(View.INVISIBLE);

        holder.nomeProdotto.setText(elemento.getPreparazione().get(position).getProdottoAssociato().getNome());
        holder.descrizioneProdotto.setText(elemento.getPreparazione().get(position).getProdottoAssociato().getDescrizione());

        holder.quantitaProdotto.setTextColor(Color.BLACK);
        holder.quantitaProdotto.setText(Double.toString(elemento.getPreparazione().get(position).getQuantitaNecessaria()));
        holder.unitaProdotto.setText(elemento.getPreparazione().get(position).getProdottoAssociato().getUnita());

    }

    @Override
    public int getItemCount() {
        return elemento.getPreparazione().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeProdotto;
        TextView descrizioneProdotto;
        TextView quantitaProdotto;
        TextView unitaProdotto;
        TextView costoAcquistoProdotto;
        TextView unitaProdottoLabel;
        TextView costoAcquistoProdottoLabel;

        public MyViewHolder(@NonNull View itemView, RecyclerViewProdottoInterface recyclerViewInterfaceProdotto) {
            super(itemView);

            unitaProdottoLabel = itemView.findViewById(R.id.textViewUnitaProdottoProdottoLabel);
            costoAcquistoProdottoLabel = itemView.findViewById(R.id.textViewCostoAcquistoLabel);
            nomeProdotto = itemView.findViewById(R.id.textViewNomeProdottoLabel);
            descrizioneProdotto = itemView.findViewById(R.id.textViewDescrizioneProdottoLabel);
            quantitaProdotto = itemView.findViewById(R.id.textViewQuantitaProdotto);
            unitaProdotto = itemView.findViewById(R.id.textViewMisuraProdotto);
            costoAcquistoProdotto = itemView.findViewById(R.id.textViewCostoAcquitoProdotto);


        }
    }


}
