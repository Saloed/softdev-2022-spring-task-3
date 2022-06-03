package javafx.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanban.Model.Board;
import kanban.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    private BoardsViewController boardsViewController;

    private User mainUser;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private void signIn(ActionEvent e){
        String username = usernameField.getText();
        if(!username.isBlank()){
            ServerController server = new ServerController();
            User user = server.getUser(username);
            if (passwordField.getText().equals(user.getPassword())) {
                setMainUser(user);
                List<Board> boards = user.getBoards();
                try {
                    Stage loginStage = (Stage) usernameField.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/boards-view.fxml")); //Окно с выбором досок
                    Parent root = fxmlLoader.load();
                    boardsViewController = fxmlLoader.getController();
                    boardsViewController.setParent(this);
                    boardsViewController.setMainUser(mainUser);
                    boardsViewController.setUsernameLabel(mainUser.getUsername());
                    boardsViewController.setListOfBoards(boards);
                    Stage stage = new Stage();
                    stage.setTitle("Boards");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                    loginStage.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void signUp(ActionEvent e){
        String username = usernameField.getText();
        if(!username.isBlank()) {
           new ServerController().post("users", new User(username, passwordField.getText()), User.class);
        }
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User user) {
        this.mainUser = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
