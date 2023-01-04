package com.example.ratatouille23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class BachecaActivity extends AppCompatActivity {

    DrawerLayout menuDrawerLayout;
    ImageView iconaMenu;
    ImageView iconaProfiloUtente;
    NavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

        menuDrawerLayout = findViewById(R.id.menuDrawerLayout);
        iconaMenu = findViewById(R.id.menuIcon);
        iconaProfiloUtente = findViewById(R.id.userIcon);
        menu = findViewById(R.id.menuNavigationView);

        inizializzaMenu();
        iconaMenuPremuta(menuDrawerLayout, iconaMenu);
        iconaProfiloUtentePremuta(iconaProfiloUtente);


    }

    private void inizializzaMenu() {
        NavHostFragment hostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController controllerMenu = hostFragment.getNavController();
        NavigationUI.setupWithNavController(menu, controllerMenu);
    }

    private void iconaMenuPremuta(DrawerLayout menuDrawerLayout, ImageView iconaMenu) {
        iconaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void iconaProfiloUtentePremuta(ImageView iconaProfiloUtente) {
        iconaProfiloUtente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }
}