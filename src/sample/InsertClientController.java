package sample;

import Data.Client;
import Data.ClientData;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class InsertClientController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField name;
    @FXML
    private TextField code;
    @FXML
    private TextField vatNumber;
    @FXML
    private TextField address;
    @FXML
    private Button confirmButton;
    @FXML
    private Circle closeCircle;
    @FXML
    private Label titleLabel;
    @FXML
    private ProgressIndicator progressBar;
    private boolean state;
    private Client currentClient;

    private Stage stage;
    private double xOffSet;
    private double yOffSet;


    @FXML
    private void initialize() {
        borderPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        borderPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });
        progressBar.setVisible(false);
        state = false;
        confirmButton.setDisable(true);
        borderPane.setOnKeyReleased(event -> {
            if (name.getText().isEmpty() || code.getText().isEmpty() || vatNumber.getText().isEmpty() || address.getText().isEmpty()) {
                confirmButton.setDisable(true);
            } else {
                confirmButton.setDisable(false);
            }
        });
    }

    public void setStage() {
        stage = (Stage) closeCircle.getScene().getWindow();
    }

    public void loadClientDetails(Client client) {
        stage = (Stage) closeCircle.getScene().getWindow();
        this.currentClient = client;
        confirmButton.setText("Modifikuoti");
        titleLabel.setText("Modifikuoti");
        name.setText(client.getName());
        code.setText(client.getCompanyCode());
        vatNumber.setText(client.getVatNumber());
        address.setText(client.getAddress());
        confirmButton.setDisable(false);
    }

    @FXML
    public void insertClientIntoDB() {
        if (titleLabel.getText().equals("Modifikuoti")) {
            progressBar.setVisible(true);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    ClientData.getInstance().updateClient(name.getText(), code.getText(), vatNumber.getText(), address.getText(), currentClient.getClientId());
                    return null;
                }
            };
            new Thread(task).start();
            progressBar.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> {
                state = true;
                closePane();
            });

        } else {
            progressBar.setVisible(true);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    ClientData.getInstance().insertClient(name.getText(), code.getText(), vatNumber.getText(), address.getText());
                    return null;
                }
            };
            new Thread(task).start();
            progressBar.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> {
                state = true;
                closePane();
            });
        }
    }

    public boolean getState() {
        return state;
    }

    @FXML
    private void closePane() {
        Stage stage = (Stage) closeCircle.getScene().getWindow();
        stage.close();
    }
}
