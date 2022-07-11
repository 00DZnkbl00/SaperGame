module com.example.sapergame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sapergame to javafx.fxml;
    exports com.example.sapergame;
}