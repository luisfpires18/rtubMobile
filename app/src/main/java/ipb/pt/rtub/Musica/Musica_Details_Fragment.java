package ipb.pt.rtub.Musica;


import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;

import Decoder.BASE64Encoder;
import entities.Album;
import entities.Musica;
import ipb.pt.rtub.R;
import models.AlbumRestClient;
import models.MusicaRestClient;

import static android.media.MediaPlayer.create;
import static org.apache.commons.codec.binary.Base64.encodeBase64;


public class Musica_Details_Fragment extends Fragment{

    private int idMusica, idAlbum;
    private String titulo, nomeAlbum;
    private byte[] musica;
    private boolean isAdmin, flag = false;

    private Button buttonApagar, buttonPlay;

    public Musica_Details_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        idMusica = (bundle.getInt("idMusica"));
        musica = (bundle.getByteArray("musica"));
        idAlbum = (bundle.getInt("idAlbum"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        getActivity().setTitle(titulo);

        new HttpRequestAsk(idMusica).execute();

        buttonApagar = (Button) getView().findViewById(R.id.buttonApagarMusica);
        if(isAdmin){
            buttonApagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonApagarMusica:
                            fragment = new Musica_Delete_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idMusica", idMusica);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonApagar.setVisibility(View.GONE);
        }

        //Não funciona;

        buttonPlay = (Button) getView().findViewById(R.id.buttonPlay);
        buttonPlay.setVisibility(View.GONE);
//        buttonPlay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Fragment fragment = null;
//
//                    switch (v.getId()) {
//                        case R.id.buttonPlay:
//                            fragment = new Musica_Play_Fragment();
//                            break;
//                    }
//                    if(fragment != null) {
//                        Bundle bundle = new Bundle();
//                        bundle.putByteArray("musica", musica);
//
//                        fragment.setArguments(bundle);
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.content_main, fragment);
//                        ft.commit();
//                    }
//                }
//            });

    }


    private class HttpRequestAsk extends AsyncTask<Void, Void, Musica> {

        private int id;

        public HttpRequestAsk(int id) {
            this.id = id;
        }


        @Override
        protected Musica doInBackground(Void... params) {
            MusicaRestClient musicaRestClient = new MusicaRestClient();
            Musica m = musicaRestClient.find(id);

            AlbumRestClient albumRestClient = new AlbumRestClient();
            Album a = albumRestClient.find(idAlbum);

            nomeAlbum = a.getNomeAlbum();
            return m;
        }

        @Override
        protected void onPostExecute(final Musica musica) {

            TextView labelId = (TextView) getView().findViewById(R.id.textView1);
            TextView textViewId = (TextView) getView().findViewById(R.id.textViewIdMusica);
            if (isAdmin) {
                textViewId.setText(String.valueOf(musica.getId()));
            } else {
                labelId.setVisibility(View.GONE);
                textViewId.setVisibility(View.GONE);
            }

            TextView textViewFaixa = (TextView) getView().findViewById(R.id.textViewFaixa);
            textViewFaixa.setText(String.valueOf(musica.getFaixa()));

            TextView textViewTitulo = (TextView) getView().findViewById(R.id.textViewTitulo);
            textViewTitulo.setText(musica.getTitulo());

            TextView textViewInterprete = (TextView) getView().findViewById(R.id.textViewInterprete);
            textViewInterprete.setText(musica.getInterprete());

            TextView textViewAlbum = (TextView) getView().findViewById(R.id.textViewAlbum);
            textViewAlbum.setText(nomeAlbum);

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.musica_details, container, false);
    }

    public static String encodeBase64String(final byte[] binaryData) {
        return StringUtils.newStringUtf8(encodeBase64(binaryData, false));
    }


}
