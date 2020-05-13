package sample;

import Data.Client;
import Data.ClientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.time.LocalDate;

import java.util.List;


public class InsertInvoiceController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField invoiceNumber;
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private Circle closeCircle;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Client> listView;
    @FXML
    private Button confirmButton;
    private List<Client> clientList;

    private Stage stage;
    private double xOffSet;
    private double yOffSet;

    @FXML
    public void initialize() {
        mainPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        mainPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });

        confirmButton.setDisable(true);
        searchField.setOnKeyReleased(event -> searchClientList());
        invoiceNumber.setOnKeyReleased(event -> {
            if (invoiceNumber.getText().isEmpty()) {
                confirmButton.setDisable(true);
            } else {
                confirmButton.setDisable(false);
            }
        });
        listView.setOnMouseClicked(event -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                confirmButton.setDisable(false);
            } else {
                confirmButton.setDisable(true);
            }
        });
        titleLabel.setText("Nauja sąskaita");
        invoiceDate.setValue(LocalDate.now());
        invoiceNumber.setText(ClientData.getInstance().getCurrentInvoiceNumber());
        loadClientChoiceBox();
    }

    public void setStage() {
        stage = (Stage) closeCircle.getScene().getWindow();
    }

    private void loadClientChoiceBox() {
        clientList = ClientData.getInstance().getClients();
        listView.setItems(FXCollections.observableArrayList(clientList));
    }

    private void setListView(ObservableList<Client> client) {
        listView.setItems(client);
    }

    @FXML
    private void createInvoice() {
        ClientData.getInstance().insertInvoice(invoiceNumber.getText(), invoiceDate.getValue(), listView.getSelectionModel().getSelectedItem());
        closePane();
    }

    @FXML
    private void closePane() {
        //  Stage stage = (Stage) closeCircle.getScene().getWindow();
        stage.close();
    }

    private void searchClientList() {
        ObservableList<Client> searchResult = FXCollections.observableArrayList();
        for (Client client : clientList) {
            if (client.getName().toLowerCase().contains(searchField.getText().toLowerCase())) {
                searchResult.add(client);
            }
        }
        setListView(searchResult);
    }
}
