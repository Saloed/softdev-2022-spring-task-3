package javafx.Controllers;

import kanban.Model.Board;
import kanban.Model.Card;
import kanban.Model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerController {
    private static final String BASE_URL = "http://localhost:8080/";

    private static RestTemplate restTemplate;
    public ServerController() {
        restTemplate = new RestTemplate();
    }

    public <T> T post(String url, T obj, Class<T> cls){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity(obj, headers);
        return restTemplate.postForObject(BASE_URL + url, entity, cls) ;
    }

    public <T> T getOne(String url,int id, Class<T> cls){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity(headers);
        return restTemplate.exchange(BASE_URL + url + "/" + id, HttpMethod.GET, entity, cls).getBody();
    }

    public User getUser(String username){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity(headers);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(BASE_URL + "users/find")
                .queryParam("username", "{username}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, User.class, params).getBody();
    }

    public void addBoard(String url, int userId, Board board){
        restTemplate.put(BASE_URL + url + "/" + userId +"/board", board);
    }

    public void addObjectInBoard(String url, Object obj, int boardId){
        restTemplate.put(BASE_URL + "boards/" + boardId + "/" + url, obj);
    }

    public void changeUsersInCard(int id, List<User> users){
        restTemplate.put(BASE_URL + "cards/" + id +"/users", users);
    }

    public void putDescription(int id, String desc){
        restTemplate.put(BASE_URL + "cards/" + id +"/desc", desc);
    }

    public void moveCard(String url, int id, Card card){
        restTemplate.put(BASE_URL + "lists/" + id +"/" + url, card);
    }

    public void delete(String url,int id){
        restTemplate.delete(BASE_URL + url + "/" + id);
    }

}
