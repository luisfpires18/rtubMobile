package ipb.pt.rtub;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ipb.pt.rtub.Album.Album_List_Fragment;
import ipb.pt.rtub.Evento.Evento_Create_Fragment;
import ipb.pt.rtub.Evento.Evento_List_Fragment;
import ipb.pt.rtub.Evento.EventosAnteriores_List_Fragment;
import ipb.pt.rtub.Membro.Editar_Perfil_Fragment;
import ipb.pt.rtub.Membro.Membro_Create_Fragment;
import ipb.pt.rtub.Membro.Membro_List_Fragment;
import ipb.pt.rtub.Pedido.Pedido_Create_Fragment;
import ipb.pt.rtub.Pedido.Pedido_List_Fragment;
import models.MembroRestClient;

public class MemberActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int idMembro;
    private String username, nome, nomeTuna, categoria, telefone;
    private boolean isAdmin, isMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        isAdmin = (bundle.getBoolean("isAdmin"));
        isMember = (bundle.getBoolean("isMember"));
        username = (bundle.getString("username"));

        idMembro = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        nomeTuna = (bundle.getString("nomeTuna"));
        categoria = (bundle.getString("categoria"));
        telefone = (bundle.getString("telefone"));

        View v =  navigationView.getHeaderView(0);
        TextView textViewNome = (TextView) v.findViewById(R.id.perfilNomeTuna);
        textViewNome.setText(nomeTuna);


        displaySelectedScreen(R.id.nav_start);
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

    private void displaySelectedScreen(int id) {

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_start:
                fragment = new StartFragment();
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_events:
                fragment = new Evento_List_Fragment();
                break;
            case R.id.nav_music:
                fragment = new Album_List_Fragment();
                break;
            case R.id.nav_members:
                fragment = new Membro_List_Fragment();
                break;
            case R.id.nav_edit:
                fragment = new Editar_Perfil_Fragment();
                break;
            case R.id.nav_logout:
                fragment = new Membro_List_Fragment();
                Intent intent = new Intent(MemberActivity.this, MainActivity.class);
                MemberActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAdmin", isAdmin);
            bundle.putBoolean("isMember", isMember);
            bundle.putString("username", username);
            bundle.putInt("id", idMembro);
            bundle.putString("nome", nome);
            bundle.putString("nomeTuna", nomeTuna);
            bundle.putString("categoria", categoria);
            bundle.putString("telefone", telefone);
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }


}
