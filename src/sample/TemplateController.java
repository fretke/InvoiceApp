package sample;

import Data.ClientData;
import Data.RentItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TemplateController {
    @FXML
    private TableView<RentItem> tableView;
    @FXML
    private Circle closeCircle;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;

    private Stage stage;
    private double xOffSet;
    private double yOffSet;

    public void initialize() {
        borderPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        borderPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });
        loadPrepList();
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

    }

    public void setStage() {
        stage = (Stage) closeCircle.getScene().getWindow();
    }

    private void loadPrepList() {
        Task<ObservableList<RentItem>> task = new Task<ObservableList<RentItem>>() {
            @Override
            protected ObservableList<RentItem> call() {
                return FXCollections.observableArrayList(ClientData.getInstance().getPrepList());
            }
        };
        new Thread(task).start();
        tableView.itemsProperty().bind(task.valueProperty());
    }

    private void reloadPrepList(RentItem item) {
        Task<ObservableList<RentItem>> task = new Task<ObservableList<RentItem>>() {
            @Override
            protected ObservableList<RentItem> call() {
                return FXCollections.observableArrayList(ClientData.getInstance().getPrepList());
            }
        };
        new Thread(task).start();
        tableView.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> {
            tableView.getSelectionModel().select(item);
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                deleteButton.setDisable(false);
                modifyButton.setDisable(false);
            }
        });
    }

    private void reloadPrepList(int row) {
        Task<ObservableList<RentItem>> task = new Task<ObservableList<RentItem>>() {
            @Override
            protected ObservableList<RentItem> call() {
                return FXCollections.observableArrayList(ClientData.getInstance().getPrepList());
            }
        };
        new Thread(task).start();
        tableView.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> {
            tableView.getSelectionModel().select(row);
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                deleteButton.setDisable(true);
                modifyButton.setDisable(true);
            }
        });
    }

    @FXML
    private void deletePrepItem() {
        RentItem item = tableView.getSelectionModel().getSelectedItem();
        int row = tableView.getSelectionModel().getFocusedIndex();
        ClientData.getInstance().deletePrepItem(item);
        reloadPrepList(row);
    }

    @FXML
    private void closePane() {
        stage.close();
    }

    @FXML
    private void openAddPrepItemPane() {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addTemplatePane.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.initStyle(StageStyle.UNDECORATED);
        AddTemplateController controller = loader.getController();
        controller.addSetUp();
        dialog.showAndWait();
        if (controller.getState()) {
            reloadPrepList(controller.getRentItem());
        }
    }

    @FXML
    private void openModifyPrepItemPane() {
        RentItem item = tableView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addTemplatePane.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.initStyle(StageStyle.UNDECORATED);
        AddTemplateController controller = loader.getController();
        controller.modifySetUp(item);
        dialog.showAndWait();
        if (controller.getState()) {
            reloadPrepList(item);
        }
    }
}
