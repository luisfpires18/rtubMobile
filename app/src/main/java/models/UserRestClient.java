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
import java.util.List;

import entities.Membro;
import entities.User;

public class UserRestClient {

    private String BASE_URL = "http://10.0.2.2:8082/api/user/";
    private RestTemplate restTemplate = new RestTemplate();


    // POST
    public URI createUser(User u) {
        return restTemplate.postForLocation(BASE_URL, u, User.class);
    }

}