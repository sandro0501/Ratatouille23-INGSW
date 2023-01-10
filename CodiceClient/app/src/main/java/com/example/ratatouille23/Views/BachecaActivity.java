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

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.Models.Addetto;
import com.example.ratatouille23.Models.Amministratore;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.ruoliPersonale;
import com.example.ratatouille23.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class BachecaActivity extends AppCompatActivity {

    private DrawerLayout menuDrawerLayout;
    private ImageView iconaMenu;
    private NavigationView menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

        menuDrawerLayout = findViewById(R.id.menuDrawerLayout);
        iconaMenu = findViewById(R.id.menuIcon);
        menu = findViewById(R.id.menuNavigationView);


        inizializzaMenu();
        iconaMenuPremuta(menuDrawerLayout, iconaMenu);


    }


    @Override
    public void onBackPressed() {
        if (Controller.getBachecaAttiva()) { }
        else super.onBackPressed();


    }

    private void inizializzaMenu() {
        NavHostFragment hostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController controllerMenu = hostFragment.getNavController();
        NavigationUI.setupWithNavController(menu, controllerMenu);

        //simulazione login utente
        utenti.add(u);
            if (utenteLoggato instanceof Addetto){
                if(((Addetto) utenteLoggato).getRuolo().equals(ruoliPersonale.addettoAllaCucina)){
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
                else if (((Addetto) utenteLoggato).getRuolo().equals(ruoliPersonale.addettoAlServizio)){
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
            }
            else if (utenteLoggato instanceof Amministratore){
                MenuItem itemMenuGestisciInventario = menu.getMenu().findItem(R.id.opzioneMenuGestisciInventario);
                itemMenuGestisciInventario.setEnabled(false);
                itemMenuGestisciInventario.setVisible(false);

                MenuItem itemMenuRegistraOrdini = menu.getMenu().findItem(R.id.opzioneMenuRegistraOrdini);
                itemMenuRegistraOrdini.setEnabled(false);
                itemMenuRegistraOrdini.setVisible(false);

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