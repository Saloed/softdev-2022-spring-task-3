package kanban;

import kanban.Model.Board;
import kanban.Model.Card;
import kanban.Model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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
        return restTemplate.postForObject(BASE_URL + url, entity, String.class);
    }

    public String getOne(String url,int id){
        entity = new HttpEntity(headers);
        return restTemplate.exchange(BASE_URL + url + "/" + id, HttpMethod.GET, entity, String.class).getBody();
    }

    public String getUser(String username){
        entity = new HttpEntity(username, headers);
        return restTemplate.exchange(BASE_URL + "users/find", HttpMethod.GET, entity, String.class).getBody();
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

    public void putTitle(String url, int id, String title){
        restTemplate.put(BASE_URL + url + "/" + id +"/title", title);
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
