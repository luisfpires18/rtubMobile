package models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import entities.Inscricao;

public class InscricaoRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Inscricao find(int id) {
        try{
            return restTemplate.getForObject(BASE_URL + "inscricao/" + id, Inscricao.class);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    // GET
    // Not usable;
    public List<Inscricao> findAll() {
        try {
            return restTemplate.exchange(BASE_URL + "inscricao/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Inscricao>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // GET/ID/ID
    public List<Inscricao> findInscrito(int idE, int idM) {
        try{
            return restTemplate.exchange(BASE_URL + "inscritos/"+ idE + "/" + idM,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Inscricao>>() {}).getBody();
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    public List<Inscricao> findInscritos(int idEvento) {
        try {
            return restTemplate.exchange(BASE_URL + "inscritos/" + idEvento,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Inscricao>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createInscricao(Inscricao i) {
        return restTemplate.postForLocation(BASE_URL + "inscricao/", i, Inscricao.class);
    }

    // DELETE
    public void delete(int id){
        try{
            restTemplate.delete(BASE_URL + "inscricao/" + id);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }

    // PUT
    public Inscricao updateInscricao(Inscricao i) {
        restTemplate.put(BASE_URL + "inscricao/" + i.getId(), i);
        return i;
    }
}