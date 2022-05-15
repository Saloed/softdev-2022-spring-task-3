package graph;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class stage extends Application {

    private File file;
    private Button button;
    private ImageView img;
    private ToggleGroup pics;
    private ToggleGroup field;
    private int size;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Button getButton(){
        return  button;
    }

    public void setButton(Button button){
        this.button = button;
    }

    public void setImg(ImageView img){
        this.img = img;
    }

    public ImageView getImg(){
        return img;
    }

    public void setPics(ToggleGroup pics){
        this.pics = pics;
    }
    public ToggleGroup getPics(){
        return pics;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return size;
    }
    public void setField(ToggleGroup field){
        this.field = field;
    }
    public ToggleGroup getField(){
        return field;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox r = size();
        BorderPane title = title();
        VBox pic = pic(stage);
        HBox root = new HBox();
        root.getChildren().addAll(r, title, pic);
        Scene scene = new Scene(root);
        root.setStyle("-fx-background: rgb(0,230,0);-fx-background-repeat: no-repeat;" +
                "-fx-background-size: 500 500; -fx-background-position: center center;");
        stage.setTitle("Tag");
        stage.getIcons().add(new Image("file:res/icon.png"));
        stage.setWidth(530);
        stage.setHeight(530);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        getButton().setOnAction((final ActionEvent e) ->{
            try {
                if (getFile() != null) setImg(new ImageView(new Image(new FileInputStream(getFile()))));
                else {RadioButton button1 = (RadioButton) pics.getSelectedToggle();
                    setImg((ImageView) button1.getGraphic());
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            RadioButton button = (RadioButton) field.getSelectedToggle();
            setSize(Integer.parseInt(button.getId()));
        });
    }



    public VBox size(){
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("3*3");
        rb1.setToggleGroup(group);
        rb1.setId("9");
        rb1.setFont(new Font(20));
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("4*4");
        rb2.setToggleGroup(group);
        rb2.setId("16");
        rb2.setFont(new Font(20));
        RadioButton rb3 = new RadioButton("5*5");
        rb3.setToggleGroup(group);
        rb3.setId("25");
        rb3.setFont(new Font(20));
        Label field = new Label("Choose field size");
        field.setFont(new Font(18));
        VBox r = new VBox();
        r.getChildren().addAll(field,rb1,rb2,rb3);
        r.setSpacing(20);
        r.setPadding(new Insets(230,0,0, 0));
        setField(group);
        return r;
    }

    public BorderPane title(){
        BorderPane title = new BorderPane();
        VBox start = new VBox();
        Button b = new Button("Start!");
        b.setPrefSize(100,50);
        setButton(b);
        Label name = new Label("Tag");
        name.setPadding(new Insets(0, 0,0,10));
        start.getChildren().addAll(name, b);
        start.setSpacing(20);
        start.setPadding(new Insets(0,0,0,60));
        name.setFont(new Font("Times New Roman",50));
        title.setTop(start);
        return title;
    }

    public VBox pic(Stage stage){
        FileChooser fileChooser = new FileChooser();
        RadioButton chosen = new RadioButton("browse");
        chosen.setFont(new Font(18));
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        chosen.setOnAction(
                (final ActionEvent e) -> setFile(fileChooser.showOpenDialog(stage)));




        final ToggleGroup images = new ToggleGroup();
        chosen.setToggleGroup(images);
        Label name = new Label("Choose picture");
        name.setFont(new Font(18));
        RadioButton jpg1 = new RadioButton("");
        RadioButton jpg2 = new RadioButton("");
        RadioButton jpg3 = new RadioButton("");
        Image standart = new Image("file:res/standart.png",  55, 55, false, false);
        jpg1.setGraphic(new ImageView(standart));
        jpg1.setToggleGroup(images);
        jpg1.setSelected(true);
        Image salo = new Image("file:res/salo.jpg", 55, 55, false, false);
        jpg2.setGraphic(new ImageView(salo));
        jpg2.setToggleGroup(images);
        Image loli = new Image("file:res/loli.jpg",  55, 55, false, false);
        jpg3.setToggleGroup(images);
        jpg3.setGraphic(new ImageView(loli));
        HBox pics = new HBox(jpg1,jpg2,jpg3);
        VBox pic = new VBox(name, pics, chosen);
        pic.setPadding(new Insets(225,0,0,0));
        pic.setSpacing(20);
        setPics(images);
        return pic;
    }


    public static void main(String[] args) {
        launch(args);
    }
}