package models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import entities.Instrumento;

public class InstrumentoRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/instrumento/";
    //private String BASE_URL = "http://127.0.0.1:8082/api/instrumento/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Instrumento find(int id) {
        try{
            return restTemplate.getForObject(BASE_URL + id, Instrumento.class);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    // GET
    // Not usable;
    public List<Instrumento> findAll() {
        try {
            return restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Instrumento>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Instrumento> findInstrumentos(int idEvento) {
        try {
            return restTemplate.exchange("http://10.0.2.2:8082/api/instrumentos/" + idEvento,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Instrumento>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createInstrumento(Instrumento i) {
        return restTemplate.postForLocation(BASE_URL, i, Instrumento.class);
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
    public Instrumento updateInstrumento(Instrumento i) {
        restTemplate.put(BASE_URL + i.getId(), i);
        return i;
    }
}