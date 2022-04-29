package server.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import server.Model.Card;
import server.Model.User;

import java.util.List;

public class ServerController {
    private static final String BASE_URL = "http://localhost:8080/";

    private static RestTemplate restTemplate;
    private HttpHeaders headers;
    private HttpEntity<Object> entity;

    public ServerController() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public String post(String url, Object obj){
        entity = new HttpEntity(obj, headers);
        return restTemplate.exchange(BASE_URL + url, HttpMethod.POST, entity, String.class).getBody();
    }

    public String getOne(String url,int id){
        entity = new HttpEntity(headers);
        return restTemplate.exchange(BASE_URL + url + "/" + id, HttpMethod.GET, entity, String.class).getBody();
    }

    public String getUsers(String url, int id){
        entity = new HttpEntity(headers);
        return restTemplate.exchange(BASE_URL + url + "/" + id +"/users", HttpMethod.GET, entity, String.class).getBody();
    }

    public void putUsers(String url, int id, List<User> users){
        restTemplate.put(BASE_URL + url + "/" + id +"/users", users);
    }

    public void putTitle(String url, int id, String title){
        restTemplate.put(BASE_URL + url + "/" + id +"/title", title);
    }

    public void putDescription(int id, String desc){
        restTemplate.put(BASE_URL + "cards/" + id +"/desc", desc);
    }

    public void putCards(int id, List<Card> cards){
        restTemplate.put(BASE_URL + "lists/" + id +"/cards", cards);
    }

    public void delete(String url,int id){
        restTemplate.delete(BASE_URL + url + "/" + id);
    }

}
