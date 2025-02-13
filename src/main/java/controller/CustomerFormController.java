package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;

import java.sql.*;
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
    private AnchorPane txtCustimerContactNo;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtCustomerName;

    @FXML
    void btnCustomerAdd(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnCustomerDelete(ActionEvent event) {

    }

    @FXML
    void btnCustomerSearch(ActionEvent event) {

    }

    @FXML
    void btnCustomerUpdate(ActionEvent event) {

    }

    private void loadTable(){
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();


        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                customerArrayList.add(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerArrayList.forEach(customer -> {
            customerObservableList.add(customer);
        });

        tblCustomers.setItems(customerObservableList);
    }

}
