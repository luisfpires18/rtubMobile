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

import entities.Album;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.AlbumRestClient;


public class Album_Create_Fragment extends Fragment {


    private Button buttonCreate;
    private EditText editTextNome, editTextAno;

    public Album_Create_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Criar Album");

        buttonCreate = (Button) getView().findViewById(R.id.createButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNome = (EditText) getView().findViewById(R.id.editTextNome);
                editTextAno = (EditText) getView().findViewById(R.id.editTextAno);

                Album a = new Album();
                a.setNomeAlbum(editTextNome.getText().toString());
                a.setReleaseDate(Integer.parseInt(editTextAno.getText().toString()));

                new HttpRequestAsk(a).execute();

                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.createButton:
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
            albumRestClient.createAlbum(a);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.album_create, container, false);
    }

}
