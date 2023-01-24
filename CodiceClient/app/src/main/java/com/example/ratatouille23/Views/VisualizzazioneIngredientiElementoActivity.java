package com.example.ratatouille23.Views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Presenters.PresenterDispensa;
import com.example.ratatouille23.Presenters.PresenterMenu;
import com.example.ratatouille23.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class VisualizzazioneIngredientiElementoActivity extends AppCompatActivity implements  RecyclerViewIngredientiElementoInterface, RecyclerViewAggiuntaIngredientiElementoInterface{

    private Toolbar toolbarNavigazione;
    private RecyclerView pannelloIngredienti;
    private IngredientiElementoRecyclerViewAdapter ingredientiElementoAdapter;
    private Elemento elemento;
    private ImageView iconaAggiuntaIngrediente;
    private Drawable iconaIndietro;
    private ImageView iconaCestino;
    private TextView titoloElemento;
    private AlertDialog.Builder builderDialogAggiuntaIngrediente;
    private AlertDialog.Builder builderDialogEliminazioneIngrediente;
    private Dialog dialogAggiuntaIngrediente;
    private Dialog dialogAggiuntaIngredienteSelezionato;
    private Dialog dialogEliminaIngrediente;
    private Button bottoneAggiuntaProdottoSelezionato;
    private Button bottoneAnnullaAggiuntaProdottoSelezionato;
    private Button bottoneAnnullaAggiungiProdottoPerElemento;
    private Button bottoneAnnullaEliminazioneIngrediente;
    private Button bottoneConfermaEliminazioneIngrediente;
    private RecyclerView pannelloProdottiInDispensa;
    private AggiuntaIngredientiRecyclerViewAdapter prodottiInDispensaAdapter;
    private ArrayList<Prodotto> dispensa;
    private Prodotto prodottoCorrente = null;
    private SearchView searchViewRicercaProdotti;
    private TextView ricercaProdottiVuota;
    private TextView nomeProdottoSelezionato;
    private TextView unitaMisuraProdottoSelezionato;
    private TextView textViewEliminazioneIngrediente;
    private Double quantitaProdottoCorrente;
    private String nomeProdottoCorrente;
    private String unitaMisuraProdottoCorrente;
    private EditText quantitaProdottoSelezionato;
    private ArrayList <Prodotto> dispensaFiltrata;
    private ArrayList<Prodotto> listaProdottiSelezionati = new ArrayList<>();
    private ArrayList<CardView> listaCardProdottiSelezionati = new ArrayList<>();
    private boolean modalitaEliminazione = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_ingredienti_elemento);

        elemento = ((Elemento)getIntent().getExtras().get("Elemento selezionato"));

        onIndietroPremuto();
        inizializzaPannelloIngredienti();
        bottoneAggiuntaIngredientePremuto();
        bottoneEliminaIngredientePremuto();


    }

    @Override
    protected void onStop() {
        if (modalitaEliminazione){
            deselezionaTuttiProdotti();
            disattivaModalitaEliminazione();
        }
        super.onStop();
    }

    private void inizializzaPannelloIngredienti() {
        pannelloIngredienti = findViewById(R.id.recyclerViewIngredientiElemento);
        ingredientiElementoAdapter = new IngredientiElementoRecyclerViewAdapter(getApplicationContext(), elemento, this);
        pannelloIngredienti.setAdapter(ingredientiElementoAdapter);
        pannelloIngredienti.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ingredientiElementoAdapter.notifyDataSetChanged();

        titoloElemento = findViewById(R.id.textViewVisualizzazioneIngredientiElementoDenominazione);
        titoloElemento.append(elemento.getDenominazionePrincipale());
    }

    private void onIndietroPremuto() {
        iconaIndietro = getResources().getDrawable(R.drawable.icon_back_arrow);
        toolbarNavigazione = (Toolbar) findViewById(R.id.toolbarVisualizzazioneIngredientiElemento);
        toolbarNavigazione.setNavigationIcon(iconaIndietro);
        toolbarNavigazione.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }

    private void bottoneEliminaIngredientePremuto() {
        iconaCestino = findViewById(R.id.imageViewIconEliminaProdottoPreparazioneElemento);
        iconaCestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modalitaEliminazione) {
                    if (listaProdottiSelezionati.size() == 0) disattivaModalitaEliminazione();
                    else {
                        final View viewEliminaProdotto = getLayoutInflater().inflate(R.layout.layout_elimina_prodotto_dialog, null);
                        builderDialogEliminazioneIngrediente = new AlertDialog.Builder(VisualizzazioneIngredientiElementoActivity.this);
                        builderDialogEliminazioneIngrediente.setView(viewEliminaProdotto);
                        builderDialogEliminazioneIngrediente.setCancelable(true);

                        textViewEliminazioneIngrediente = (TextView) viewEliminaProdotto.findViewById(R.id.textViewEliminaProdottoDescrizioneDialog);
                        if (listaProdottiSelezionati.size() == 1) {
                            textViewEliminazioneIngrediente.setText("Sei sicuro di voler eliminare il prodotto selezionato dalla preparazione dell'elemento?");
                        } else {
                            textViewEliminazioneIngrediente.setText("Sei sicuro di voler eliminare i " + listaProdottiSelezionati.size() + " prodotti selezionati dalla preparazione dell'elemento?");
                        }

                        bottoneAnnullaEliminazioneIngrediente = (Button) viewEliminaProdotto.findViewById(R.id.bottoneAnnullaEliminaProdotto);
                        bottoneAnnullaEliminazioneIngrediente.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                disattivaModalitaEliminazione();
                                deselezionaTuttiProdotti();
                                dialogEliminaIngrediente.dismiss();
                            }
                        });

                        bottoneConfermaEliminazioneIngrediente = (Button) viewEliminaProdotto.findViewById(R.id.bottoneEliminaProdotto);
                        bottoneConfermaEliminazioneIngrediente.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //codice db
                                PresenterDispensa.getInstance().mostraAlert(VisualizzazioneIngredientiElementoActivity.this, "Eliminazione effettuata", "Eliminazione dei prodotti selezionati effettuata correttamente!");
                                disattivaModalitaEliminazione();
                                deselezionaTuttiProdotti();
                                dialogEliminaIngrediente.dismiss();
                            }
                        });

                        dialogEliminaIngrediente = builderDialogEliminazioneIngrediente.create();
                        dialogEliminaIngrediente.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                        dialogEliminaIngrediente.show();
                    }
                }
                else {
                    modalitaEliminazione = true;
                    iconaAggiuntaIngrediente.setEnabled(false);
                    iconaCestino.setImageResource(R.drawable.icon_elimina_prodotto_elemento_attiva);
                    Toast.makeText(VisualizzazioneIngredientiElementoActivity.this,
                            "Modalità eliminazione ingrediente dalla preparazione attiva", Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    private void deselezionaTuttiProdotti() {
        modalitaEliminazione = false;
        for (CardView card : listaCardProdottiSelezionati) card.setCardBackgroundColor(Color.WHITE);
        listaCardProdottiSelezionati.clear();
        listaProdottiSelezionati.clear();
    }

    private void disattivaModalitaEliminazione() {
        modalitaEliminazione = false;
        iconaAggiuntaIngrediente.setEnabled(true);
        iconaCestino.setImageResource(R.drawable.icon_elimina_prodotto_elemento);
    }

    private void bottoneAggiuntaIngredientePremuto(){
        iconaAggiuntaIngrediente = findViewById(R.id.imageViewIconAddProdottoPreparazioneElemento);
        iconaAggiuntaIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraRicercaProdottiInDispensa();
            }
        });
    }

    private void mostraRicercaProdottiInDispensa(){
        final View viewAggiuntaIngrediente = getLayoutInflater().inflate(R.layout.layout_ricerca_prodotto_per_elemento, null);
        builderDialogAggiuntaIngrediente = new AlertDialog.Builder(VisualizzazioneIngredientiElementoActivity.this);
        builderDialogAggiuntaIngrediente.setView(viewAggiuntaIngrediente);
        builderDialogAggiuntaIngrediente.setCancelable(true);

        pannelloProdottiInDispensa = viewAggiuntaIngrediente.findViewById(R.id.recyclerViewDispensaElemento);
        ricercaProdottiVuota = viewAggiuntaIngrediente.findViewById(R.id.textViewRicercaVuotaProdottoInDispensa);
        riempiDispensa();
        prodottiInDispensaAdapter = new AggiuntaIngredientiRecyclerViewAdapter(viewAggiuntaIngrediente.getContext(), dispensa, this);
        pannelloProdottiInDispensa.setAdapter(prodottiInDispensaAdapter);
        pannelloProdottiInDispensa.setLayoutManager(new LinearLayoutManager(viewAggiuntaIngrediente.getContext()));
        prodottiInDispensaAdapter.notifyDataSetChanged();

        searchViewRicercaProdotti = viewAggiuntaIngrediente.findViewById(R.id.searchViewRicercaProdottoInDispensa);
        TextView searchText = (TextView) searchViewRicercaProdotti.findViewById(androidx.appcompat.R.id.search_src_text);
        try {
            @SuppressLint("SoonBlockedPrivateApi") Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            field.set(searchText, R.drawable.color_cursor);
        } catch (Exception e){ }

        searchViewRicercaProdotti.clearFocus();
        searchViewRicercaProdotti.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nomeProdotto) {
                filtraProdottiInDispensa(nomeProdotto);
                return true;
            }
        });

        bottoneAnnullaAggiungiProdottoPerElemento = (Button) viewAggiuntaIngrediente.findViewById(R.id.bottoneAnnullaAggiungiProdottoPerElemento);
        bottoneAnnullaAggiungiProdottoPerElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAggiuntaIngrediente.dismiss();
            }
        });

        dialogAggiuntaIngrediente = builderDialogAggiuntaIngrediente.create();
        dialogAggiuntaIngrediente.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogAggiuntaIngrediente.show();
    }

    private void riempiDispensa(){
        dispensa = new ArrayList<>();

        String[] nomeProdotto = getResources().getStringArray(R.array.nome_prodotto);
        String[] descrizoneProdotto = getResources().getStringArray(R.array.descrizione_prodotto);
        String[] unitaProdotto = getResources().getStringArray(R.array.unita_prodotto);
        String[] costoAcquistoProdotto = getResources().getStringArray(R.array.costoacq_prodotto);
        String[] quantitaProdotto = getResources().getStringArray(R.array.quantita_prodotto);
        String[] sogliaProdotto = getResources().getStringArray(R.array.soglia_prodotto);


        for(int i=0; i<nomeProdotto.length; i++){
            Prodotto prodotto = new Prodotto(nomeProdotto[i],descrizoneProdotto[i],unitaProdotto[i],costoAcquistoProdotto[i],
                    Double.parseDouble(quantitaProdotto[i]),Double.parseDouble(sogliaProdotto[i]));
            dispensa.add(prodotto);
        }
    }

    private void filtraProdottiInDispensa(String nomeProdotto) {
        dispensaFiltrata = new ArrayList<Prodotto>();

        for(Prodotto prodotto : dispensa){
            if(prodotto.getNome().toLowerCase().contains(nomeProdotto.toLowerCase())){
                ricercaProdottiVuota.setVisibility(View.GONE);
                dispensaFiltrata.add(prodotto);
            }

            prodottiInDispensaAdapter.setDispensaFiltrata(dispensaFiltrata);


            if(dispensaFiltrata.isEmpty()){
                ricercaProdottiVuota.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onProdottoClicked(Prodotto prodottoSelezionato, View itemView) {
            prodottoCorrente = prodottoSelezionato;

                nomeProdottoCorrente = prodottoCorrente.getNome();
                quantitaProdottoCorrente = prodottoCorrente.getQuantita();
                unitaMisuraProdottoCorrente = prodottoCorrente.getUnita();

                dialogAggiuntaIngrediente.dismiss();

                final View viewAggiuntaIngredienteSelezionato = getLayoutInflater().inflate(R.layout.layout_aggiungi_prodotto_selezionato_per_elemento, null);

                nomeProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.textViewProdottoInDispensaSelezionato);
                nomeProdottoSelezionato.append(nomeProdottoCorrente);

                unitaMisuraProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.textViewMisuraProdottoSelezionato);
                unitaMisuraProdottoSelezionato.setText(unitaMisuraProdottoCorrente);

                bottoneAnnullaAggiuntaProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.bottoneAnnullaAggiungiProdottoSelezionato);
                bottoneAnnullaAggiuntaProdottoSelezionato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAggiuntaIngredienteSelezionato.dismiss();
                        dialogAggiuntaIngrediente.show();
                    }
                });

                bottoneAggiuntaProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.bottoneAggiungiProdottoSelezionato);
                bottoneAggiuntaProdottoSelezionato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quantitaProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.EditTextQuantitaProdottoSelezionato);

                        if (quantitaProdottoSelezionato.getText().toString().trim().length() != 0) {
                            PresenterMenu.getInstance().mostraAlert(VisualizzazioneIngredientiElementoActivity.this, "Prodotto selezionato aggiunto",
                                    "Hai aggiunto correttamente all'elemento del menù " + quantitaProdottoSelezionato.getText() + " " +
                                            unitaMisuraProdottoCorrente + " del prodotto " + nomeProdottoCorrente);

                    /*
                    Preparazione p = new Preparazione(prodottoCorrente, Double.parseDouble(quantitaProdottoSelezionato.getText().toString()));
                    elemento.getPreparazione().add(p); */

                            dialogAggiuntaIngredienteSelezionato.dismiss();
                            dialogAggiuntaIngrediente.dismiss();
                        } else {
                            PresenterMenu.getInstance().mostraAlert(VisualizzazioneIngredientiElementoActivity.this, "Attenzione",
                                    "Non hai specificato la quantità necessaria alla preparazione per il prodotto selezionato");
                        }

                    }
                });

                builderDialogAggiuntaIngrediente.setView(viewAggiuntaIngredienteSelezionato);
                builderDialogAggiuntaIngrediente.setCancelable(true);
                dialogAggiuntaIngredienteSelezionato = builderDialogAggiuntaIngrediente.create();
                dialogAggiuntaIngredienteSelezionato.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                dialogAggiuntaIngredienteSelezionato.show();
        }

    @Override
    public void onProdottoAssociatoAElementoSelezionato(Prodotto prodottoSelezionato, View itemView) {
        if(modalitaEliminazione){
            selezionaDeselezionaProdotto(prodottoSelezionato, itemView);
        }

    }

    private void selezionaDeselezionaProdotto(Prodotto prodottoCorrente, View itemView) {

        if (!listaProdottiSelezionati.contains(prodottoCorrente)) {
            listaProdottiSelezionati.add(prodottoCorrente);
            CardView cardProdotto = itemView.findViewById(R.id.cardViewProdotto);
            cardProdotto.setCardBackgroundColor(Color.parseColor("#F4B851"));
            listaCardProdottiSelezionati.add(cardProdotto);
        }
        else {
            listaProdottiSelezionati.remove(prodottoCorrente);
            CardView cardProdotto = itemView.findViewById(R.id.cardViewProdotto);
            cardProdotto.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            listaCardProdottiSelezionati.remove(cardProdotto);
        }
    }
}

