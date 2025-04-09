package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import service.BoFactory;
import service.custom.CustomerBo;
import util.BoType;

import java.util.ArrayList;

public class CustomerFormController {

    @FXML
    private TableColumn colCustomerContactNo;

    @FXML
    private TableColumn colCustomerID;

    @FXML
    private TableColumn colCustomerName;

    @FXML
    private TableView tblCustomers;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtCustomerName;

    CustomerBo customerBo = BoFactory.getInstance().getBoType(BoType.CUSTOMER);


    @FXML
    void btnCustomerAdd(ActionEvent event) {
        if (txtCustomerID.getText().length()>0){
            new Alert(Alert.AlertType.ERROR, "Please clear the fields before adding a new customer").show();
        } else {
            boolean isCustomerAdd = customerBo.addCustomer(
                    new Customer(
                            null,
                            txtCustomerName.getText(),
                            txtContactNo.getText()
                    )
            );
            if (isCustomerAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Added!!").show();

            }

            loadTable();
        }

    }

    @FXML
    void btnCustomerDelete(ActionEvent event) {
        boolean isCustomerDeleted = customerBo.deleteCustomer(
                new Customer(
                        Integer.parseInt(txtCustomerID.getText()),
                        txtCustomerName.getText(),
                        txtContactNo.getText()
                )
        );
        if (isCustomerDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Deleted!!").show();

        }

        loadTable();
    }

    @FXML
    void btnCustomerSearch(ActionEvent event) {
        Customer customerSearching = new Customer(
                null,
                txtCustomerName.getText(),
                txtContactNo.getText()
        );

        Customer customer = customerBo.searchCustomer(customerSearching);
        if (customer!=null) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Found!!").show();
            txtCustomerID.setText(String.valueOf(customer.getId()));
            txtCustomerName.setText(customer.getName());
            txtContactNo.setText(customer.getContactNumber());
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Found!!").show();
            txtCustomerID.setText("");
        }

    }

    @FXML
    void btnCustomerUpdate(ActionEvent event) {
        boolean isCustomerUpdated = customerBo.updateCustomer(
                new Customer(
                        Integer.parseInt(txtCustomerID.getText()),
                        txtCustomerName.getText(),
                        txtContactNo.getText()
                )
        );
        if (isCustomerUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Updated!!").show();

        }

        loadTable();
    }

    @FXML
    private void btnClear(ActionEvent event) {
        txtCustomerID.setText("");
        txtCustomerName.setText("");
        txtContactNo.setText("");
    }

    private void loadTable(){
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        customerBo.getAll().forEach(customer -> {
            customerArrayList.add(customer);
        });

        customerArrayList.forEach(customer -> {
            customerObservableList.add(customer);
        });

        tblCustomers.setItems(customerObservableList);
    }

    @FXML
    public void initialize(){
        loadTable();
    }



}
