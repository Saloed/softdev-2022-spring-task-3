package kanban.Model;

import kanban.Entity.BoardEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private Long id;
    private String title;
    private List<User> users;

    public static Board toModel(BoardEntity entity){
        Board board = new Board();
        board.setId(entity.getId());
        board.setTitle(entity.getTitle());
        board.setUsers(entity.getUsers().stream().map(User::toModel).collect(Collectors.toList()));
        return board;
    }

    public Board() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
