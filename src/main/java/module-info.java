module com.example.mineswip {
    requires javafx.controls;
    requires javafx.fxml;


    opens minesweep to javafx.fxml;
    exports minesweep;
}