package models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import entities.Pedido;

public class PedidoRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/pedido/";
    //private String BASE_URL = "http://127.0.0.1:8082/api/pedido/";
    private RestTemplate restTemplate = new RestTemplate();


    // GET/ID
    public Pedido find(int id) {
        try{
            return restTemplate.getForObject(BASE_URL + id, Pedido.class);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    // GET
    public List<Pedido> findAll() {
        try {
            return restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Pedido>>() {}).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST
    public URI createPedido(Pedido p) {
        return restTemplate.postForLocation(BASE_URL, p, Pedido.class);
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