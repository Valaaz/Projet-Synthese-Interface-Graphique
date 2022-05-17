package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ConvexeController {
    private boolean isFileSelected = false;

    @FXML
    Button btnFileChooser;
    @FXML
    Label lblFileChoosen, lblSortAlgo;
    @FXML
    Pane panePoints;
    @FXML
    ChoiceBox<String> chBoxConvexeAlgo, chBoxSortAlgo;

    @FXML
    public void initialize() {
        panePoints.setStyle("-fx-background-color: #DCDCDC");

        lblFileChoosen.setVisible(false);

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
                panePoints.getChildren().clear();
                isFileSelected = true;
                lblFileChoosen.setVisible(true);

                btnFileChooser.setText("Sélectionner un autre fichier");
                lblFileChoosen.setText("Fichier sélectionné : " + selectedFile.getName());

                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    reader.readLine();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] coordinates = line.split(" ");
                        Point2D point = new Point2D(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
                        Circle circle = new Circle(point.getX(), point.getY(), 2);
                        panePoints.getChildren().add(circle);
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
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
            alert.setContentText("Sélectionnez un fichier avec le bouton \"Insérer un fichier\" puis validez ensuite");

            alert.showAndWait();
        }
    }

    @FXML
    public void reset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmation de réinitialisation");
        alert.setHeaderText("Êtes-vous sûr de vouloir tout réinitialiser ?");
        alert.setContentText("Si vous confirmez, cela effacera les points affichés et vous devrez de nouveau sélectionner un fichier");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            btnFileChooser.setText("Insérer un fichier");
            lblFileChoosen.setVisible(false);
            isFileSelected = false;

            panePoints.getChildren().clear();

            chBoxConvexeAlgo.getSelectionModel().select(0);
            chBoxSortAlgo.getSelectionModel().select(0);
        }
    }
}