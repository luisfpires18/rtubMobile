package models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import entities.Album;

public class AlbumRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/album/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Album find(int id){
        try{
            return restTemplate.getForObject(BASE_URL + id, Album.class);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    // GET
    public List<Album> getAll() {
        try {
            return restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Album>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createAlbum(Album a) {
        return restTemplate.postForLocation(BASE_URL, a, Album.class);
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
    public Album updateAlbum(Album a) {
        restTemplate.put(BASE_URL + a.getId(), a);
        return a;
    }
}