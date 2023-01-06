package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ratatouille23.Controller;
import com.example.ratatouille23.R;
import com.google.android.material.navigation.NavigationView;


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