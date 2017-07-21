package ipb.pt.rtub.Evento;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import entities.Evento;
import entities.Membro;
import ipb.pt.rtub.Inscricao.Inscricao_Create_Fragment;
import ipb.pt.rtub.Inscricao.Inscricao_List_Fragment;
import ipb.pt.rtub.Instrumento.Instrumento_List_Fragment;
import ipb.pt.rtub.R;
import models.EventoRestClient;
import models.MembroRestClient;

public class Evento_Details_Fragment extends Fragment {

    public Evento_Details_Fragment() {

    }

    private Button buttonDelete, buttonVerInscritos, buttonInscrever, buttonInstrumentos, buttonUpdate;
    private int id, userLogged;
    private String nome, local, tipo, data, username;
    private EventoRestClient eventoRestClient = new EventoRestClient();
    private boolean isAdmin, isMember;
    public ArrayList<Membro> membros;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        local = (bundle.getString("local"));
        tipo = (bundle.getString("tipo"));
        data = (bundle.getString("data"));
        isAdmin = (bundle.getBoolean("isAdmin"));
        isMember = (bundle.getBoolean("isMember"));
        username = (bundle.getString("username"));
        userLogged = (bundle.getInt("userLogged"));

        getActivity().setTitle(nome);

        AsyncTask<Void, Void, Evento> execute = new HttpRequestAsk(id).execute();

        try {
            execute.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        buttonUpdate = (Button) getView().findViewById(R.id.buttonUpdateEvento);
        buttonDelete = (Button) getView().findViewById(R.id.buttonDeleteEvento);
        if(isAdmin){
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonUpdateEvento:
                            fragment = new Evento_Update_Fragment();
                            break;
                    }
                    if (fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("nome", nome);
                        bundle.putString("local", local);
                        bundle.putString("tipo", tipo);
                        bundle.putString("data", data);


                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonDeleteEvento:
                            fragment = new Evento_Delete_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonUpdate.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        }

        buttonInscrever = (Button) getView().findViewById(R.id.buttonInscrever);
        buttonInstrumentos = (Button) getView().findViewById(R.id.buttonInstrumentos);
        buttonVerInscritos = (Button) getView().findViewById(R.id.buttonInscritos);

        if(isAdmin || isMember){
            buttonInscrever.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonInscrever:
                            fragment = new Inscricao_Create_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("nome", nome);

                        bundle.putBoolean("isAdmin", isAdmin);
                        bundle.putBoolean("isMember", isMember);
                        bundle.putInt("userLogged", userLogged);

                        bundle.putParcelableArrayList("membros", membros);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });

            buttonInstrumentos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonInstrumentos:
                            fragment = new Instrumento_List_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("nome", nome);
                        bundle.putBoolean("isAdmin", isAdmin);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });

            buttonVerInscritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonInscritos:
                            fragment = new Inscricao_List_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("nome", nome);
                        bundle.putBoolean("isAdmin", isAdmin);
                        bundle.putBoolean("isMember", isMember);
                        bundle.putString("username", username);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonInscrever.setVisibility(View.GONE);
            buttonInstrumentos.setVisibility(View.GONE);
            buttonVerInscritos.setVisibility(View.GONE);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Evento> {

        private int id;

        public HttpRequestAsk(int id) {
            this.id = id;
        }


        @Override
        protected Evento doInBackground(Void... params) {
            MembroRestClient membroRestClient = new MembroRestClient();
            membros = membroRestClient.getArrayMembros();
            return eventoRestClient.find(id);
        }

        @Override
        protected void onPostExecute(final Evento evento) {

            TextView labelId = (TextView) getView().findViewById(R.id.textView1);
            TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
            if(isAdmin){
                textViewId.setText(String.valueOf(evento.getId()));
            }else{
                labelId.setVisibility(View.GONE);
                textViewId.setVisibility(View.GONE);
            }

            TextView textViewNome = (TextView) getView().findViewById(R.id.textViewNome);
            textViewNome.setText(evento.getNome());

            TextView textViewLocal = (TextView) getView().findViewById(R.id.textViewLocal);
            textViewLocal.setText(evento.getLocal());

            TextView textViewTipo = (TextView) getView().findViewById(R.id.textViewTipo);
            textViewTipo.setText(evento.getTipo());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String data  = dateFormat.format(evento.getData());
            TextView textViewData = (TextView) getView().findViewById(R.id.textViewData);
            textViewData.setText(data);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evento_details, container, false);
    }

}
