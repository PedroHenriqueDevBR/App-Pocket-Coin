package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.droppages.pedrohenrique.pocketcoin.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar                 toolbar;
    private DrawerLayout            drawer;
    private ActionBarDrawerToggle   toggle;
    private NavigationView          navigationView;
    private FrameLayout             frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind
        toolbar     = findViewById(R.id.toolbar);
        drawer      = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frame_layout);

        // Toolbar
        setSupportActionBar(toolbar);

        // AppBar
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Menu navegação
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Inicia fragmento
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Home - página inicial
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        } else if (id == R.id.nav_registro) {
            // Registro - listar log de registro
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new LogFragment()).commit();
        } else if (id == R.id.nav_configuracao) {
            // Configuração - define comportamento do app
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ConfiguracaoFragment()).commit();
        } else if (id == R.id.nav_carteira) {
            // Atalho - tela de gerencia de carteiras
            abrirActivityCarteiras();
        } else if (id == R.id.nav_categoria) {
            // Atalho - tela de gerencia de categorias
            abrirActivityCategoria();
        } else if (id == R.id.nav_tag) {
            // Atalho - tela de gerencia de tags
            abrirActivityTag();
        } else if (id == R.id.nav_sair) {
            // Sair - Finaliza o app (Sessão continua segura)
            sair();
        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void abrirActivityCarteiras(){
        startActivity(new Intent(this, CarteiraActivity.class));
    }

    private void abrirActivityCategoria(){
        startActivity(new Intent(this, CategoriaActivity.class));
    }

    private void abrirActivityTag(){
        startActivity(new Intent(this, TagActivity.class));
    }

    public void sair(){ this.finish(); }
}
