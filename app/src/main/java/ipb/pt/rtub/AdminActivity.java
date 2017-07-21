package ipb.pt.rtub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ipb.pt.rtub.Album.Album_List_Fragment;
import ipb.pt.rtub.Evento.Evento_List_Fragment;
import ipb.pt.rtub.Evento.EventosAnteriores_List_Fragment;
import ipb.pt.rtub.Membro.Editar_Perfil_Fragment;
import ipb.pt.rtub.Membro.Membro_List_Fragment;
import ipb.pt.rtub.Pedido.Pedido_List_Fragment;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int idMembro;
    private String username, nome, nomeTuna, categoria, telefone;
    private boolean isAdmin, isMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_login) {
////            return true;
////        }
//
//        Fragment fragment = null;
//
//        switch (id) {
//            case R.id.action_login:
//                fragment = new LoginFragment();
//                break;
//        }
//
//        if(fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_main, fragment);
//            ft.commit();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void displaySelectedScreen(int id) {

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_start:
                fragment = new StartFragment();
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_requests:
                fragment = new Pedido_List_Fragment();
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
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                AdminActivity.this.startActivity(intent);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);



//        if (id == R.id.nav_start) {
//            Toast.makeText(this, "Inicio", Toast.LENGTH_LONG).show();
//        } else if (id == R.id.nav_news) {
//            Toast.makeText(this, "Noticias", Toast.LENGTH_LONG).show();
//        } else if (id == R.id.nav_events) {
//
//        } else if (id == R.id.nav_music) {
//
//        } else if(id == R.id.nav_contact) {
//
//        }
//
//        // PARTE DE PERFIL - ADMIN & MEMBROS
//        else if (id == R.id.nav_edit) {
//
//        } else if (id == R.id.nav_options) {
//
//        } else if(id == R.id.nav_logout) {

//        }



        return true;
    }


}
