package models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import entities.Evento;

public class EventoRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/evento/";
    //private String BASE_URL = "http://127.0.0.1:8082/api/evento/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Evento find(int id){
        try{
            return restTemplate.getForObject(BASE_URL + id, Evento.class);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    // GET
    public List<Evento> proximosEventos() {
        try {
            return restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Evento>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Evento> eventosAnteriores() {
        try {
            return restTemplate.exchange("http://10.0.2.2:8082/api/eventos/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Evento>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createEvento(Evento e) {
        return restTemplate.postForLocation(BASE_URL, e, Evento.class);
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
    public Evento updateEvento(Evento e) {
        restTemplate.put(BASE_URL + e.getId(), e);
        return e;
    }
}