package models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import entities.Premio;

public class PremioRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/premio/";
    private RestTemplate restTemplate = new RestTemplate();

    // GET
    public List<Premio> findPremios(int id) {
        try {
            return restTemplate.exchange(BASE_URL + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Premio>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createPremio(Premio p) {
        return restTemplate.postForLocation(BASE_URL, p, Premio.class);
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