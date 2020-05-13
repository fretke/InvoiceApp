package sample;

import Coversion.DocFormer;
import Data.*;
import animatefx.animation.ZoomIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class RentItemPaneController {

    public static final String redTextColor = "-fx-text-inner-color: red;";
    public static final String blackTextColor = "-fx-text-inner-color: black;";

    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane fixedPane;
    @FXML
    private Pane defaultPane;
    @FXML
    private CheckBox checkBox;
    @FXML
    private ChoiceBox<RentItem> choiceBox;
    @FXML
    private TableView<RentItem> tableView;
    @FXML
    private TextField itemName;
    @FXML
    private TextField measuringUnit;
    @FXML
    private TextField prepUnit;
    @FXML
    private TextField quantity;
    @FXML
    private TextField prepQuantity;
    @FXML
    private TextField amount;
    @FXML
    private TextField prepPrice;
    @FXML
    private Button insertItem;
    @FXML
    private Button deleteItem;
    @FXML
    private Circle closeCircle;
    @FXML
    private Label titleLabel;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label vatLabel;
    @FXML
    private Label sumLabel;
    @FXML
    private ProgressIndicator progressIndicator;

    private Stage stage;
    private double xOffSet;
    private double yOffSet;
    private Invoice invoice;
    private boolean state;

    @FXML
    public void initialize() {

        state = false;
        borderPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        borderPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });

        defaultPane.toFront();
        checkBox.setOnMouseClicked(event -> {
            if (checkBox.isSelected()) {
                ZoomIn zoom = new ZoomIn(fixedPane);
                zoom.setSpeed(3);
                zoom.play();
                fixedPane.toFront();
                defaultPane.toBack();
            } else {
                ZoomIn zoom = new ZoomIn(defaultPane);
                zoom.setSpeed(3);
                zoom.play();
                defaultPane.toFront();
                fixedPane.toBack();
            }
            checkToEnableButtons();
        });
        deleteItem.setDisable(true);
        borderPane.setOnKeyReleased(event -> {
            checkToEnableButtons();
            highlightIncorrect();
        });
        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                deleteItem.setDisable(true);
            } else {
                deleteItem.setDisable(false);
            }
        });

        List<RentItem> prepItem = ClientData.getInstance().getPrepList();
        choiceBox.getItems().setAll(prepItem);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setOnAction(event -> uploadPrepFields());
        uploadPrepFields();

    }

    public void setInfo(Invoice invoice, String client) {
        stage = (Stage) closeCircle.getScene().getWindow();
        titleLabel.setText("Klientas: \"" + client + "\", sąskaita nr.: " + invoice.getInvoiceNumber());
        loadRentItems(invoice);
    }

    private void checkToEnableButtons() {
        if (checkBox.isSelected()) {
            if ((itemName.getText().isEmpty() || measuringUnit.getText().isEmpty() || quantity.getText().isEmpty() || amount.getText().isEmpty()
                    || !CheckDouble.checkString(quantity.getText()) || !CheckDouble.checkString(amount.getText()))) {
                insertItem.setDisable(true);
            } else {
                insertItem.setDisable(false);
            }
        } else if (prepUnit.getText().isEmpty() || prepQuantity.getText().isEmpty() || prepPrice.getText().isEmpty()
                || !CheckDouble.checkString(prepQuantity.getText()) || !CheckDouble.checkString(prepPrice.getText())) {
            insertItem.setDisable(true);
        } else {
            insertItem.setDisable(false);
        }
    }

    private void highlightIncorrect() {
        if (!CheckDouble.checkString(quantity.getText())) {
            quantity.setStyle(redTextColor);
        } else {
            quantity.setPromptText(null);
            quantity.setStyle(blackTextColor);
        }

        if (!CheckDouble.checkString(amount.getText())) {
            amount.setStyle(redTextColor);
        } else {
            amount.setStyle(blackTextColor);
        }

        if (!CheckDouble.checkString(prepQuantity.getText())) {
            prepQuantity.setStyle(redTextColor);
        } else {
            prepQuantity.setStyle(blackTextColor);
        }

        if (!CheckDouble.checkString(prepPrice.getText())) {
            prepPrice.setStyle(redTextColor);
        } else {
            prepPrice.setStyle(blackTextColor);
        }
    }

    public void uploadPrepFields() {
        RentItem item = choiceBox.getSelectionModel().getSelectedItem();
        prepUnit.setText(item.getMeasuringUnit());
        prepQuantity.setText("1");
        prepPrice.setText(String.valueOf(item.getPrice()));
    }


    public void loadRentItems(Invoice invoice) {
        this.invoice = invoice;
        tableView.setItems(FXCollections.observableArrayList(invoice.getList()));
        double totalAmount = invoice.getTotalAmount();
        double vatAmount = Math.round((totalAmount * 0.21) * 100.00) / 100.00;
        double sum = Math.round((totalAmount + vatAmount) * 100.00) / 100.00;
        totalAmountLabel.setText(String.valueOf(totalAmount));
        vatLabel.setText(String.valueOf(vatAmount));
        sumLabel.setText(String.valueOf(sum));
    }

    public void insertItem() {
        String name;
        String unit;
        double qty;
        double value;
        if (checkBox.isSelected()) {
            name = itemName.getText();
            unit = measuringUnit.getText();
            qty = Double.parseDouble(quantity.getText());
            value = Double.parseDouble(amount.getText());
            itemName.clear();
            measuringUnit.clear();
            quantity.clear();
            amount.clear();
        } else {
            RentItem item = choiceBox.getSelectionModel().getSelectedItem();
            name = item.getItemName();
            unit = item.getMeasuringUnit();
            qty = Double.parseDouble(prepQuantity.getText());
            value = Double.parseDouble(prepPrice.getText());
        }

        int itemId = ClientData.getInstance().insertRentItem(name, unit, qty, value, invoice.getInvoiceId());
        if (itemId != -1) {
            RentItem item = new RentItem(name, unit, qty, value);
            item.setRentItemId(itemId);
            invoice.addRentItem(item);
            loadRentItems(invoice);
        }
        state = true;

    }

    public void deleteItem() {
        RentItem item = tableView.getSelectionModel().getSelectedItem();
        if (ClientData.getInstance().deleteRentItem(item)) {
            invoice.removeRentItem(item);
            loadRentItems(invoice);
        }
        state = true;
    }

    @FXML
    private void createDoc() {
        Task<ObservableList<Void>> task = new Task<ObservableList<Void>>() {
            @Override
            protected ObservableList<Void> call() throws Exception {
                DocFormer.getInstance().formWordDocument(invoice, ClientData.getInstance().getCompanyList().get(0));
                Thread.sleep(500);
                return null;
            }
        };
        progressIndicator.setVisible(true);
        progressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> progressIndicator.setVisible(false));
    }

    public boolean getState() {
        return state;
    }

    @FXML
    public void closePane() {
        stage.close();
    }
}
