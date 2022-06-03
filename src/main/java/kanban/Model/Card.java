package kanban.Model;

import java.util.List;

public class Card {
    private Long id;
    private String title;
    private List<User> users;
    private String description;
    private Column column;

    public Card() {
    }

    public Card(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Card(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Card(String title, String description, Column column) {
        this.title = title;
        this.description = description;
        this.column = column;
    }

    public Card(Long id, String title, List<User> users, String description, Column column) {
        this.id = id;
        this.title = title;
        this.users = users;
        this.description = description;
        this.column = column;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
