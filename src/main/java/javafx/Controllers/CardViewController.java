package javafx.Controllers;

import javafx.Model.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import kanban.Model.User;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.*;

public class CardViewController implements Initializable {

    private BoardViewController controller;

    private Note note;

    private Map<String, Integer> usernameWithID;

    @FXML
    private CheckComboBox<String> usersList;

    @FXML
    private TextArea description;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private void MoveTaskToNextColumn(ActionEvent e){
        try {
            controller.toNextColumn(note);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void MoveTaskToPreviousColumn(ActionEvent e){
        try {
            controller.toPrevColumn(note);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void saveChanges(ActionEvent e){
        ServerController server = new ServerController();
        String description = this.description.getText();
        List<User> users = new ArrayList<>();
        if(note.getCard().getUsers() != null) {
            ObservableList<String> u = usersList.getCheckModel().getCheckedItems();
            for(int i = 0; i < u.toArray().length; i++){
                users.add(new User(usernameWithID.get(u.get(i)).longValue()));
            }
        }
        if(!note.getCard().getDescription().equals(description)) {
            note.getCard().setDescription(description);
            server.putDescription(note.getCard().getId().intValue(), description);
        }
        if(!note.getCard().getUsers().equals(users)) {
            note.getCard().setUsers(null);
            server.changeUsersInCard(note.getCard().getId().intValue(), users);
        }
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void setParent (BoardViewController controller){
        this.controller = controller;
    }

    public void setDescriptionText(String descriptionText) {
        this.description.setText(descriptionText);
    }

    public void setUsersList(List<User> users){
        usernameWithID = new HashMap<>();
        ObservableList<String> strings = FXCollections.observableArrayList();
        for(int i = 0; i < users.toArray().length; i++){
            strings.add(users.get(i).getUsername());
            usernameWithID.put(users.get(i).getUsername(), users.get(i).getId().intValue());
        }
        usersList.getItems().addAll(strings);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCheckedUsersList(List<User> users) {
        for(int i = 0; i < users.toArray().length; i++){
            usersList.getCheckModel().check(users.get(i).getUsername());
        }
    }
}
