package com.example.ratatouille23.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class AvvisoRecyclerViewAdapter extends RecyclerView.Adapter<AvvisoRecyclerViewAdapter.MyViewHolder> {

	private final RecyclerViewAvvisoInterface recyclerViewInterfaceAvviso;

    private Context context;
    private ArrayList<Bacheca> avvisi;

    public AvvisoRecyclerViewAdapter(Context context, ArrayList<Bacheca> avvisi, RecyclerViewAvvisoInterface recyclerViewInterfaceAvviso){
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

        holder.imageViewAvvisi.setImageResource(R.drawable.icon_avviso_da_vedere);
        holder.autoreAvviso.setText(avvisi.get(position).getAvvisoAssociato().getAutore().getNomeCompleto());
        holder.oggettoAvviso.setText(avvisi.get(position).getAvvisoAssociato().getOggetto());
        holder.corpoAvviso.setText(avvisi.get(position).getAvvisoAssociato().getCorpo());
        LocalDate data = avvisi.get(position).getAvvisoAssociato().getDataCreazione();
        holder.dataAvviso.setText(data.toString());
        holder.ruoloAutoreAvviso.setText(avvisi.get(position).getAvvisoAssociato().getAutore().getRuoloUtente());

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

        public MyViewHolder(@NonNull View itemView, RecyclerViewAvvisoInterface recyclerViewInterfaceAvviso) {
            super(itemView);

            imageViewAvvisi = itemView.findViewById(R.id.imageViewImmagineElementoMenuCard);
            autoreAvviso = itemView.findViewById(R.id.textViewTitoloPrincipaleElementoMenuCard);
            oggettoAvviso = itemView.findViewById(R.id.textViewDescrizionePrincipaleElementoMenuCard);
            corpoAvviso = itemView.findViewById(R.id.textViewDescrizioneSecondariaElementoMenuCard);
            dataAvviso = itemView.findViewById(R.id.textViewCostoElementoMenuCard);
            ruoloAutoreAvviso = itemView.findViewById(R.id.textViewTitoloSecondarioElementoMenuCard);

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
