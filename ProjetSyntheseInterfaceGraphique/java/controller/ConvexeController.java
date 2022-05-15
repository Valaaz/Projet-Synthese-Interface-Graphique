package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConvexeController {
    @FXML
    Button btnFileChooser;
    @FXML
    Label lblFileChoosen;
    @FXML
    TextArea txtAreaPoints;

    @FXML
    public void initialize() {
        txtAreaPoints.setEditable(false);
    }

    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choisissez un fichier");
        File homeDir = new File("./exp");
        fileChooser.setInitialDirectory(homeDir);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All", "*"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                btnFileChooser.setText("Sélectionner un autre fichier");
                lblFileChoosen.setText("Fichier sélectionné : " + selectedFile.getName());
                StringBuilder text = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = reader.readLine()) != null)
                        text.append(line).append("\n");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                txtAreaPoints.setText(String.valueOf(text));

            } catch (Exception e) {
                System.err.println("Erreur: impossible d'utiliser le fichier");
            }
        }
    }
}