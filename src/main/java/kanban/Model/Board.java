package kanban.Model;

import java.util.List;

public class Board {
    private Long id;
    private String title;
    private List<User> users;
    private List<Column> columns;

    public Board() {
    }

    public Board(Long id) {
        this.id = id;
    }

    public Board(String title) {
        this.title = title;
    }

    public Board(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Board(Long id, String title, List<User> users, List<Column> columns) {
        this.id = id;
        this.title = title;
        this.users = users;
        this.columns = columns;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
