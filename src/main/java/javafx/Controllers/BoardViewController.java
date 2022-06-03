package javafx.Controllers;

import javafx.Model.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kanban.Model.Board;
import kanban.Model.Card;
import kanban.Model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BoardViewController implements Initializable {

    private CardViewController cardViewController;

    private BoardsViewController controller;

    private Board board;

    @FXML
    private Text title;

    @FXML
    private TextField newTask;

    @FXML
    private TextField  newUserField;

    @FXML
    private VBox todo;

    @FXML
    private VBox inProgress;

    @FXML
    private VBox done;

    @FXML
    private void addNewUser(ActionEvent e){
        String username = newUserField.getText();
        if(!username.isBlank()){
            ServerController server = new ServerController();
            User user = server.getUser(username);
            server.addObjectInBoard("user", user, board.getId().intValue());
            server.addBoard("users", user.getId().intValue(), board);
        }
    }

    @FXML
    private void addNewTask(ActionEvent e) {
        ServerController server = new ServerController();
        Card card = server.post("cards", new Card(newTask.getText(), "That's task.", board.getColumns().get(0)), Card.class);
        Note note = new Note(new TextField(newTask.getText()), card);
        setActionOnTask(note.getTextField(), note);
        todo.getChildren().add(note.getTextField());
        server.moveCard("addcard", board.getColumns().get(0).getId().intValue(), card);
    }

    public void addTasksAtToDoColumn(List<Card> cards){
        List<TextField> tasks = new ArrayList<>();
        creationOfFieldsForCards(cards, tasks);
        todo.getChildren().addAll(tasks);
    }

    public void addTasksAtInProgressColumn(List<Card> cards){
        List<TextField> tasks = new ArrayList<>();
        creationOfFieldsForCards(cards, tasks);
        inProgress.getChildren().addAll(tasks);
    }

    public void addTasksAtDoneColumn(List<Card> cards){
        List<TextField> tasks = new ArrayList<>();
        creationOfFieldsForCards(cards, tasks);
        done.getChildren().addAll(tasks);
    }

    private void creationOfFieldsForCards(List<Card> cards, List<TextField> tasks) {
        for(int i = 0; i < cards.toArray().length; i++) {
            TextField task = new TextField(cards.get(i).getTitle());
            tasks.add(task);
            setActionOnTask(task, new Note(task, cards.get(i)));
        }
    }

    private void setActionOnTask(TextField task, Note note) {
        task.setEditable(false);
        task.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/card-view.fxml"));
                Parent root = fxmlLoader.load();

                Card card = new ServerController().getOne("cards", note.getCard().getId().intValue(), Card.class);
                note.setCard(card);

                cardViewController = fxmlLoader.getController();
                cardViewController.setParent(this);
                cardViewController.setNote(note);
                cardViewController.setDescriptionText(note.getCard().getDescription());
                cardViewController.setUsersList(board.getUsers());
                if(note.getCard().getUsers() != null) cardViewController.setCheckedUsersList(note.getCard().getUsers());

                Stage stage = new Stage();
                stage.setTitle(note.getCard().getTitle());
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void toNextColumn(Note n){
        ServerController server = new ServerController();
        if(n.getCard().getColumn().getTitle().equals(todo.getId())){
            todo.getChildren().removeAll(n.getTextField());
            inProgress.getChildren().addAll(n.getTextField());
            n.getCard().setColumn(board.getColumns().get(1));

            server.moveCard("deletecard", board.getColumns().get(0).getId().intValue(), n.getCard());
            server.moveCard("addcard", board.getColumns().get(1).getId().intValue(), n.getCard());
        }
        else if(n.getCard().getColumn().getTitle().equals(inProgress.getId())){
            inProgress.getChildren().removeAll(n.getTextField());
            done.getChildren().addAll(n.getTextField());
            n.getCard().setColumn(board.getColumns().get(2));

            server.moveCard("deletecard", board.getColumns().get(1).getId().intValue(), n.getCard());
            server.moveCard("addcard", board.getColumns().get(2).getId().intValue(), n.getCard());
        }
    }


    public void toPrevColumn(Note n){
        ServerController server = new ServerController();
        if(n.getCard().getColumn().getTitle().equals(inProgress.getId())){
            inProgress.getChildren().removeAll(n.getTextField());
            todo.getChildren().addAll(n.getTextField());
            n.getCard().setColumn(board.getColumns().get(0));

            server.moveCard("deletecard", board.getColumns().get(1).getId().intValue(), n.getCard());
            server.moveCard("addcard", board.getColumns().get(0).getId().intValue(), n.getCard());
        }
        else if(n.getCard().getColumn().getTitle().equals(done.getId())){
            done.getChildren().removeAll(n.getTextField());
            inProgress.getChildren().addAll(n.getTextField());
            n.getCard().setColumn(board.getColumns().get(1));

            server.moveCard("deletecard", board.getColumns().get(2).getId().intValue(), n.getCard());
            server.moveCard("addcard", board.getColumns().get(1).getId().intValue(), n.getCard());
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setParent (BoardsViewController controller){
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
