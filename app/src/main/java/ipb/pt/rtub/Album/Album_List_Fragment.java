package ipb.pt.rtub.Album;


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

import java.text.SimpleDateFormat;
import java.util.List;

import adapters.AlbumListAdapter;
import entities.Album;
import ipb.pt.rtub.Musica.Musica_List_Fragment;
import ipb.pt.rtub.R;
import models.AlbumRestClient;

public class Album_List_Fragment extends Fragment {

    private Button buttonNew;
    private boolean isAdmin = false;

    public Album_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Albums");
        new HttpRequestAsk().execute();

        Bundle bundle = getArguments();
        if(bundle != null){
            isAdmin = (bundle.getBoolean("isAdmin"));
        }


        if(isAdmin){
            buttonNew = (Button) getView().findViewById(R.id.buttonNovoAlbum);
            buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonNovoAlbum:
                            fragment = new Album_Create_Fragment();
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
            buttonNew = (Button) getView().findViewById(R.id.buttonNovoAlbum);
            buttonNew.setVisibility(View.INVISIBLE);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Album>>{

        @Override
        protected List<Album> doInBackground(Void... params) {
            AlbumRestClient albumRestClient = new AlbumRestClient();
            return albumRestClient.getAll();
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Album> albums) {
            final ListView listViewAlbum = (ListView) getView().findViewById(R.id.listViewAlbum);
            ArrayAdapter<Album> adapter = new AlbumListAdapter(getActivity(), albums);

            listViewAlbum.setAdapter(adapter);



            listViewAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Album album = albums.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewAlbum:
                            fragment = new Musica_List_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idAlbum", album.getId());
                        bundle.putString("nomeAlbum", album.getNomeAlbum());
                        bundle.putInt("releaseDate", album.getReleaseDate());

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
        return inflater.inflate(R.layout.album_list, container, false);
    }

}
