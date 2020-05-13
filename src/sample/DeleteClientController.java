package sample;

import Data.Client;
import Data.ClientData;
import Data.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DeleteClientController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label alertLabel;
    @FXML
    private Circle closeCircle;
    @FXML
    private Button deleteButton;
    private Client currentClient;
    private Invoice currentInvoice;
    private boolean status;

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

        deleteButton.setOnAction(event -> {
            if (currentClient != null) {
                deleteClient();
            } else {
                deleteInvoice();
            }
        });
        status = false;
    }

    public void setUpPane(Client client) {
        stage = (Stage) closeCircle.getScene().getWindow();
        currentClient = client;
        titleLabel.setText("Ištrinti sutartį");
        alertLabel.setText("Ar tikrai norite ištrinti klientą " + client.getName()
                + "? (Bus ištrintos visos sąskaitos susijusios su šiuo klientu)");
    }

    public void setUpPane(Invoice invoice) {
        stage = (Stage) closeCircle.getScene().getWindow();
        currentInvoice = invoice;
        titleLabel.setText("Ištrinti sutartį");
        alertLabel.setText("Ar tikrai norite ištrinti sąskaitą Nr.: " + invoice.getInvoiceNumber()
                + "?");
    }

    @FXML
    private void deleteClient() {
        ClientData.getInstance().deleteClient(currentClient);
        status = true;
        closePane();
    }

    private void deleteInvoice() {
        ClientData.getInstance().deleteInvoice(currentInvoice);
        status = true;
        closePane();
    }

    public boolean getStatus() {
        return status;
    }

    @FXML
    private void closePane() {
        // Stage stage = (Stage) closeCircle.getScene().getWindow();
        stage.close();
    }
}
