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
import com.example.ratatouille23.R;

import java.util.ArrayList;

public class AvvisoRecyclerViewAdapter extends RecyclerView.Adapter<AvvisoRecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterfaceAvviso;

    Context context;
    ArrayList<Avviso> avvisi;


    public AvvisoRecyclerViewAdapter(Context context, ArrayList<Avviso> avvisi, RecyclerViewInterface recyclerViewInterfaceAvviso){
        this.context = context;
        this.avvisi = avvisi;
        this.recyclerViewInterfaceAvviso = recyclerViewInterfaceAvviso;
    }

    @NonNull
    @Override
    public AvvisoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //fornisce lo stile grafico alle diverse "cardView"
        //LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_avviso, parent, false);

        return new AvvisoRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceAvviso);
    }

    @Override
    public void onBindViewHolder(@NonNull AvvisoRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assegna valori alle "card" che abbiamo creato in dipendenza anche della loro posizione nella lista recycler view

        holder.imageViewAvvisi.setImageResource(avvisi.get(position).getIconaAvviso());
        holder.autoreAvviso.setText(avvisi.get(position).getAutore());
        holder.oggettoAvviso.setText(avvisi.get(position).getOggetto());
        holder.corpoAvviso.setText(avvisi.get(position).getCorpo());
        holder.dataAvviso.setText(avvisi.get(position).getDataCreazione());
        holder.ruoloAutoreAvviso.setText(avvisi.get(position).getRuoloAutoreAvviso());

    }

    @Override
    public int getItemCount() {
        //serve al recyclerView per conoscere quante "card" devono essere mostrate in totale sul display
        return avvisi.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //una specie di metodo onCreate che prende gli elementi di cui sono formate le card dal file xml
        ImageView imageViewAvvisi;
        TextView autoreAvviso, oggettoAvviso, corpoAvviso, dataAvviso, ruoloAutoreAvviso;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterfaceAvviso) {
            super(itemView);

            imageViewAvvisi = itemView.findViewById(R.id.imageViewNotifica);
            autoreAvviso = itemView.findViewById(R.id.textViewAutoreAvviso);
            oggettoAvviso = itemView.findViewById(R.id.textViewOggettoAvviso);
            corpoAvviso = itemView.findViewById(R.id.textViewCorpoAvviso);
            dataAvviso = itemView.findViewById(R.id.textViewDataAvviso);
            ruoloAutoreAvviso = itemView.findViewById(R.id.textViewRuoloAutoreAvviso);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterfaceAvviso != null) {
                        int posizioneAvviso = getAdapterPosition();
                        if(posizioneAvviso!=RecyclerView.NO_POSITION){
                            recyclerViewInterfaceAvviso.onAvvisoClicked(posizioneAvviso);
                        }

                    }
                }
            });
        }
    }
}
