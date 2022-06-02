package javafx.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanban.Model.Board;
import kanban.Model.Column;
import kanban.Model.User;

import java.net.URL;
import java.util.*;

public class BoardsViewController implements Initializable {

    private BoardViewController boardViewController;

    private LoginViewController controller;

    private Map<String, Integer> titleWithIDBoard;

    private User mainUser;

    @FXML
    private TextField boardNameField;

    @FXML
    public ComboBox<String> listOfBoards;

    @FXML
    private Label usernameLabel;

    @FXML
    private void addNewBoard(ActionEvent e){
        String newBoardName = boardNameField.getText();
        if(!newBoardName.isBlank()){

            ServerController server = new ServerController();
            List<User> users = new ArrayList<>();
            users.add(mainUser);
            Board board = server.post("boards", new Board(newBoardName, users), Board.class);

            Column todo = server.post("lists", new Column("todo"), Column.class);
            Column inProgress = server.post("lists", new Column("inProgress"), Column.class);
            Column done = server.post("lists", new Column("done"), Column.class);

            server.addBoard("users", mainUser.getId().intValue(), board);
            server.addObjectInBoard("list", todo, board.getId().intValue());
            server.addObjectInBoard("list", inProgress, board.getId().intValue());
            server.addObjectInBoard("list", done, board.getId().intValue());
            addBoardAtListOfBoards(new Board(board.getId(), board.getTitle()));
        }
    }

    @FXML
    private void openBoard(ActionEvent e){
        int id = titleWithIDBoard.get(listOfBoards.getSelectionModel().getSelectedItem());
        if(listOfBoards.getSelectionModel().getSelectedItem() != null){
            ServerController server = new ServerController();
            Board board = server.getOne("boards", id, Board.class);
            openBoardWindow(board);
        }

    }

    private void openBoardWindow(Board board) {
        try {
            Stage boardsStage = (Stage) usernameLabel.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/board-view.fxml"));
            Parent root = fxmlLoader.load();

            boardViewController = fxmlLoader.getController();
            boardViewController.setParent(this);
            boardViewController.setBoard(board);
            List<Column> columns = board.getColumns();
            boardViewController.addTasksAtToDoColumn(columns.get(0).getCards());
            boardViewController.addTasksAtInProgressColumn(columns.get(1).getCards());
            boardViewController.addTasksAtDoneColumn(columns.get(2).getCards());

            Stage stage = new Stage();
            MenuBar menuBar = (MenuBar)fxmlLoader.getNamespace().get("menuBar");
            menuBar.prefWidthProperty().bind(stage.widthProperty());
            stage.setTitle(board.getTitle());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            boardsStage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setListOfBoards(List<Board> listOfBoards) {
        titleWithIDBoard = new HashMap<>();
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < listOfBoards.toArray().length; i++){
            titles.add(listOfBoards.get(i).getTitle());
            titleWithIDBoard.put(listOfBoards.get(i).getTitle(), listOfBoards.get(i).getId().intValue());
        }
        this.listOfBoards.getItems().setAll(titles);
    }

    public void addBoardAtListOfBoards(Board board) {
        ObservableList<String> titles = listOfBoards.getItems();
        titleWithIDBoard.put(board.getTitle(), board.getId().intValue());
        titles.add(board.getTitle());
        this.listOfBoards = new ComboBox<String>(titles);
    }

    public void setUsernameLabel(String username) {
        this.usernameLabel.setText(username);
    }

    public void setMainUser(User user) {
        this.mainUser = user;
    }

    public void setParent (LoginViewController controller){
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
