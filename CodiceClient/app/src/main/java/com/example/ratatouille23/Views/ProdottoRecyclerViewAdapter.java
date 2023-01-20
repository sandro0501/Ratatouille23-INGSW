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
import com.example.ratatouille23.Presenters.PresenterDispensa;
import com.example.ratatouille23.R;

import java.util.ArrayList;

public class ProdottoRecyclerViewAdapter extends RecyclerView.Adapter<ProdottoRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewProdottoInterface recyclerViewInterfaceProdotto;

    private Context context;
    private ArrayList<Prodotto> dispensa;
    private boolean isProdottoSottoSoglia;

    public ProdottoRecyclerViewAdapter(Context context, ArrayList<Prodotto> dispensa, RecyclerViewProdottoInterface recyclerViewInterfaceProdotto){
        this.context = context;
        this.dispensa = dispensa;
        this.recyclerViewInterfaceProdotto = recyclerViewInterfaceProdotto;
    }

    @NonNull
    @Override
    public ProdottoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //fornisce lo stile grafico alle diverse "cardView"
        //LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_prodotto, parent, false);

        return new ProdottoRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceProdotto);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdottoRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assegna valori alle "card" che abbiamo creato in dipendenza anche della loro posizione nella lista recycler view

        isProdottoSottoSoglia = PresenterDispensa.getInstance().controllaSogliaProdotto(dispensa,position);

        /*
        if (Controller.getIsModalitaEliminazioneProdottoAttiva() == true){
            Prodotto prodottoCorrente = dispensa.get(position);
            CardView cardProdotto = holder.itemView.findViewById(R.id.cardViewProdotto);

            if(prodottoCorrente.isSelected()){
                cardProdotto.setCardBackgroundColor(Color.parseColor("#F2A726"));
            } else {
                cardProdotto.setCardBackgroundColor(Color.WHITE);
            }

            cardProdotto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prodottoCorrente.setSelected(!prodottoCorrente.isSelected());
                    if(prodottoCorrente.isSelected()){
                        cardProdotto.setCardBackgroundColor(Color.parseColor("#F2A726"));
                    } else {
                        cardProdotto.setCardBackgroundColor(Color.WHITE);
                    }

                }
            });

        } */

        holder.nomeProdotto.setText(dispensa.get(position).getNome());
        holder.descrizioneProdotto.setText(dispensa.get(position).getDescrizione());
        holder.quantitaProdotto.setText(Double.toString(dispensa.get(position).getQuantita()));

        if(isProdottoSottoSoglia){
            holder.quantitaProdotto.setTextColor(Color.RED);
        }

        holder.unitaProdotto.setText(dispensa.get(position).getUnita());
        holder.costoAcquistoProdotto.setText(dispensa.get(position).getCostoAcquisto());

    }



    @Override
    public int getItemCount() {
        //serve al recyclerView per conoscere quante "card" devono essere mostrate in totale sul display
        return dispensa.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //una specie di metodo onCreate che prende gli elementi di cui sono formate le card dal file xml
        TextView nomeProdotto;
        TextView descrizioneProdotto;
        TextView quantitaProdotto;
        TextView unitaProdotto;
        TextView costoAcquistoProdotto;

        public MyViewHolder(@NonNull View itemView, RecyclerViewProdottoInterface recyclerViewInterfaceProdotto) {
            super(itemView);

            nomeProdotto = itemView.findViewById(R.id.textViewNomeProdottoLabel);
            descrizioneProdotto = itemView.findViewById(R.id.textViewDescrizioneProdottoLabel);
            quantitaProdotto = itemView.findViewById(R.id.textViewQuantitaProdotto);
            unitaProdotto = itemView.findViewById(R.id.textViewMisuraProdotto);
            costoAcquistoProdotto = itemView.findViewById(R.id.textViewCostoAcquitoProdotto);

            itemView.findViewById(R.id.cardViewProdotto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterfaceProdotto != null) {
                        int posizioneProdotto = getAdapterPosition();
                        if(posizioneProdotto!=RecyclerView.NO_POSITION){
                                recyclerViewInterfaceProdotto.onProdottoClicked(posizioneProdotto, itemView);
                        }
                    }
                }
            });
        }
    }
}