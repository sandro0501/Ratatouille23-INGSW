package com.example.ratatouille23.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.listaAllergeni;
import com.example.ratatouille23.Presenters.PresenterRistorante;
import com.example.ratatouille23.R;

import java.io.File;
import java.util.ArrayList;

public class ElementoMenuRecyclerViewAdapter extends RecyclerView.Adapter<ElementoMenuRecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewElementoMenuInterface recyclerViewInterfaceElemento;

    private Context context;

    private ArrayList<Elemento> listaElementi;
    private Utente utenteCorrente;

    public ElementoMenuRecyclerViewAdapter(Context context, ArrayList<Elemento> listaElementi, RecyclerViewElementoMenuInterface recyclerViewInterfaceElemento, Utente utente){
        this.context = context;
        this.listaElementi = listaElementi;
        this.recyclerViewInterfaceElemento = recyclerViewInterfaceElemento;
        utenteCorrente = utente;
    }

    @NonNull
    @Override
    public ElementoMenuRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_elemento_menu, parent, false);

        return new ElementoMenuRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceElemento);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementoMenuRecyclerViewAdapter.MyViewHolder holder, int position) {

        Elemento elementoCorrente = listaElementi.get(position);

        holder.textViewTitoloPrincipale.setText(elementoCorrente.getDenominazionePrincipale());
        holder.textViewTitoloSecondario.setText(elementoCorrente.getDenominazioneSecondaria());
        holder.textViewDescrizionePrincipale.setText(elementoCorrente.getDescrizionePrincipale());
        holder.textViewDescrizioneSecondaria.setText(elementoCorrente.getDescrizioneSecondaria());

        if (utenteCorrente.getRuoloUtente().equals("Addetto al servizio"))
            holder.iconaVediIngredienti.setVisibility(View.INVISIBLE);
        else
            holder.iconaVediIngredienti.setVisibility(View.VISIBLE);

        holder.textViewCosto.setText("€" + elementoCorrente.getCosto().toString());
        boolean iconaDaMostrare;

        if (!listaElementi.isEmpty()) holder.sezioneCorrente = listaElementi.get(0).getAppartiene();

        for (ImageView icona : holder.listaIconeAllergeni) {
            iconaDaMostrare = false;
            for (Allergene allergeneCorrente : listaElementi.get(position).getPresenta()) {
                if (allergeneCorrente.getNome().equals(((listaAllergeni) icona.getTag()))){
                    iconaDaMostrare = true;
                }
            }
            if (iconaDaMostrare) icona.setVisibility(View.VISIBLE);
            else icona.setVisibility(View.INVISIBLE);
        }

        if (elementoCorrente.getAppartiene().getRistorante().isTuristico()){
            holder.textViewTitoloSecondario.setVisibility(View.VISIBLE);
            holder.textViewDescrizioneSecondaria.setVisibility(View.VISIBLE);
        }
        else {
            holder.textViewTitoloSecondario.setVisibility(View.INVISIBLE);
            holder.textViewDescrizioneSecondaria.setVisibility(View.INVISIBLE);
        }

        String pathFoto = ((Integer)elementoCorrente.getIdElemento()).toString()+"_LogoElemento.jpg";
        Amplify.Storage.list(pathFoto,
                result -> {
                    if (!result.getItems().isEmpty())
                        holder.setLogoDownload(pathFoto);
        },
                error -> Log.e("MyAmplifyApp", "List failure", error)
        );

    }

    @Override
    public int getItemCount() {
        return listaElementi.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //una specie di metodo onCreate che prende gli elementi di cui sono formate le card dal file xml

        TextView textViewTitoloPrincipale;
        TextView textViewTitoloSecondario;
        TextView textViewDescrizionePrincipale;
        TextView textViewDescrizioneSecondaria;
        TextView textViewCosto;
        ImageView immagineElemento;
        ImageView iconaVediIngredienti;

        ArrayList<ImageView> listaIconeAllergeni = new ArrayList<>();

        ImageView iconaPesce;
        ImageView iconaGlutine;
        ImageView iconaUova;
        ImageView iconaNoci;
        ImageView iconaArachidi;
        ImageView iconaSolfiti;
        ImageView iconaCrostacei;
        ImageView iconaMolluschi;
        ImageView iconaLupini;
        ImageView iconaSenape;
        ImageView iconaSesamo;
        ImageView iconaLattosio;
        ImageView iconaSedano;
        ImageView iconaSoia;

        SezioneMenu sezioneCorrente;
        File fileImmagine;

        public MyViewHolder(@NonNull View itemView, RecyclerViewElementoMenuInterface recyclerViewInterfaceElemento) {
            super(itemView);

            textViewTitoloPrincipale = itemView.findViewById(R.id.textViewTitoloPrincipaleElementoMenuCard);
            textViewTitoloSecondario = itemView.findViewById(R.id.textViewTitoloSecondarioElementoMenuCard);
            textViewDescrizionePrincipale = itemView.findViewById(R.id.textViewDescrizionePrincipaleElementoMenuCard);
            textViewDescrizioneSecondaria = itemView.findViewById(R.id.textViewDescrizioneSecondariaElementoMenuCard);
            textViewCosto = itemView.findViewById(R.id.textViewCostoElementoMenuCard);
            immagineElemento = itemView.findViewById(R.id.imageViewImmagineElementoMenuCard);
            iconaVediIngredienti = itemView.findViewById(R.id.imageViewVediIngredientiElementoMenuCard);

            iconaPesce = itemView.findViewById(R.id.imageViewIconaAllergeniPesceVisualizza);
            iconaGlutine = itemView.findViewById(R.id.imageViewIconaAllergeniGlutineVisualizza);
            iconaUova = itemView.findViewById(R.id.imageViewIconaAllergeniUovaVisualizza);
            iconaNoci = itemView.findViewById(R.id.imageViewIconaAllergeniNociVisualizza);
            iconaArachidi = itemView.findViewById(R.id.imageViewIconaAllergeniArachidiVisualizza);
            iconaSolfiti = itemView.findViewById(R.id.imageViewIconaAllergeniSolfitiVisualizza);
            iconaCrostacei = itemView.findViewById(R.id.imageViewIconaAllergeniCrostaceiVisualizza);
            iconaMolluschi = itemView.findViewById(R.id.imageViewIconaAllergeniMolluschiVisualizza);
            iconaLupini = itemView.findViewById(R.id.imageViewIconaAllergeniLupiniVisualizza);
            iconaSenape = itemView.findViewById(R.id.imageViewIconaAllergeniSenapeVisualizza);
            iconaSesamo = itemView.findViewById(R.id.imageViewIconaAllergeniSesamoVisualizza);
            iconaLattosio = itemView.findViewById(R.id.imageViewIconaAllergeniLattosioVisualizza);
            iconaSedano = itemView.findViewById(R.id.imageViewIconaAllergeniSedanoVisualizza);
            iconaSoia = itemView.findViewById(R.id.imageViewIconaAllergeniSoiaVisualizza);

            iconaPesce.setTag(listaAllergeni.Pesce);
            iconaGlutine.setTag(listaAllergeni.Glutine);
            iconaUova.setTag(listaAllergeni.Uova);
            iconaNoci.setTag(listaAllergeni.Noci);
            iconaArachidi.setTag(listaAllergeni.Arachidi);
            iconaSolfiti.setTag(listaAllergeni.Solfiti);
            iconaCrostacei.setTag(listaAllergeni.Crostacei);
            iconaMolluschi.setTag(listaAllergeni.Molluschi);
            iconaLupini.setTag(listaAllergeni.Lupini);
            iconaSenape.setTag(listaAllergeni.Senape);
            iconaSesamo.setTag(listaAllergeni.Sesamo);
            iconaLattosio.setTag(listaAllergeni.Lattosio);
            iconaSedano.setTag(listaAllergeni.Sedano);
            iconaSoia.setTag(listaAllergeni.Soia);

            listaIconeAllergeni.add(iconaPesce);
            listaIconeAllergeni.add(iconaMolluschi);
            listaIconeAllergeni.add(iconaArachidi);
            listaIconeAllergeni.add(iconaGlutine);
            listaIconeAllergeni.add(iconaUova);
            listaIconeAllergeni.add(iconaSolfiti);
            listaIconeAllergeni.add(iconaCrostacei);
            listaIconeAllergeni.add(iconaLupini);
            listaIconeAllergeni.add(iconaSenape);
            listaIconeAllergeni.add(iconaSesamo);
            listaIconeAllergeni.add(iconaLattosio);
            listaIconeAllergeni.add(iconaSedano);
            listaIconeAllergeni.add(iconaSoia);
            listaIconeAllergeni.add(iconaNoci);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterfaceElemento != null) {
                        if (utenteCorrente.getRuoloUtente().equals("Amministratore") || utenteCorrente.getRuoloUtente().equals("Supervisore")) {
                            int posizioneElemento = getAdapterPosition();
                            if (posizioneElemento != RecyclerView.NO_POSITION) {
                                recyclerViewInterfaceElemento.onElementoClicked(listaElementi.get(posizioneElemento), fileImmagine, view);
                            }
                        }
                    }
                }
            });

            iconaVediIngredienti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterfaceElemento != null) {
                        int posizioneElemento = getAdapterPosition();
                        if(posizioneElemento!=RecyclerView.NO_POSITION){
                            recyclerViewInterfaceElemento.onVediIngredientiElementoClicked(listaElementi.get(posizioneElemento), view);
                        }

                    }
                }
            });

        }

        public void setLogoDownload(String pathFoto) {
            Amplify.Storage.downloadFile(
                    pathFoto,
                    new File(((MenuFragment)recyclerViewInterfaceElemento).getContext().getFilesDir() + "/" + pathFoto),
                    result -> {
                        File fileLogo = result.getFile();
                        Bitmap bitmapLogo  = BitmapFactory.decodeFile(fileLogo.getAbsolutePath());
                        immagineElemento.setImageBitmap(bitmapLogo);
                        fileImmagine = fileLogo;
                    },
                    error -> PresenterRistorante.getInstance().mostraAlert(((MenuFragment)recyclerViewInterfaceElemento).getContext(), "Errore!", "L'immagine non è stata scaricata correttamente, riprovare")

            );
        }
    }

}
