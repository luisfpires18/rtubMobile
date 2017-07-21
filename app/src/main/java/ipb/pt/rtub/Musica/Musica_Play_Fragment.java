package ipb.pt.rtub.Musica;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.codec.binary.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import entities.Album;
import entities.Musica;
import ipb.pt.rtub.R;
import models.AlbumRestClient;
import models.MusicaRestClient;

import static org.apache.commons.codec.binary.Base64.encodeBase64;


public class Musica_Play_Fragment extends Fragment  implements MediaPlayer.OnPreparedListener{

    private byte[] musica;


    public Musica_Play_Fragment() {

    }
//
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        musica = (bundle.getByteArray("musica"));

        getActivity().setTitle("Ouvir");

//        // OPTION 1;
//        String s = encodeBase64String(musica);
//        String url = "data:audio/mp3;base64,"+ s;
//
//        Uri uri = Uri.parse(url);
//        MediaPlayer mp = new MediaPlayer();
//        try {
//           // mp.setDataSource(url);
//            mp.setDataSource(getContext(), uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("EXCEPTION");
//        }
//        mp.setOnPreparedListener(this);
//        mp.prepareAsync();


        // OPTION 2;
//        File outputFile = null;
//        try {
//            outputFile = File.createTempFile("file", "mp3", getContext().getCacheDir());
//            outputFile.deleteOnExit();
//            FileOutputStream fileoutputstream = new FileOutputStream(outputFile);
//            fileoutputstream.write(musica);
//            fileoutputstream.close();
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        MediaPlayer mp = new MediaPlayer();
//        try {
//            // mp.setDataSource(url);
//
//            Uri uri = Uri.fromFile(outputFile);
//            String path = outputFile.getAbsolutePath();
//
//            mp.setDataSource(getContext(), uri);
//            //mp.setDataSource(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("EXCEPTION");
//        }
//        mp.setOnPreparedListener(this);
//        mp.prepareAsync();




    }




    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.musica_play, container, false);
    }

    public static String encodeBase64String(final byte[] binaryData) {
        return StringUtils.newStringUtf8(encodeBase64(binaryData, false));
    }


}
