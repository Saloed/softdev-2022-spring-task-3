package javafx.Controllers;

import javafx.Model.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import kanban.Model.User;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CardViewController implements Initializable {

    private BoardViewController controller;

    private Note note;

    @FXML
    private CheckComboBox<User> usersList;

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
        List<User> users = usersList.getCheckModel().getCheckedItems();
        if(!note.getCard().getDescription().equals(description)) {
            note.getCard().setDescription(description);
            server.putDescription(note.getCard().getId().intValue(), description);
        }
        if(!note.getCard().getUsers().equals(users)) {
            note.getCard().setUsers(users);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
