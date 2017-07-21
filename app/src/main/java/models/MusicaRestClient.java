package models;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import entities.Musica;

public class MusicaRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/musica/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Musica find(int id) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(BASE_URL + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null ){
                buffer.append(line);
            }

            String finalJson = buffer.toString();


            JSONObject parentObject = new JSONObject(finalJson);

                Musica m = new Musica();
                m.setId(parentObject.getInt("id"));
                m.setFaixa(parentObject.getInt("faixa"));
                m.setTitulo(parentObject.getString("titulo"));
                m.setInterprete(parentObject.getString("interprete"));
                m.setIdAlbum(parentObject.getInt("albumId"));

                String music = parentObject.getString("musica");
                m.setMusica(music.getBytes());



            return m;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            e.getMessage();
        } finally{
            if(connection != null){
                connection.disconnect();
            }
            try {
                if(reader != null){
                    reader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Musica> getMusicas(int idAlbum) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("http://10.0.2.2:8082/api/musicas/" + idAlbum);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null ){
                buffer.append(line);
            }

            String finalJson = buffer.toString();


            JSONArray parentArray = new JSONArray(finalJson);


            List<Musica> musicas = new ArrayList<>();

            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject parentObject = parentArray.getJSONObject(i);
                Musica m = new Musica();
                m.setId(parentObject.getInt("id"));
                m.setFaixa(parentObject.getInt("faixa"));
                m.setTitulo(parentObject.getString("titulo"));
                m.setInterprete(parentObject.getString("interprete"));
                m.setIdAlbum(parentObject.getInt("albumId"));

                String music = parentObject.getString("musica");
                m.setMusica(music.getBytes());

                musicas.add(m);
            }

            return musicas;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            e.getMessage();
        } finally{
            if(connection != null){
                connection.disconnect();
            }
            try {
                if(reader != null){
                    reader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    // DELETE
    public void delete(int id){
        try{
            restTemplate.delete(BASE_URL + id);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }
}