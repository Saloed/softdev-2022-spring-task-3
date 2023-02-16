package javafx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartScreen extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loginView = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent root = loginView.load();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
