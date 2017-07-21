package ipb.pt.rtub.Album;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import entities.Album;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.AlbumRestClient;


public class Album_Update_Fragment extends Fragment {

    private Button buttonUpdate;
    private int idAlbum, releaseDate;
    private String nomeAlbum;
    private EditText editTextNome, editTextAno;

    public Album_Update_Fragment() {
        
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = getArguments();
        idAlbum = (bundle.getInt("idAlbum"));
        nomeAlbum = (bundle.getString("nomeAlbum"));
        releaseDate = (bundle.getInt("releaseDate"));

        getActivity().setTitle("Alterar Album" + nomeAlbum);

        TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
        textViewId.setText(String.valueOf(idAlbum));

        editTextNome = (EditText) getView().findViewById(R.id.editTextNome);
        editTextNome.setText(nomeAlbum);

        editTextAno = (EditText) getView().findViewById(R.id.editTextAno);
        editTextAno.setText(String.valueOf(releaseDate));


        buttonUpdate = (Button) getView().findViewById(R.id.updateConfirmAlbum);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Album a = new Album();
                a.setId(idAlbum);
                a.setNomeAlbum(editTextNome.getText().toString());
                a.setReleaseDate(Integer.parseInt(editTextAno.getText().toString()));

                new HttpRequestAsk(a).execute();
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.updateConfirmAlbum:
                        fragment = new StartFragment();
                        break;
                }
                if(fragment != null) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Album> {

        private Album a;

        public HttpRequestAsk(Album album){
            this.a = album;
        }

        @Override
        protected Album doInBackground(Void... params) {
            AlbumRestClient albumRestClient = new AlbumRestClient();
            return albumRestClient.updateAlbum(a);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.album_update, container, false);
    }

}
