package sample;

import Data.ClientData;
import Data.RentItem;
import animatefx.animation.ZoomIn;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AddTemplateController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Circle closeCircle;
    @FXML
    private Label titleLabel;
    @FXML
    private Button addButton;

    @FXML
    private TextField nameField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField priceField;
    private RentItem rentItem;
    private boolean state;

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
        ZoomIn zoom = new ZoomIn(borderPane);
        zoom.setSpeed(3).play();
        state = false;
        addButton.setOnAction(event -> {
            if (addButton.getText().equals("Modifikuoti")) {
                modifyRentItem();
            } else {
                addRentItem();
            }
        });
    }

    public void modifySetUp(RentItem item) {
        stage = (Stage) closeCircle.getScene().getWindow();
        this.rentItem = item;
        nameField.setText(item.getItemName());
        unitField.setText(item.getMeasuringUnit());
        priceField.setText(String.valueOf(item.getPrice()));
        addButton.setText("Modifikuoti");
        titleLabel.setText("Modifikuoti paruoštuką");
    }

    public void addSetUp() {
        stage = (Stage) closeCircle.getScene().getWindow();
        addButton.setText("Pridėti");
        titleLabel.setText("Pridėti paruoštuką");
    }

    @FXML
    private void modifyRentItem() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                ClientData.getInstance().updatePrepItem(nameField.getText(), unitField.getText(), Double.parseDouble(priceField.getText()), rentItem.getRentItemId());
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            state = true;
            closePane();
        });
    }

    @FXML
    private void addRentItem() {
        RentItem item = new RentItem(nameField.getText(), unitField.getText(), 1, Double.parseDouble(priceField.getText()));
        int generatedKey = ClientData.getInstance().addPrepItem(nameField.getText(), unitField.getText(), Double.parseDouble(priceField.getText()));
        if (generatedKey != -1) {
            item.setRentItemId(generatedKey);
        }
        state = true;
        this.rentItem = item;
        closePane();
    }

    public RentItem getRentItem() {
        return this.rentItem;
    }

    public boolean getState() {
        return state;
    }

    @FXML
    private void closePane() {
        //   Stage stage = (Stage) closeCircle.getScene().getWindow();
        stage.close();
    }
}

