package sample;

import Data.Client;
import Data.ClientData;
import Data.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class UpdateInvoiceController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Circle closeButton;
    @FXML
    private TextField invoiceNumber;
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private Button modifyButton;
    @FXML
    private ChoiceBox<Client> clientChoice;
    private Stage stage;
    private Invoice invoice;
    private double xOffSet;
    private double yOffSet;

    public void loadInfo(Invoice invoice) {
        mainPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        mainPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });


        this.invoice = invoice;
        stage = (Stage) closeButton.getScene().getWindow();

        invoiceNumber.setText(invoice.getInvoiceNumber());
        invoiceDate.setValue(invoice.getDate());
        invoiceNumber.setOnKeyReleased(event -> {
            if (invoiceNumber.getText().isEmpty()) {
                modifyButton.setDisable(true);
            } else {
                modifyButton.setDisable(false);
            }
        });
        clientChoice.getItems().setAll(ClientData.getInstance().getClients());
        clientChoice.getSelectionModel().select(invoice.getClient());
    }

    @FXML
    private void updateItem() {
        String number = invoiceNumber.getText();
        String date = invoiceDate.getValue().toString();
        Client client = clientChoice.getSelectionModel().getSelectedItem();
        ClientData.getInstance().updateInvoice(number, date, client, invoice.getInvoiceId());
        closeStage();
    }

    @FXML
    private void closeStage() {
        stage.close();
    }
}
