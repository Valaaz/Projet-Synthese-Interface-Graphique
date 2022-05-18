package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ConvexController {
    @FXML
    Tab tabImportFile, tabGenerateFile;

    @FXML
    public void initialize() throws IOException {
        tabImportFile.setContent(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/resources/import-file.fxml"))));
        tabGenerateFile.setContent(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/resources/generate-file.fxml"))));
    }
}
