package javafx.Controllers;

import kanban.Model.Card;
import kanban.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CardViewController implements Initializable {

    private BoardViewController controller;

    private Card card;

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
            controller.toNextColumn(card);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void MoveTaskToPreviousColumn(ActionEvent e){
        try {
            controller.toPrevColumn(card);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void saveChanges(ActionEvent e){
        String description = this.description.getText();
        List<User> users = usersList.getCheckModel().getCheckedItems();
        if(!card.getDescription().equals(description)) card.setDescription(description);
        if(!card.getUsers().equals(users)) card.setUsers(users);
    }

    public void setCard(Card card) {
        this.card = card;
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
