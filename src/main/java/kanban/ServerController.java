package kanban;

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
    private HttpHeaders headers;
    private HttpEntity<Object> entity;

    public ServerController() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public String post(String url, Object obj){
        entity = new HttpEntity(obj, headers);
        return restTemplate.postForObject(BASE_URL + url, entity, String.class);
    }

    public String getOne(String url,int id){
        entity = new HttpEntity(headers);
        return restTemplate.exchange(BASE_URL + url + "/" + id, HttpMethod.GET, entity, String.class).getBody();
    }

    public String getUser(String username){
        entity = new HttpEntity(headers);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(BASE_URL + "users/find")
                .queryParam("username", "{username}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class, params).getBody();
    }

    public String getColumn(String board){
        entity = new HttpEntity<>(headers);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(BASE_URL + "lists/find")
                .queryParam("board", "{board}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("board", board);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class, params).getBody();
    }

    public String getBoard(String title){
        entity = new HttpEntity<>(headers);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(BASE_URL + "boards/find")
                .queryParam("title", "{title}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class, params).getBody();
    }

    public void addBoard(String url, int id, Board board){
        restTemplate.put(BASE_URL + url + "/" + id +"/board", board);
    }

    public void addObjectInBoard(String url, int boardId, int obgId){
        restTemplate.put(BASE_URL + "boards/" + boardId + "/" + url, obgId);
    }

    public void changeUsersInCard(int id, List<User> users){
        restTemplate.put(BASE_URL + "cards/" + id +"/user", users);
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
