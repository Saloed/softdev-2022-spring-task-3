package kanban.Model;

import java.util.List;

public class User {
    private Long id;
    private String username;
    private String password;
    private List<Board> boards;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, List<Board> boards) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.boards = boards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    @Override
    public String toString() {
        return username;
    }
}
