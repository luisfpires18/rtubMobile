package ipb.pt.rtub.Musica;


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

import java.util.List;

import adapters.MusicaListAdapter;
import entities.Musica;
import ipb.pt.rtub.Album.Album_Delete_Fragment;
import ipb.pt.rtub.Album.Album_Update_Fragment;
import ipb.pt.rtub.R;
import models.MusicaRestClient;

public class Musica_List_Fragment extends Fragment {

    private Button buttonAlterar, buttonApagar;
    private int idAlbum, releaseDate;
    private String nomeAlbum;
    private boolean isAdmin;

    public Musica_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        idAlbum = (bundle.getInt("idAlbum"));
        nomeAlbum = (bundle.getString("nomeAlbum"));
        releaseDate = (bundle.getInt("releaseDate"));

        isAdmin = (bundle.getBoolean("isAdmin"));

        getActivity().setTitle("Musicas do Album " + nomeAlbum);
        new HttpRequestAsk().execute();


        buttonAlterar = (Button) getView().findViewById(R.id.buttonAlterarAlbum);
        buttonApagar = (Button) getView().findViewById(R.id.buttonApagarAlbum);
        if(isAdmin){
            buttonAlterar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonAlterarAlbum:
                            fragment = new Album_Update_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();

                        bundle.putInt("idAlbum", idAlbum);
                        bundle.putString("nomeAlbum", nomeAlbum);
                        bundle.putInt("releaseDate", releaseDate);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
            buttonApagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonApagarAlbum:
                            fragment = new Album_Delete_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idAlbum", idAlbum);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonAlterar.setVisibility(View.GONE);
            buttonApagar.setVisibility(View.GONE);
        }

    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Musica>>{

        @Override
        protected List<Musica> doInBackground(Void... params) {
            MusicaRestClient musicaRestClient = new MusicaRestClient();
            List<Musica> musicas = musicaRestClient.getMusicas(idAlbum);
            return musicas;
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Musica> musicas) {
            final ListView listViewMusica = (ListView) getView().findViewById(R.id.listViewMusica);
            ArrayAdapter<Musica> adapter = new MusicaListAdapter(getActivity(), musicas);

            listViewMusica.setAdapter(adapter);

            listViewMusica.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Musica musica = musicas.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewMusica:
                            fragment = new Musica_Details_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idMusica", musica.getId());
                        bundle.putInt("idAlbum", musica.getIdAlbum());
                        bundle.putString("titulo", musica.getTitulo());
                        bundle.putByteArray("musica", musica.getMusica());
                        bundle.putBoolean("isAdmin", isAdmin);

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
        return inflater.inflate(R.layout.musica_list, container, false);
    }

}
