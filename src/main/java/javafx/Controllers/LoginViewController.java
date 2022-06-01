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
import kanban.ServerController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
            JSONObject user = new JSONObject(server.getUser(username));
            if (passwordField.getText().equals(user.getString("password"))) {
                setMainUser(new User(user.getLong("id"), username));
                JSONArray jsonBoards = user.getJSONArray("boards");
                List<Board> boards = new ArrayList<>();
                for (int i = 0; i < jsonBoards.length(); i++) {
                    JSONObject jsonBoard = jsonBoards.getJSONObject(i);
                    boards.add(new Board(jsonBoard.getLong("id"), jsonBoard.getString("title")));
                }
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
            ServerController server = new ServerController();
            System.out.println(server.post("users", new User(username, passwordField.getText())));
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
