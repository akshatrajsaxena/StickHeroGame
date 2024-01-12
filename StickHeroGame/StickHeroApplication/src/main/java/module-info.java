module com.example.stickheroapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;


    opens com.example.stickheroapplication to javafx.fxml;
    exports com.example.stickheroapplication;
}