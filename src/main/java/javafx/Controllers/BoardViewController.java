package javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kanban.Model.Board;
import kanban.Model.Card;
import kanban.ServerController;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BoardViewController implements Initializable {

    private CardViewController cardViewController;

    private BoardsViewController controller;

    private Board board;

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
            JSONObject user = new JSONObject(server.getUser(username));
            server.addObjectInBoard("user", board.getId().intValue(), (int) user.getLong("id"));
            server.addBoard("users", (int) user.getLong("id"), board);
        }
    }

    @FXML
    private void addNewTask(ActionEvent e) {
        TextField task = new TextField(newTask.getText());
        Card card = new Card(newTask.getText(), task);
        card.setColumn(board.getColumns().get(0));
        card.setDescription("That's task.");

        setActionOnTask(task, card);
        todo.getChildren().addAll(task);

        ServerController server = new ServerController();
        server.post("cards", card);
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
            cards.get(i).setTextField(task);
            setActionOnTask(task, cards.get(i));
        }
    }

    private void setActionOnTask(TextField task, Card card) {
        task.setEditable(false);
        task.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/card-view.fxml"));
                Parent root = fxmlLoader.load();

                cardViewController = fxmlLoader.getController();
                cardViewController.setParent(this);
                cardViewController.setCard(card);
                cardViewController.setDescriptionText(card.getDescription());

                Stage stage = new Stage();
                stage.setTitle(card.getTitle());
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void toNextColumn(Card c){
        ServerController server = new ServerController();
        if(c.getColumn().getTitle().equals(todo.getId())){
            todo.getChildren().removeAll(c.getTextField());
            inProgress.getChildren().addAll(c.getTextField());
            c.setColumn(board.getColumns().get(1));

            server.moveCard("addcard", board.getColumns().get(1).getId().intValue(), c);
            server.moveCard("deletecard", board.getColumns().get(0).getId().intValue(), c);
        }
        else if(c.getColumn().getTitle().equals(inProgress.getId())){
            inProgress.getChildren().removeAll(c.getTextField());
            done.getChildren().addAll(c.getTextField());
            c.setColumn(board.getColumns().get(2));

            server.moveCard("addcard", board.getColumns().get(2).getId().intValue(), c);
            server.moveCard("deletecard", board.getColumns().get(1).getId().intValue(), c);
        }
    }


    public void toPrevColumn(Card c){
        ServerController server = new ServerController();
        if(c.getColumn().getTitle().equals(inProgress.getId())){
            inProgress.getChildren().removeAll(c.getTextField());
            todo.getChildren().addAll(c.getTextField());
            c.setColumn(board.getColumns().get(0));

            server.moveCard("addcard", board.getColumns().get(0).getId().intValue(), c);
            server.moveCard("deletecard", board.getColumns().get(1).getId().intValue(), c);
        }
        else if(c.getColumn().getTitle().equals(done.getId())){
            done.getChildren().removeAll(c.getTextField());
            inProgress.getChildren().addAll(c.getTextField());
            c.setColumn(board.getColumns().get(1));

            server.moveCard("addcard", board.getColumns().get(1).getId().intValue(), c);
            server.moveCard("deletecard", board.getColumns().get(2).getId().intValue(), c);
        }
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
