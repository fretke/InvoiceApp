package sample;

import Data.ClientData;
import Data.Invoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    @FXML
    private BorderPane mainPane;
    @FXML
    private TableView<Invoice> invoiceTable;
    @FXML
    private Button deleteClientButton;

    public void initialize() {
        loadInvoices();
        System.out.println("Controller initialize method run");
        deleteClientButton.setDisable(true);
        invoiceTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    loadRentItemPane();
                }
            }
            if (invoiceTable.getSelectionModel().getSelectedItem() != null) {
                deleteClientButton.setDisable(false);
            } else {
                deleteClientButton.setDisable(true);
            }
        });
        initializeContextMenu();
    }

    private void initializeContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem modify = new MenuItem("Modifikuoti..");
        modify.setOnAction(event -> updateInvoicePane());
        contextMenu.getItems().add(modify);
        invoiceTable.setContextMenu(contextMenu);
    }

    public void updateInvoicePane() {
        Invoice invoice = invoiceTable.getSelectionModel().getSelectedItem();
        int row = invoiceTable.getSelectionModel().getFocusedIndex();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("updateInvoice.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.initStyle(StageStyle.UNDECORATED);
        UpdateInvoiceController controller = loader.getController();
        controller.loadInfo(invoice);
        dialog.showAndWait();
        reloadInvoices(row);
    }


    public void loadRentItemPane() {
        Invoice invoice = invoiceTable.getSelectionModel().getSelectedItem();
        int row = invoiceTable.getSelectionModel().getFocusedIndex();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RentItemPane1.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle("Sąskaita Nr.: " + invoice.getInvoiceNumber());
        RentItemPaneController controller = loader.getController();
        controller.loadRentItems(invoice);
        controller.setInfo(invoice, invoice.getClient().getName());

        dialog.showAndWait();
        if (controller.getState()) {
            reloadInvoices(row);
        }
    }

    private void reloadInvoices(int row) {
        Task<ObservableList<Invoice>> task = new Task<ObservableList<Invoice>>() {
            @Override
            protected ObservableList<Invoice> call() {
                return FXCollections.observableArrayList(ClientData.getInstance().getInvoiceList());
            }
        };
        new Thread(task).start();
        invoiceTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> {
            invoiceTable.getSelectionModel().select(row);
            deleteClientButton.setDisable(false);
        });
    }

    @FXML
    private void openCompanyRequisites() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("companyRequisitePane.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompanyRequisiteController controller = loader.getController();
        controller.loadPane();

        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.showAndWait();
    }

    @FXML
    private void openClientPane() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("settingsClientPane.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SettingsClientController controller = loader.getController();
        controller.setStage();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.showAndWait();
    }

    public void loadInvoices() {
        Task<ObservableList<Invoice>> task = new Task<ObservableList<Invoice>>() {
            @Override
            protected ObservableList<Invoice> call() {
                return FXCollections.observableArrayList(ClientData.getInstance().getInvoiceList());
            }
        };
        new Thread(task).start();
        invoiceTable.itemsProperty().bind(task.valueProperty());
    }

    @FXML
    public void deleteInvoice() {
        Invoice invoice = invoiceTable.getSelectionModel().getSelectedItem();
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
        controller.setUpPane(invoice);
        dialog.showAndWait();

        if (controller.getStatus()) {
            loadInvoices();
        }
    }

    public void loadInsertInvoicePane() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("insertInvoicePane.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        InsertInvoiceController controller = loader.getController();
        controller.setStage();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.showAndWait();
        loadInvoices();
    }

    @FXML
    private void loadTemplatePane() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("templatePane.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TemplateController controller = loader.getController();
        controller.setStage();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.showAndWait();
    }

}
