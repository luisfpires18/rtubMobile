package models;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import entities.Membro;

public class MembroRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/membro/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Membro find(int id) {
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

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject object = new JSONObject(finalJson);
            Membro m = new Membro();
            m.setId(id);
            m.setName(object.getString("name"));
            m.setNomeTuna(object.getString("nomeTuna"));
            m.setCategoria(object.getString("categoria"));
            m.setTelefone(object.getString("telefone"));
            m.setEmail(object.getString("email"));
            m.setAdmin(object.getBoolean("isAdmin"));

            return m;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // GET
    public List<Membro> findAll() {
        try {
            return restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Membro>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // GET ALL ARRAY
    public ArrayList<Membro> getArrayMembros() {
        try {
            List<Membro> membros = restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Membro>>() {}).getBody();
            ArrayList<Membro> array = new ArrayList<>();

            for(int k = 0; k < membros.size(); k++){
                array.add(membros.get(k));
            }

            return array;
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createMembro(Membro m) {
        return restTemplate.postForLocation(BASE_URL, m, Membro.class);
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

    // PUT
        public Membro updateMembro(Membro m) {
            restTemplate.put(BASE_URL + m.getId(), m);
            return m;
        }
}