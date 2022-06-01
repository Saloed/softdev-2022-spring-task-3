package kanban.Model;

import javafx.scene.control.TextField;

import java.util.List;

public class Card {
    private String title;
    private List<User> users;
    private String description;
    private TextField textField;
    private Column column;

    public Card() {
    }

    public Card(String title, List<User> users, String description) {
        this.title = title;
        this.users = users;
        this.description = description;
    }

    public Card(String title, TextField textField) {
        this.title = title;
        this.textField = textField;
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

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
