package com.example.ratatouille23.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Bacheca;
import com.example.ratatouille23.R;

import java.lang.reflect.Type;
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

        holder.autoreAvviso.setText(avvisi.get(position).getAvvisoAssociato().getAutore().getNomeCompleto());
        holder.oggettoAvviso.setText(avvisi.get(position).getAvvisoAssociato().getOggetto());
        holder.corpoAvviso.setText(avvisi.get(position).getAvvisoAssociato().getCorpo());
        LocalDate data = avvisi.get(position).getAvvisoAssociato().getDataCreazione();
        holder.dataAvviso.setText(data.toString());
        holder.ruoloAutoreAvviso.setText(avvisi.get(position).getAvvisoAssociato().getAutore().getRuoloUtente());

        Typeface verdanaFace = ResourcesCompat.getFont(context, R.font.verdana_font);
        if (avvisi.get(position).isVisualizzato()) {

            //cambia aspetto testo
            holder.autoreAvviso.setTypeface(verdanaFace, Typeface.NORMAL);
            holder.ruoloAutoreAvviso.setTypeface(verdanaFace, Typeface.ITALIC);
            holder.oggettoAvviso.setTypeface(verdanaFace, Typeface.NORMAL);
            holder.dataAvviso.setTypeface(verdanaFace, Typeface.NORMAL);

            //cambia icona notifica
            holder.imageViewAvvisi.setImageResource(R.drawable.icon_avviso_visto);
            holder.imageViewNascondiAvviso.setVisibility(View.VISIBLE);
        }
        else {
            holder.autoreAvviso.setTypeface(verdanaFace, Typeface.BOLD);
            holder.ruoloAutoreAvviso.setTypeface(verdanaFace, Typeface.BOLD_ITALIC);
            holder.oggettoAvviso.setTypeface(verdanaFace, Typeface.BOLD);
            holder.dataAvviso.setTypeface(verdanaFace, Typeface.BOLD);

            holder.imageViewAvvisi.setImageResource(R.drawable.icon_avviso_da_vedere);
            holder.imageViewNascondiAvviso.setVisibility(View.INVISIBLE);
        }

        holder.imageViewNascondiAvviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewInterfaceAvviso != null) {
                        recyclerViewInterfaceAvviso.onOcchioAvvisoClicked(avvisi.get(holder.getAdapterPosition()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //serve al recyclerView per conoscere quante "card" devono essere mostrate in totale sul display
        return avvisi.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //una specie di metodo onCreate che prende gli elementi di cui sono formate le card dal file xml
        ImageView imageViewAvvisi, imageViewNascondiAvviso;
        TextView autoreAvviso, oggettoAvviso, corpoAvviso, dataAvviso, ruoloAutoreAvviso;

        public MyViewHolder(@NonNull View itemView, RecyclerViewAvvisoInterface recyclerViewInterfaceAvviso) {
            super(itemView);

            imageViewAvvisi = itemView.findViewById(R.id.imageViewImmagineElementoMenuCard);
            autoreAvviso = itemView.findViewById(R.id.textViewTitoloPrincipaleElementoMenuCard);
            oggettoAvviso = itemView.findViewById(R.id.textViewDescrizionePrincipaleElementoMenuCard);
            corpoAvviso = itemView.findViewById(R.id.textViewDescrizioneSecondariaElementoMenuCard);
            dataAvviso = itemView.findViewById(R.id.textViewCostoElementoMenuCard);
            ruoloAutoreAvviso = itemView.findViewById(R.id.textViewTitoloSecondarioElementoMenuCard);
            imageViewNascondiAvviso = itemView.findViewById(R.id.imageViewVediIngredientiElementoMenuCard);

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
