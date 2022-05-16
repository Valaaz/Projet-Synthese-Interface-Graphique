package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConvexeController {
    private boolean isFileSelected = false;

    @FXML
    Button btnFileChooser;
    @FXML
    Label lblFileChoosen, lblSortAlgo;
    @FXML
    TextArea txtAreaPoints;
    @FXML
    ChoiceBox<String> chBoxConvexeAlgo, chBoxSortAlgo;

    @FXML
    public void initialize() {
        txtAreaPoints.setEditable(false);

        chBoxConvexeAlgo.getItems().add("Convexe Hull");
        chBoxConvexeAlgo.getItems().add("Slow Convexe Hull");
        chBoxConvexeAlgo.getItems().add("Rapid Convexe Hull");
        chBoxConvexeAlgo.getSelectionModel().select(0);

        chBoxSortAlgo.getItems().add("Tri par tas (tableau)");
        chBoxSortAlgo.getItems().add("Tri par tas (arbre)");
        chBoxSortAlgo.getItems().add("Tri par sélection");
        chBoxSortAlgo.getSelectionModel().select(0);

        chBoxConvexeAlgo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (observableValue.getValue().intValue() == 0) {
                    lblSortAlgo.setDisable(false);
                    chBoxSortAlgo.setDisable(false);
                    lblSortAlgo.setVisible(true);
                    chBoxSortAlgo.setVisible(true);
                } else {
                    lblSortAlgo.setDisable(true);
                    chBoxSortAlgo.setDisable(true);
                    lblSortAlgo.setVisible(false);
                    chBoxSortAlgo.setVisible(false);
                }
            }
        });
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
                isFileSelected = true;
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

    @FXML
    public void validate() {
        if (!isFileSelected) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Erreur fichier");
            alert.setHeaderText("Aucun fichier n'a été sélectionné");
            alert.setContentText("Sélectionnez un fichier puis validez ensuite");

            alert.showAndWait();
        }
    }
}