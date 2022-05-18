package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;

public class GenerateFileController {
    private ArrayList<Circle> pointsToSave;

    @FXML
    private Label lblFileChoosen;

    @FXML
    private Pane panePoints;

    @FXML
    Button btnSave;

    @FXML
    public void initialize() {
        panePoints.setStyle("-fx-background-color: #DCDCDC");
        pointsToSave = new ArrayList<Circle>();

        panePoints.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown()) {
                    Point2D PHG = new Point2D(e.getX(), e.getY());
                    Circle circle = new Circle(PHG.getX(), PHG.getY(), 2);
                    panePoints.getChildren().add(circle);
                    pointsToSave.add(circle);
                }
            }
        });
    }

    @FXML
    void reset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmation de réinitialisation");
        alert.setHeaderText("Êtes-vous sûr de vouloir tout réinitialiser ?");
        alert.setContentText("Si vous confirmez, cela effacera les points affichés");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            panePoints.getChildren().clear();
            pointsToSave.clear();
        }
    }

    @FXML
    void validate() throws IOException {
        if (pointsToSave.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Erreur de points");
            alert.setHeaderText("Il n'y a pas de point à enregistrer");
            alert.setContentText("Placez au moins un point pour pouvoir sauvegarder le fichier");

            Optional<ButtonType> option = alert.showAndWait();
        } else {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Sauvegardez le fichier");
            File homeDir = new File("./java/controller");
            fileChooser.setInitialDirectory(homeDir);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*"),
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File newFile = fileChooser.showSaveDialog(new Stage());
            if (newFile != null) {
                String coordonates = String.valueOf(pointsToSave.size());
                for (Circle point : pointsToSave) {
                    coordonates += "\n" + Math.round(point.getCenterX()) + " " + Math.round(point.getCenterY());
                }
                saveTextToFile(coordonates, newFile);
            }
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}