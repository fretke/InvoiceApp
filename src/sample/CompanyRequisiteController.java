package sample;

import Data.Company;
import Data.Employee;
import Data.ClientData;
import animatefx.animation.ZoomIn;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class CompanyRequisiteController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Button saveButton;
    @FXML
    private Circle closeButton;

    @FXML
    private TextField name;
    @FXML
    private TextField code;
    @FXML
    private TextField vatNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField bankAccountNumber;
    @FXML
    private TextField bankName;
    @FXML
    private TextField bankCode;
    @FXML
    private ChoiceBox<Employee> employees;

    @FXML
    private BorderPane secondaryPane;
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private Circle switchPaneCircle;
    @FXML
    private Button insert;
    @FXML
    private Button delete;
    @FXML
    private TextField employeeName;
    @FXML
    private TextField employeePosition;

    private Company company;
    private Stage stage;
    private double xOffSet;
    private double yOffSet;


    public void initialize() {
        secondaryPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        secondaryPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });
        mainPane.setOnMousePressed(event -> {
            xOffSet = stage.getX() - event.getScreenX();
            yOffSet = stage.getY() - event.getScreenY();
        });

        mainPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffSet);
            stage.setY(event.getScreenY() + yOffSet);
        });
        insert.setDisable(true);
        delete.setDisable(true);
        secondaryPane.setOnKeyReleased(event -> {
            if (employeeName.getText().isEmpty() || employeePosition.getText().isEmpty()) {
                insert.setDisable(true);
            } else {
                insert.setDisable(false);
            }
        });
        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                delete.setDisable(false);
            } else {
                delete.setDisable(true);
            }
        });
        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("Daugiau..");
        item.setOnAction(event -> {
            ZoomIn zoom = new ZoomIn(secondaryPane);
            zoom.setSpeed(2).play();
            secondaryPane.toFront();
            this.stage = (Stage) switchPaneCircle.getScene().getWindow();
        });
        switchPaneCircle.setOnMouseClicked(event -> {
            mainPane.toFront();
            stage = (Stage) closeButton.getScene().getWindow();

        });
        menu.getItems().add(item);
        employees.setContextMenu(menu);
        mainPane.setOnKeyReleased(event -> {
            if (name.getText().isEmpty() || code.getText().isEmpty() || vatNumber.getText().isEmpty() || address.getText().isEmpty() ||
                    bankAccountNumber.getText().isEmpty() || bankName.getText().isEmpty() || bankCode.getText().isEmpty()) {
                saveButton.setDisable(true);
            } else {
                saveButton.setDisable(false);
            }
        });
    }

    @FXML
    public void insertEmployee() {
        Employee employee = new Employee(employeeName.getText(), employeePosition.getText(), false);
        ClientData.getInstance().insertEmployee(employee);
        reloadPane();
    }

    @FXML
    public void deleteEmployee() {
        ClientData.getInstance().deleteEmployee(tableView.getSelectionModel().getSelectedItem());
        reloadPane();
    }

    @FXML
    public void loadPane() {
        stage = (Stage) closeButton.getScene().getWindow();
        this.company = ClientData.getInstance().getCompanyList().get(0);
        name.setText(company.getName());
        code.setText(company.getCode());
        vatNumber.setText(company.getVatNumber());
        address.setText(company.getAddress());
        bankAccountNumber.setText(company.getAccountNumber());
        bankName.setText(company.getBankName());
        bankCode.setText(company.getBankCode());
        employees.getItems().setAll(company.getList());
        setCurrentEmployee();
        tableView.getItems().setAll(company.getList());
    }

    private void reloadPane() {
        this.company = ClientData.getInstance().getCompanyList().get(0);
        employees.getItems().setAll(company.getList());
        setCurrentEmployee();
        tableView.getItems().setAll(company.getList());
    }

    private void setCurrentEmployee() {
        for (Employee employee : company.getList()) {
            if (employee.isSelected()) {
                employees.getSelectionModel().select(employee);
                break;
            }
        }
    }

    @FXML
    private void closePane() {
        //   Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void updateCompany() {
        Company updatedCompany = new Company(name.getText(), code.getText(), vatNumber.getText(), address.getText(),
                bankAccountNumber.getText(), bankName.getText(), bankCode.getText());
        ClientData.getInstance().updateCompany(updatedCompany);
        Employee employee = employees.getSelectionModel().getSelectedItem();
        if (!employee.isSelected()) {
            ClientData.getInstance().updateSelected(employee);
        }
        closePane();
    }


}
