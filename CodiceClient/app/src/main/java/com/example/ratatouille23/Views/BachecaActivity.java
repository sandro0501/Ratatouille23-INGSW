package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;
import com.example.ratatouille23.Presenters.PresenterBacheca;
import com.example.ratatouille23.R;
import com.google.android.material.navigation.NavigationView;


public class BachecaActivity extends AppCompatActivity {

    private DrawerLayout menuDrawerLayout;
    private ImageView iconaMenu;
    private NavigationView menu;
    private TextView textViewNomeRistorante;
    private Ristorante ristoranteCorrente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

        Utente utenteCorrente = (Utente)getIntent().getSerializableExtra("Utente");
        ristoranteCorrente = utenteCorrente.getIdRistorante();

        textViewNomeRistorante = findViewById(R.id.textViewDenominazioneRistorante);
        menuDrawerLayout = findViewById(R.id.menuDrawerLayout);
        iconaMenu = findViewById(R.id.menuIcon);
        menu = findViewById(R.id.menuNavigationView);

        textViewNomeRistorante.setText(ristoranteCorrente.getDenominazione());

        inizializzaMenu();
        iconaMenuPremuta(menuDrawerLayout, iconaMenu);


    }


    @Override
    public void onBackPressed() {
        if (PresenterBacheca.getInstance().getBachecaAttiva()) { }
        else super.onBackPressed();

    }

    private void inizializzaMenu() {
        NavHostFragment hostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController controllerMenu = hostFragment.getNavController();
        NavigationUI.setupWithNavController(menu, controllerMenu);
        Utente u = UtenteFactory.getInstance().getNuovoUtente("Genoveffa", "Arcobaleno", "emailprova", "Amministratore", true);

        //simulazione login utente
            if (u.getRuoloUtente().equals("Addetto alla cucina")){
                MenuItem itemMenuGestioneRistorante = menu.getMenu().findItem(R.id.opzioneMenuGestisciRistorante);
                itemMenuGestioneRistorante.setEnabled(false);
                itemMenuGestioneRistorante.setVisible(false);

                MenuItem itemMenuRegistraDipendente = menu.getMenu().findItem(R.id.opzioneMenuGestisciDipendenti);
                itemMenuRegistraDipendente.setEnabled(false);
                itemMenuRegistraDipendente.setVisible(false);

                MenuItem itemMenuRegistraOrdini = menu.getMenu().findItem(R.id.opzioneMenuRegistraOrdini);
                itemMenuRegistraOrdini.setEnabled(false);
                itemMenuRegistraOrdini.setVisible(false);
            }
            else if (u.getRuoloUtente().equals("Addetto al servizio")){
                MenuItem itemMenuGestioneRistorante = menu.getMenu().findItem(R.id.opzioneMenuGestisciRistorante);
                itemMenuGestioneRistorante.setEnabled(false);
                itemMenuGestioneRistorante.setVisible(false);

                MenuItem itemMenuRegistraDipendente = menu.getMenu().findItem(R.id.opzioneMenuGestisciDipendenti);
                itemMenuRegistraDipendente.setEnabled(false);
                itemMenuRegistraDipendente.setVisible(false);

                MenuItem itemMenuGestisciInventario = menu.getMenu().findItem(R.id.opzioneMenuGestisciInventario);
                itemMenuGestisciInventario.setEnabled(false);
                itemMenuGestisciInventario.setVisible(false);
            }
            else if (u.getRuoloUtente().equals("Amministratore")){
                MenuItem itemMenuGestisciInventario = menu.getMenu().findItem(R.id.opzioneMenuGestisciInventario);
                itemMenuGestisciInventario.setEnabled(false);
                itemMenuGestisciInventario.setVisible(false);

                MenuItem itemMenuRegistraOrdini = menu.getMenu().findItem(R.id.opzioneMenuRegistraOrdini);
                itemMenuRegistraOrdini.setEnabled(false);
                itemMenuRegistraOrdini.setVisible(false);

            }
            else {
                MenuItem itemMenuRegistraOrdini = menu.getMenu().findItem(R.id.opzioneMenuRegistraOrdini);
                itemMenuRegistraOrdini.setEnabled(false);
                itemMenuRegistraOrdini.setVisible(false);

                MenuItem itemMenuGestioneRistorante = menu.getMenu().findItem(R.id.opzioneMenuGestisciRistorante);
                itemMenuGestioneRistorante.setEnabled(false);
                itemMenuGestioneRistorante.setVisible(false);

                MenuItem itemMenuRegistraDipendente = menu.getMenu().findItem(R.id.opzioneMenuGestisciDipendenti);
                itemMenuRegistraDipendente.setEnabled(false);
                itemMenuRegistraDipendente.setVisible(false);
            }
    }

    private void iconaMenuPremuta(DrawerLayout menuDrawerLayout, ImageView iconaMenu) {
        iconaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

}