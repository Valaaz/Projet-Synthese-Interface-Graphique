module com.example.projetsyntheseinterfacegraphique {
    requires javafx.controls;
    requires javafx.fxml;


    opens main to javafx.fxml;
    exports main;
    exports application;
    opens application to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}