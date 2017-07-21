package ipb.pt.rtub.Evento;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import adapters.EventoListAdapter;
import entities.Evento;
import ipb.pt.rtub.AboutFragment;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.EventoRestClient;

public class Evento_List_Fragment extends Fragment {

    private Button buttonNew, buttonHistEventos;
    private boolean isAdmin = false, isMember = false;
    private String username;
    private int idMembro;

    public Evento_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Eventos");
        new HttpRequestAsk().execute();

        Bundle bundle = getArguments();
        if(bundle != null){
            isAdmin = (bundle.getBoolean("isAdmin"));
            isMember = (bundle.getBoolean("isMember"));
            username = (bundle.getString("username"));
            idMembro = (bundle.getInt("id"));
        }


        buttonHistEventos = (Button) getView().findViewById(R.id.buttonHistEventos);
        buttonHistEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.buttonHistEventos:
                        fragment = new EventosAnteriores_List_Fragment();
                        break;
                }
                if(fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isAdmin", isAdmin);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });

        if(isAdmin){
            buttonNew = (Button) getView().findViewById(R.id.buttonNovoEvento);
            buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonNovoEvento:
                            fragment = new Evento_Create_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonNew = (Button) getView().findViewById(R.id.buttonNovoEvento);
            buttonNew.setVisibility(View.INVISIBLE);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Evento>>{

        @Override
        protected List<Evento> doInBackground(Void... params) {
            EventoRestClient eventoRestClient = new EventoRestClient();
            return eventoRestClient.proximosEventos();
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Evento> eventos) {
            final ListView listViewEvento = (ListView) getView().findViewById(R.id.listViewEvento);
            ArrayAdapter<Evento> adapter = new EventoListAdapter(getActivity(), eventos);

            listViewEvento.setAdapter(adapter);



            listViewEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Evento evento = eventos.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewEvento:
                            fragment = new Evento_Details_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", evento.getId());
                        bundle.putInt("userLogged", idMembro);
                        bundle.putString("nome", evento.getNome());
                        bundle.putString("local", evento.getLocal());
                        bundle.putString("tipo", evento.getTipo());
                        bundle.putBoolean("isAdmin", isAdmin);
                        bundle.putBoolean("isMember", isMember);
                        bundle.putString("username", username);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String d = formatter.format(evento.getData());
                        bundle.putString("data", d);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }

            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evento_list, container, false);
    }

}
