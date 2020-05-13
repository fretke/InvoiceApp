package sample;

import Data.Client;
import Data.ClientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SettingsClientController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Circle closeCircle;
    @FXML
    private TableView<Client> tableView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;

    private Stage stage;
    private double xOffSet;
    private double yOffSet;

    @FXML
    private void initialize() {
        mainPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        mainPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });

        deleteButton.setDisable(true);
        modifyButton.setDisable(true);
        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                deleteButton.setDisable(false);
                modifyButton.setDisable(false);
            } else {
                deleteButton.setDisable(true);
                modifyButton.setDisable(true);
            }
        });
        loadClient(-1);
    }

    public void setStage() {
        stage = (Stage) closeCircle.getScene().getWindow();
    }

    private void loadClient(int row) {
        Task<ObservableList<Client>> task = new Task<ObservableList<Client>>() {
            @Override
            protected ObservableList<Client> call() {
                return FXCollections.observableArrayList(ClientData.getInstance().getClients());
            }
        };
        new Thread(task).start();
        tableView.itemsProperty().bind(task.valueProperty());
        if (row != -1) {
            task.setOnSucceeded(event -> tableView.getSelectionModel().select(row));
        }
    }

    @FXML
    public void deleteClient() {
        Client client = tableView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("deleteClientPane.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.initStyle(StageStyle.UNDECORATED);
        DeleteClientController controller = loader.getController();
        controller.setUpPane(client);
        dialog.showAndWait();
        if (controller.getStatus()) {
            loadClient(-1);
        }
    }

    @FXML
    public void insertClientPane() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("insertClient.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            System.out.println("Problem in loading insertClientPane " + e.getMessage());
        }
        InsertClientController controller = loader.getController();
        dialog.initStyle(StageStyle.UNDECORATED);
        controller.setStage();
        dialog.showAndWait();
        if (controller.getState()) {
            loadClient(-1);
        }
    }

    @FXML
    private void modifyClientPane() {
        int row = tableView.getSelectionModel().getFocusedIndex();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("insertClient.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            System.out.println("Problem in loading insertClientPane " + e.getMessage());
        }
        InsertClientController controller = loader.getController();
        dialog.initStyle(StageStyle.UNDECORATED);
        controller.loadClientDetails(tableView.getSelectionModel().getSelectedItem());
        dialog.showAndWait();
        if (controller.getState()) {
            loadClient(row);
        }
    }

    @FXML
    private void closePane() {
        // Stage stage = (Stage) closeCircle.getScene().getWindow();
        stage.close();
    }
}


