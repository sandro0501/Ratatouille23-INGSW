package com.example.ratatouille23.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.listaAllergeni;
import com.example.ratatouille23.Presenters.PresenterMenu;
import com.example.ratatouille23.Presenters.PresenterRistorante;
import com.example.ratatouille23.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements RecyclerViewSezioneMenuInterface, RecyclerViewElementoMenuInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE = 5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView iconaCestino;
    private Spinner spinnerSceltaAggiunta;
    private TextView textViewSpinner;

    private TextView titoloDialog;
    private AutoCompleteTextView titoloPrincipaleElementoEditText;
    private EditText titoloSecondarioElementoEditText;
    private EditText descrizionePrincipaleElementoEditText;
    private EditText descrizioneSecondariaElementoEditText;
    private EditText prezzoElementoEditText;
    private Spinner sezioneElementoSpinner;
    private Button bottoneConferma;
    private Button bottoneAnnulla;

    private View fragmentCorrente;

    private RecyclerView recyclerViewMenu;
    private SezioneMenuRecyclerViewAdapter adapterSezioni;
    private ArrayList<SezioneMenu> listaSezioni = new ArrayList<>();
    private ArrayList<Elemento> listaElementiSelezionati = new ArrayList<>();

    private ItemTouchHelper itemTouchHelper;
    private ArrayList<CardView> listaCardElementiSelezionati = new ArrayList<>();

    private AlertDialog.Builder builderDialogElemento;
    private Dialog dialogElemento;

    private Utente utenteCorrente;
    private Ristorante ristoranteCorrente;

    private boolean modalitaEliminazione = false;
    private boolean primoAccessoSpinner;

    private ArrayAdapter<Elemento> adapterAutoComplete;

    private CheckBox checkboxPesce, checkboxGlutine, checkboxUova, checkboxNoci, checkboxArachidi, checkboxSolfiti, checkboxCrostacei, checkboxMolluschi, checkboxLupini, checkboxSenape, checkboxSesamo, checkboxLattosio, checkboxSedano, checkboxSoia;
    private AppCompatImageButton bottoneModificaFoto;
    private ImageView logoElemento;
    private Uri uriLogoCorrente;
    private boolean fotoModificata = false;

    public MenuFragment() {
        // Required empty public constructor
    }

    public boolean isModalitaEliminazione() {
        return modalitaEliminazione;
    }

    public void setModalitaEliminazione(boolean modalitaEliminazione) {
        this.modalitaEliminazione = modalitaEliminazione;
    }

    public SezioneMenuRecyclerViewAdapter getAdapterSezioni() {
        return adapterSezioni;
    }

    public void setAdapterSezioni(SezioneMenuRecyclerViewAdapter adapterSezioni) {
        this.adapterSezioni = adapterSezioni;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return itemTouchHelper;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentCorrente = inflater.inflate(R.layout.fragment_menu, container, false);
        primoAccessoSpinner = true;
        return fragmentCorrente;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerViewMenu = view.findViewById(R.id.recyclerViewSezioniMenu);
        spinnerSceltaAggiunta = view.findViewById(R.id.spinnerSceltaAggiuntaSezioneElementoMenu);
        iconaCestino = view.findViewById(R.id.iconaCestinoMenu);
        textViewSpinner = view.findViewById(R.id.textViewSceltaAggiunta);

        adapterAutoComplete = new ArrayAdapter<Elemento>(getContext(), R.layout.spinner_layout, new ArrayList<>());
        adapterAutoComplete.setNotifyOnChange(true);

        adapterSezioni = new SezioneMenuRecyclerViewAdapter(getContext(), listaSezioni, this);
        recyclerViewMenu.setAdapter(adapterSezioni);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSezioni.notifyDataSetChanged();

        String sceltaElementoSezione[] = {"Aggiungi una sezione", "Aggiungi un piatto", "Placeholder"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_menu_layout, sceltaElementoSezione) {
            @Override
            public int getCount() {
                return (2); // Truncate the list
            }
        };

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinnerSceltaAggiunta.setAdapter(spinnerAdapter);
        spinnerSceltaAggiunta.setSelection(2, false);
        Log.i("CREATE", "CREATE");

        utenteCorrente = (Utente)getActivity().getIntent().getSerializableExtra("Utente");
        ristoranteCorrente = utenteCorrente.getIdRistorante();

        iconaCestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaElementiSelezionati.size() == 0)
                    attivaDisattivaModalitaEliminazione();
                else
                    eliminaProdottiSelezionati();
            }
        });

        spinnerSceltaAggiunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (primoAccessoSpinner) {
                    primoAccessoSpinner = false;
                    spinnerSceltaAggiunta.setSelection(2, false);
                }
                else {

                    if (spinnerAdapter.getItem(i).equals("Aggiungi una sezione")) {
                        SezioneMenu nuovaSezione = new SezioneMenu("Nuova sezione", listaSezioni.size());
                        nuovaSezione.setRistorante(ristoranteCorrente);
                        PresenterMenu.getInstance().aggiungiSezione(MenuFragment.this, nuovaSezione);
                        spinnerSceltaAggiunta.setSelection(2, false);

                    } else if (spinnerAdapter.getItem(i).equals("Aggiungi un piatto")) {
                        mostraDialogAggiuntaElemento();
                        spinnerSceltaAggiunta.setSelection(2, false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

         itemTouchHelper = new ItemTouchHelper(simpleCallbackSezioni);
         itemTouchHelper.attachToRecyclerView(recyclerViewMenu);

    }

    @Override
    public void onStart() {
        PresenterMenu.getInstance().aggiornaDatiRistorante(MenuFragment.this, ristoranteCorrente.getIdRistorante());
        PresenterMenu.getInstance().estraiMenu(this, ristoranteCorrente);
        super.onStart();
    }

    private void mostraDialogAggiuntaElemento() {
        final View viewAggiungiElemento = getLayoutInflater().inflate(R.layout.layout_aggiungi_elemento_dialog, null);

        builderDialogElemento = new AlertDialog.Builder(getContext(), R.style.WrapContentDialog);
        builderDialogElemento.setView(viewAggiungiElemento);
        builderDialogElemento.setCancelable(true);


        ArrayList<CheckBox> checkBoxAllergeni = new ArrayList<>();

        checkboxPesce = viewAggiungiElemento.findViewById(R.id.checkBoxAllergenePesce);
        checkboxGlutine = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneGlutine);
        checkboxUova = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneUova);
        checkboxNoci = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneNoci);
        checkboxArachidi = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneArachidi);
        checkboxSolfiti = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneSolfiti);
        checkboxCrostacei = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneCrostacei);
        checkboxMolluschi = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneFruttiDiMare);
        checkboxLupini = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneLupini);
        checkboxSenape = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneMostarda);
        checkboxSesamo = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneSesamo);
        checkboxLattosio = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneLatte);
        checkboxSedano = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneSedano);
        checkboxSoia = viewAggiungiElemento.findViewById(R.id.checkBoxAllergeneSoia);

        checkboxPesce.setTag(listaAllergeni.Pesce);
        checkboxGlutine.setTag(listaAllergeni.Glutine);
        checkboxUova.setTag(listaAllergeni.Uova);
        checkboxNoci.setTag(listaAllergeni.Noci);
        checkboxArachidi.setTag(listaAllergeni.Arachidi);
        checkboxSolfiti.setTag(listaAllergeni.Solfiti);
        checkboxCrostacei.setTag(listaAllergeni.Crostacei);
        checkboxMolluschi.setTag(listaAllergeni.Molluschi);
        checkboxLupini.setTag(listaAllergeni.Lupini);
        checkboxSenape.setTag(listaAllergeni.Senape);
        checkboxSesamo.setTag(listaAllergeni.Sesamo);
        checkboxLattosio.setTag(listaAllergeni.Lattosio);
        checkboxSedano.setTag(listaAllergeni.Sedano);
        checkboxSoia.setTag(listaAllergeni.Soia);

        checkBoxAllergeni.add(checkboxPesce);
        checkBoxAllergeni.add(checkboxMolluschi);
        checkBoxAllergeni.add(checkboxArachidi);
        checkBoxAllergeni.add(checkboxGlutine);
        checkBoxAllergeni.add(checkboxUova);
        checkBoxAllergeni.add(checkboxSolfiti);
        checkBoxAllergeni.add(checkboxCrostacei);
        checkBoxAllergeni.add(checkboxLupini);
        checkBoxAllergeni.add(checkboxSenape);
        checkBoxAllergeni.add(checkboxSesamo);
        checkBoxAllergeni.add(checkboxLattosio);
        checkBoxAllergeni.add(checkboxSedano);
        checkBoxAllergeni.add(checkboxSoia);
        checkBoxAllergeni.add(checkboxNoci);

        titoloDialog = (TextView) viewAggiungiElemento.findViewById(R.id.textViewAggiungiElementoTitolo);
        titoloPrincipaleElementoEditText = (AutoCompleteTextView) viewAggiungiElemento.findViewById(R.id.EditTextTitoloPrincipaleElemento);
        titoloSecondarioElementoEditText = (EditText) viewAggiungiElemento.findViewById(R.id.EditTextTitoloSecondarioElemento);
        descrizionePrincipaleElementoEditText = (EditText) viewAggiungiElemento.findViewById(R.id.editTexDescrizionePrincipaleElemento);
        descrizioneSecondariaElementoEditText = (EditText) viewAggiungiElemento.findViewById(R.id.editTextDescrizioneSecondariaElemento);
        prezzoElementoEditText = (EditText) viewAggiungiElemento.findViewById(R.id.editTextCostoElemento);
        sezioneElementoSpinner = (Spinner) viewAggiungiElemento.findViewById(R.id.spinnerSezioneElemento);
        bottoneConferma = (Button) viewAggiungiElemento.findViewById(R.id.bottoneAggiungiModificaElemento);
        bottoneAnnulla = (Button) viewAggiungiElemento.findViewById(R.id.bottoneAnnullaElemento);
        bottoneModificaFoto = viewAggiungiElemento.findViewById(R.id.imageButtonSceltaFotoElemento);
        logoElemento = viewAggiungiElemento.findViewById(R.id.iconaLogoElemento);

        if(!ristoranteCorrente.isTuristico()){
            titoloSecondarioElementoEditText.setEnabled(false);
            titoloSecondarioElementoEditText.setHint("Per impostare un titolo secondario, rendere il ristorante turistico");
            descrizioneSecondariaElementoEditText.setEnabled(false);
            descrizioneSecondariaElementoEditText.setHint("Per impostare una descrizione secondaria, rendere il ristorante turistico");
        } else {
            titoloSecondarioElementoEditText.setEnabled(true);
            descrizioneSecondariaElementoEditText.setEnabled(true);
        }

        ArrayAdapter<SezioneMenu> adapterSpinner = new ArrayAdapter<SezioneMenu>(getContext(), R.layout.spinner_layout, listaSezioni);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_item_layout);
        sezioneElementoSpinner.setAdapter(adapterSpinner);

        titoloPrincipaleElementoEditText.setAdapter(adapterAutoComplete);

        titoloPrincipaleElementoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (titoloPrincipaleElementoEditText.getText().toString().length() >= titoloPrincipaleElementoEditText.getThreshold()) {
                    PresenterMenu.getInstance().settaElementiDaIniziale(MenuFragment.this, titoloPrincipaleElementoEditText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapterAutoComplete.clear();
            }
        });

        titoloPrincipaleElementoEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Elemento elementoScelto = adapterAutoComplete.getItem(i);
                descrizionePrincipaleElementoEditText.setText(elementoScelto.getDescrizionePrincipale());
                for (CheckBox checkBox : checkBoxAllergeni) {
                    for (Allergene allergene : elementoScelto.getPresenta()){
                        if (allergene.getNome().equals(checkBox.getTag()))
                            checkBox.setChecked(true);
                    }
                }

            }
        });

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SezioneMenu sezioneScelta = (SezioneMenu)sezioneElementoSpinner.getSelectedItem();
                Elemento piattoDaAggiungere = new Elemento(
                        titoloPrincipaleElementoEditText.getText().toString(),
                        descrizionePrincipaleElementoEditText.getText().toString(),
                        Double.parseDouble(prezzoElementoEditText.getText().toString()),
                        sezioneScelta.getAppartenente().size()
                        );
                piattoDaAggiungere.setDenominazioneSecondaria(titoloSecondarioElementoEditText.getText().toString());
                piattoDaAggiungere.setDescrizioneSecondaria(descrizioneSecondariaElementoEditText.getText().toString());
                piattoDaAggiungere.setAppartiene(sezioneScelta);
                ArrayList<Allergene> allergeniPiattoCorrente = new ArrayList<>();
                for (CheckBox checkBox : checkBoxAllergeni) {
                    if (checkBox.isChecked()) {
                        allergeniPiattoCorrente.add(new Allergene((listaAllergeni) checkBox.getTag()));
                    }
                }
                piattoDaAggiungere.setPresenta(allergeniPiattoCorrente);
                PresenterMenu.getInstance().aggiungiElemento(MenuFragment.this, piattoDaAggiungere);
            }
        });

        bottoneModificaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogElemento.dismiss();
            }
        });



        dialogElemento = builderDialogElemento.create();
        dialogElemento.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogElemento.show();

    }

    private void eliminaProdottiSelezionati() {
        PresenterMenu.getInstance().eliminaElementi(this, listaElementiSelezionati);
        attivaDisattivaModalitaEliminazione();
    }

    private void attivaDisattivaModalitaEliminazione() {
        int width = iconaCestino.getWidth();
        int height = iconaCestino.getHeight();
        if (modalitaEliminazione) {

            modalitaEliminazione = false;
            iconaCestino.setImageResource(R.drawable.icon_rimuovi_elemento);
            iconaCestino.requestLayout();
            iconaCestino.getLayoutParams().height = height;
            iconaCestino.getLayoutParams().width = width;
            deselezionaTuttiElementi();
            spinnerSceltaAggiunta.setVisibility(View.VISIBLE);
            textViewSpinner.setVisibility(View.VISIBLE);
        }
        else {
            modalitaEliminazione = true;
            iconaCestino.setImageResource(R.drawable.icon_rimuovi_elemento_selected);
            iconaCestino.requestLayout();
            iconaCestino.getLayoutParams().height = height;
            iconaCestino.getLayoutParams().width = width;
            spinnerSceltaAggiunta.setVisibility(View.INVISIBLE);
            textViewSpinner.setVisibility(View.INVISIBLE);
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallbackSezioni = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

        SezioneMenuRecyclerViewAdapter.MyViewHolder holderCorrente;

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            holderCorrente = (SezioneMenuRecyclerViewAdapter.MyViewHolder)viewHolder;

            int posizioneIniziale = viewHolder.getAdapterPosition();
            int posizioneFinale = target.getAdapterPosition();

            Collections.swap(listaSezioni, posizioneIniziale, posizioneFinale);

            recyclerView.getAdapter().notifyItemMoved(posizioneIniziale, posizioneFinale);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
        @Override
        public boolean isLongPressDragEnabled()
        {
            return false;
        }
    };


    @Override
    public void onElementoClicked(Elemento elementoCliccato, File fileImmagine, View itemView) {
        if (modalitaEliminazione) {
            selezionaDeseleziona(elementoCliccato, itemView);
        }
        else {
            //modifica elemento
            Log.println(Log.VERBOSE, "aa", elementoCliccato.getDenominazionePrincipale());
            mostraDialogModificaElemento(elementoCliccato, fileImmagine);

        }
    }

    private void mostraDialogModificaElemento(Elemento elementoDaModificare, File fileImmagine) {
        final View viewModificaElemento = getLayoutInflater().inflate(R.layout.layout_aggiungi_elemento_dialog, null);

        builderDialogElemento = new AlertDialog.Builder(getContext(), R.style.WrapContentDialog);
        builderDialogElemento.setView(viewModificaElemento);
        builderDialogElemento.setCancelable(true);

        ArrayList<CheckBox> checkBoxAllergeni = new ArrayList<>();

        checkboxPesce = viewModificaElemento.findViewById(R.id.checkBoxAllergenePesce);
        checkboxGlutine = viewModificaElemento.findViewById(R.id.checkBoxAllergeneGlutine);
        checkboxUova = viewModificaElemento.findViewById(R.id.checkBoxAllergeneUova);
        checkboxNoci = viewModificaElemento.findViewById(R.id.checkBoxAllergeneNoci);
        checkboxArachidi = viewModificaElemento.findViewById(R.id.checkBoxAllergeneArachidi);
        checkboxSolfiti = viewModificaElemento.findViewById(R.id.checkBoxAllergeneSolfiti);
        checkboxCrostacei = viewModificaElemento.findViewById(R.id.checkBoxAllergeneCrostacei);
        checkboxMolluschi = viewModificaElemento.findViewById(R.id.checkBoxAllergeneFruttiDiMare);
        checkboxLupini = viewModificaElemento.findViewById(R.id.checkBoxAllergeneLupini);
        checkboxSenape = viewModificaElemento.findViewById(R.id.checkBoxAllergeneMostarda);
        checkboxSesamo = viewModificaElemento.findViewById(R.id.checkBoxAllergeneSesamo);
        checkboxLattosio = viewModificaElemento.findViewById(R.id.checkBoxAllergeneLatte);
        checkboxSedano = viewModificaElemento.findViewById(R.id.checkBoxAllergeneSedano);
        checkboxSoia = viewModificaElemento.findViewById(R.id.checkBoxAllergeneSoia);

        checkboxPesce.setTag(listaAllergeni.Pesce);
        checkboxGlutine.setTag(listaAllergeni.Glutine);
        checkboxUova.setTag(listaAllergeni.Uova);
        checkboxNoci.setTag(listaAllergeni.Noci);
        checkboxArachidi.setTag(listaAllergeni.Arachidi);
        checkboxSolfiti.setTag(listaAllergeni.Solfiti);
        checkboxCrostacei.setTag(listaAllergeni.Crostacei);
        checkboxMolluschi.setTag(listaAllergeni.Molluschi);
        checkboxLupini.setTag(listaAllergeni.Lupini);
        checkboxSenape.setTag(listaAllergeni.Senape);
        checkboxSesamo.setTag(listaAllergeni.Sesamo);
        checkboxLattosio.setTag(listaAllergeni.Lattosio);
        checkboxSedano.setTag(listaAllergeni.Sedano);
        checkboxSoia.setTag(listaAllergeni.Soia);

        checkBoxAllergeni.add(checkboxPesce);
        checkBoxAllergeni.add(checkboxMolluschi);
        checkBoxAllergeni.add(checkboxArachidi);
        checkBoxAllergeni.add(checkboxGlutine);
        checkBoxAllergeni.add(checkboxUova);
        checkBoxAllergeni.add(checkboxSolfiti);
        checkBoxAllergeni.add(checkboxCrostacei);
        checkBoxAllergeni.add(checkboxLupini);
        checkBoxAllergeni.add(checkboxSenape);
        checkBoxAllergeni.add(checkboxSesamo);
        checkBoxAllergeni.add(checkboxLattosio);
        checkBoxAllergeni.add(checkboxSedano);
        checkBoxAllergeni.add(checkboxSoia);
        checkBoxAllergeni.add(checkboxNoci);

        titoloDialog = (TextView) viewModificaElemento.findViewById(R.id.textViewAggiungiElementoTitolo);
        titoloDialog.setText("Modifica piatto del menù");

        titoloPrincipaleElementoEditText = (AutoCompleteTextView) viewModificaElemento.findViewById(R.id.EditTextTitoloPrincipaleElemento);
        titoloPrincipaleElementoEditText.append(elementoDaModificare.getDenominazionePrincipale());

        titoloSecondarioElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.EditTextTitoloSecondarioElemento);

        descrizionePrincipaleElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.editTexDescrizionePrincipaleElemento);
        descrizionePrincipaleElementoEditText.append(elementoDaModificare.getDescrizionePrincipale());

        descrizioneSecondariaElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.editTextDescrizioneSecondariaElemento);
        bottoneModificaFoto = viewModificaElemento.findViewById(R.id.imageButtonSceltaFotoElemento);
        logoElemento = viewModificaElemento.findViewById(R.id.iconaLogoElemento);
        if (fileImmagine != null) {
            Bitmap bitmapLogo  = BitmapFactory.decodeFile(fileImmagine.getAbsolutePath());
            logoElemento.setImageBitmap(bitmapLogo);
        }

        if(!ristoranteCorrente.isTuristico()){
            titoloSecondarioElementoEditText.setEnabled(false);
            titoloSecondarioElementoEditText.setHint("Per impostare un titolo secondario, rendere il ristorante turistico");
            descrizioneSecondariaElementoEditText.setEnabled(false);
            descrizioneSecondariaElementoEditText.setHint("Per impostare una descrizione secondaria, rendere il ristorante turistico");
        } else {
            titoloSecondarioElementoEditText.setEnabled(true);
            descrizioneSecondariaElementoEditText.setEnabled(true);
            titoloSecondarioElementoEditText.append(elementoDaModificare.getDenominazioneSecondaria());
            descrizioneSecondariaElementoEditText.append(elementoDaModificare.getDescrizioneSecondaria());
        }

        prezzoElementoEditText = (EditText) viewModificaElemento.findViewById(R.id.editTextCostoElemento);
        prezzoElementoEditText.append(String.valueOf(elementoDaModificare.getCosto()));

        sezioneElementoSpinner = (Spinner) viewModificaElemento.findViewById(R.id.spinnerSezioneElemento);

        ArrayAdapter<SezioneMenu> adapterSpinner = new ArrayAdapter<SezioneMenu>(getContext(), R.layout.spinner_layout, listaSezioni);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_item_layout);
        sezioneElementoSpinner.setAdapter(adapterSpinner);

        sezioneElementoSpinner.setSelection(adapterSpinner.getPosition(elementoDaModificare.getAppartiene()));

        for (CheckBox checkBox : checkBoxAllergeni) {
            for (Allergene allergene : elementoDaModificare.getPresenta()) {
                if (allergene.getNome().equals(checkBox.getTag()))
                    checkBox.setChecked(true);
            }
        }

        bottoneConferma = (Button) viewModificaElemento.findViewById(R.id.bottoneAggiungiModificaElemento);
        bottoneConferma.setText("Modifica");

        bottoneAnnulla = (Button) viewModificaElemento.findViewById(R.id.bottoneAnnullaElemento);


        titoloPrincipaleElementoEditText.setAdapter(adapterAutoComplete);

        titoloPrincipaleElementoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (titoloPrincipaleElementoEditText.getText().toString().length() >= titoloPrincipaleElementoEditText.getThreshold()) {
                    PresenterMenu.getInstance().settaElementiDaIniziale(MenuFragment.this, titoloPrincipaleElementoEditText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapterAutoComplete.clear();
            }
        });

        titoloPrincipaleElementoEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Elemento elementoScelto = adapterAutoComplete.getItem(i);
                descrizionePrincipaleElementoEditText.setText(elementoScelto.getDescrizionePrincipale());
                for (CheckBox checkBox : checkBoxAllergeni) {
                    for (Allergene allergene : elementoScelto.getPresenta()){
                        if (allergene.getNome().equals(checkBox.getTag()))
                            checkBox.setChecked(true);
                    }
                }

            }
        });

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementoDaModificare.setDenominazionePrincipale(titoloPrincipaleElementoEditText.getText().toString());
                elementoDaModificare.setDescrizionePrincipale(descrizionePrincipaleElementoEditText.getText().toString());
                if (ristoranteCorrente.isTuristico()) {
                    elementoDaModificare.setDenominazioneSecondaria(titoloSecondarioElementoEditText.getText().toString());
                    elementoDaModificare.setDescrizioneSecondaria(descrizioneSecondariaElementoEditText.getText().toString());
                }
                elementoDaModificare.setCosto(Double.parseDouble(prezzoElementoEditText.getText().toString()));
                elementoDaModificare.setAppartiene((SezioneMenu) sezioneElementoSpinner.getSelectedItem());

                ArrayList<Allergene> allergeniPiattoCorrente = new ArrayList<>();
                for (CheckBox checkBox : checkBoxAllergeni) {
                    if (checkBox.isChecked()) {
                        allergeniPiattoCorrente.add(new Allergene((listaAllergeni) checkBox.getTag()));
                    }
                }
                elementoDaModificare.setPresenta(allergeniPiattoCorrente);

                if (fotoModificata) {

                    InputStream streamLogo = null;
                    try {
                        streamLogo = getActivity().getContentResolver().openInputStream(uriLogoCorrente);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    uploadS3Modifica(streamLogo, uriLogoCorrente, elementoDaModificare);
                    fotoModificata = false;

                }
                else
                    PresenterMenu.getInstance().modificaElemento(elementoDaModificare, elementoDaModificare.getPresenta(),MenuFragment.this);
            }
        });

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogElemento.dismiss();
            }
        });

        bottoneModificaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        dialogElemento = builderDialogElemento.create();
        dialogElemento.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogElemento.show();
    }

    private void uploadS3Modifica(InputStream streamLogo, Uri uriLogoCorrente, Elemento elemento) {
        Amplify.Storage.uploadInputStream(
                ((Integer)elemento.getIdElemento()).toString()+"_LogoElemento.jpg",
                streamLogo,
                result -> {
                    PresenterMenu.getInstance().modificaElemento(elemento, elemento.getPresenta(),MenuFragment.this);
                } ,
                storageFailure -> PresenterRistorante.getInstance().mostraAlert(MenuFragment.this.getContext(), "Errore!", "L'immagine non è stata caricata correttamente, riprovare")
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //error
                return;
            }

            uriLogoCorrente = data.getData();
            logoElemento.setImageURI(uriLogoCorrente);
            fotoModificata = true;
        }
    }

    @Override
    public void onVediIngredientiElementoClicked(Elemento elemento, View view) {
        Intent i = new Intent(getContext(), VisualizzazioneIngredientiElementoActivity.class);
        i.putExtra("Elemento selezionato", (Serializable) elemento);
        i.putExtra("Preparazione",(Serializable)elemento.getPreparazione());
        startActivity(i);
    }

    private void selezionaDeseleziona(Elemento elementoCliccato, View itemView) {
        CardView cardElemento = itemView.findViewById(R.id.cardElementoMenu);
        if (listaElementiSelezionati.contains(elementoCliccato)) {
            listaElementiSelezionati.remove(elementoCliccato);
            listaCardElementiSelezionati.remove(cardElemento);
            cardElemento.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            listaElementiSelezionati.add(elementoCliccato);
            listaCardElementiSelezionati.add(cardElemento);
            cardElemento.setBackgroundColor(Color.parseColor("#F4B851"));
        }
    }

    @Override
    public void onStop() {
        modalitaEliminazione = true;
        attivaDisattivaModalitaEliminazione();
        bloccaOrdineMenu();
        super.onStop();
    }

    private void bloccaOrdineMenu() {
        for (SezioneMenu sezione : listaSezioni) {
            sezione.setPosizione(listaSezioni.indexOf(sezione));
            for (Elemento elemento : sezione.getAppartenente())
                elemento.setPosizione(sezione.getAppartenente().indexOf(elemento));
        }
        PresenterMenu.getInstance().settaPosizioniMenu(this, listaSezioni);
    }

    private void deselezionaTuttiElementi() {
        listaElementiSelezionati.clear();
        for (CardView card : listaCardElementiSelezionati) card.setBackgroundColor(Color.parseColor("#FFFFFF"));
        listaCardElementiSelezionati.clear();
    }


    public void setListaSezioni(ArrayList<SezioneMenu> listaSezioni)
    {
        for (SezioneMenu sezione : listaSezioni)
            sezione.setRistorante(ristoranteCorrente);

        this.listaSezioni.clear();
        this.listaSezioni.addAll(listaSezioni);
        adapterSezioni.notifyDataSetChanged();
    }


    public void setRistoranteCorrente(Ristorante ristorante) {
        ristoranteCorrente = ristorante;
    }

    public void setupListaElementiOpenFoodFacts(ArrayList<Elemento> listaElementiOttenuta) {
        adapterAutoComplete.addAll(listaElementiOttenuta);
        adapterAutoComplete.getFilter().filter(null);
    }

    public void aggiornaMenu() {
        PresenterMenu.getInstance().estraiMenu(this, ristoranteCorrente);
    }

    public void elementoAggiuntoCorrettamente(Elemento elemento) {

        if (fotoModificata) {
            InputStream streamLogo = null;
            try {
                streamLogo = getActivity().getContentResolver().openInputStream(uriLogoCorrente);
            } catch (FileNotFoundException e) {
                Log.i("FILE NOT FOUND", "");
                e.printStackTrace();
            }
            Log.i("PROVA", "");
            fotoModificata = false;
            uploadS3Aggiunta(streamLogo, uriLogoCorrente, elemento);
        }
        else {
            dialogElemento.dismiss();
            PresenterMenu.getInstance().mostraAlert(getContext(), "Piatto aggiunto", "Piatto aggiunto correttamente al menù");
            PresenterMenu.getInstance().estraiMenu(this, ristoranteCorrente);
        }

    }

    public void elementoModificato() {
        dialogElemento.dismiss();
        PresenterMenu.getInstance().mostraAlert(getContext(), "Piatto modificato!", "Il piatto e' stato correttamente aggiornato.");
        PresenterMenu.getInstance().estraiMenu(this, ristoranteCorrente);
    }

    public void setListaSezioniPerAggiungereNuovoLogo(ArrayList<SezioneMenu> listaSezioni, Elemento elemento) {
        setListaSezioni(listaSezioni);
        SezioneMenu sezioneNellaLista = listaSezioni.get(elemento.getAppartiene().getPosizione());
        Elemento elementoConId = sezioneNellaLista.getAppartenente().get(elemento.getPosizione());



    }

    private void uploadS3Aggiunta(InputStream streamLogo, Uri uriLogoCorrente, Elemento elemento) {
        Amplify.Storage.uploadInputStream(
                ((Integer)elemento.getIdElemento()).toString()+"_LogoElemento.jpg",
                streamLogo,
                result -> {
                    Log.i("SUCCESSO", "");
                    dialogElemento.dismiss();
                    PresenterMenu.getInstance().mostraAlert(getContext(), "Piatto aggiunto", "Piatto aggiunto correttamente al menù");
                    PresenterMenu.getInstance().estraiMenu(this, ristoranteCorrente);
                } ,
                storageFailure -> {
                    Log.i("FALLIMENTO", "");
                    dialogElemento.dismiss();
                    PresenterRistorante.getInstance().mostraAlert(MenuFragment.this.getContext(), "Errore!", "Il piatto è stato aggiunto ma l'immagine non è stata caricata correttamente!");
                    PresenterMenu.getInstance().estraiMenu(this, ristoranteCorrente);
                }
        );

    }
}