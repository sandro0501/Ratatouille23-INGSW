package com.example.ratatouille23.Views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Preparazione;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.listaAllergeni;
import com.example.ratatouille23.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class VisualizzazioneIngredientiElementoActivity extends AppCompatActivity implements  RecyclerViewAggiuntaIngredientiElementoInterface{

    private Toolbar toolbarNavigazione;
    private RecyclerView pannelloIngredienti;
    private IngredientiElementoRecyclerViewAdapter ingredientiElementoAdapter;
    private Elemento elemento;
    private RecyclerViewProdottoInterface ingredientiElementoInterface;
    private ImageView iconaAggiuntaIngrediente;
    private Drawable iconaIndietro;
    private ImageView iconaCestino;
    private TextView titoloElemento;
    private AlertDialog.Builder builderDialogAggiuntaIngrediente;
    private Dialog dialogAggiuntaIngrediente;
    private Dialog dialogAggiuntaIngredienteSelezionato;
    private Button bottoneAggiuntaProdottoSelezionato;
    private Button annullaAggiuntaProdottoSelezionato;
    private Button bottoneAnnullaAggiungiProdottoPerElemento;
    private RecyclerView pannelloProdottiInDispensa;
    private AggiuntaIngredientiRecyclerViewAdapter prodottiInDispensaAdapter;
    private ArrayList<Prodotto> dispensa;
    private Prodotto prodottoCorrente = null;
    private SearchView searchViewRicercaProdotti;
    private TextView ricercaProdottiVuota;
    private TextView nomeProdottoSelezionato;
    private TextView unitaMisuraProdottoSelezionato;
    private Double quantitaProdottoCorrente;
    private String nomeProdottoCorrente;
    private String unitaMisuraProdottoCorrente;
    private ArrayList <Prodotto> dispensaFiltrata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_ingredienti_elemento);

        setUpPreparazioneElemento();
        onIndietroPremuto();
        inizializzaPannelloIngredienti();
        onAggiungiIngredientePremuto();


    }

    private void inizializzaPannelloIngredienti() {
        pannelloIngredienti = findViewById(R.id.recyclerViewIngredientiElemento);
        ingredientiElementoAdapter = new IngredientiElementoRecyclerViewAdapter(getApplicationContext(), elemento, ingredientiElementoInterface);
        pannelloIngredienti.setAdapter(ingredientiElementoAdapter);
        pannelloIngredienti.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ingredientiElementoAdapter.notifyDataSetChanged();

        titoloElemento = findViewById(R.id.textViewVisualizzazioneIngredientiElementoDenominazione);
        titoloElemento.append(elemento.getDenominazionePrincipale());
    }

    private void setUpPreparazioneElemento() {
        Prodotto passata = new Prodotto("Passata di pomodoro", "Passata buona", "kg", "2.30", 10, 5);
        Prodotto pasta = new Prodotto("Maccheroni", "maccarun", "kg", "1.00", 20, 10);
        Double quantitaPassata = 2.00;
        Double quantitaPasta = 3.00;

        Preparazione preparazionePastaAlPomodoroUno = new Preparazione(passata, quantitaPassata);
        Preparazione preparazionePastaAlPomodoroDue = new Preparazione(pasta, quantitaPasta);

        ArrayList<Preparazione> preparazionePastaAlPomodoro = new ArrayList<Preparazione>();
        preparazionePastaAlPomodoro.add(preparazionePastaAlPomodoroUno);
        preparazionePastaAlPomodoro.add(preparazionePastaAlPomodoroDue);

        ArrayList<Allergene> allergeniPasta = new ArrayList<Allergene>();
        Allergene allergenePasta = new Allergene(listaAllergeni.Glutine);
        allergeniPasta.add(allergenePasta);

        SezioneMenu sezione = new SezioneMenu("sezione", 1);

        elemento = new Elemento("Pasta al pomodoro", "pomodoro pasta", 5.00, 1, allergeniPasta, preparazionePastaAlPomodoro, sezione);

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

    private void onAggiungiIngredientePremuto(){
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
        Typeface myCustomFont = Typeface.createFromAsset(this.getAssets(), "res/font/verdana.ttf");
        searchText.setTypeface(myCustomFont);
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
    public void onProdottoClicked(int posizioneProdotto, View itemView) {

        prodottoCorrente = dispensa.get(posizioneProdotto);
        nomeProdottoCorrente = prodottoCorrente.getNome();
        quantitaProdottoCorrente = prodottoCorrente.getQuantita();
        unitaMisuraProdottoCorrente = prodottoCorrente.getUnita();

        dialogAggiuntaIngrediente.dismiss();

        final View viewAggiuntaIngredienteSelezionato = getLayoutInflater().inflate(R.layout.layout_aggiungi_prodotto_selezionato_per_elemento, null);

        nomeProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.textViewProdottoInDispensaSelezionato);
        nomeProdottoSelezionato.append(nomeProdottoCorrente);

        unitaMisuraProdottoSelezionato = viewAggiuntaIngredienteSelezionato.findViewById(R.id.textViewMisuraProdottoSelezionato);
        unitaMisuraProdottoSelezionato.setText(unitaMisuraProdottoCorrente);

        bottoneAnnullaAggiungiProdottoPerElemento = viewAggiuntaIngredienteSelezionato.findViewById(R.id.bottoneAnnullaAggiungiProdottoSelezionato);
        bottoneAnnullaAggiungiProdottoPerElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAggiuntaIngredienteSelezionato.dismiss();
                dialogAggiuntaIngrediente.show();
            }
        });



        builderDialogAggiuntaIngrediente.setView(viewAggiuntaIngredienteSelezionato);
        builderDialogAggiuntaIngrediente.setCancelable(true);
        dialogAggiuntaIngredienteSelezionato = builderDialogAggiuntaIngrediente.create();
        dialogAggiuntaIngredienteSelezionato.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogAggiuntaIngredienteSelezionato.show();

    }
}
