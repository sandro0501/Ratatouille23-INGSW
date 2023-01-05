package com.example.ratatouille23.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
<<<<<<< HEAD
=======
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
>>>>>>> 241d12eb80dd20c8aa393b3f52eeee8d436ac7ee
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ratatouille23.R;
import com.google.android.material.navigation.NavigationView;


public class BachecaActivity extends AppCompatActivity {

    DrawerLayout menuDrawerLayout;
    ImageView iconaMenu;
    NavigationView menu;

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

<<<<<<< HEAD
    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if(f instanceof BachecaFragment){}
        else super.onBackPressed();


    }
=======
>>>>>>> 241d12eb80dd20c8aa393b3f52eeee8d436ac7ee

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