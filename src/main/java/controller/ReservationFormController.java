package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReservationFormController {

    @FXML
    private TableColumn colCheckIn;

    @FXML
    private TableColumn colCheckOut;

    @FXML
    private TableColumn colCusName;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colRoomNo;

    @FXML
    private TableView tblReservation;

    @FXML
    private TextField txtCheckInDate;

    @FXML
    private TextField txtCheckOutDate;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtRoomNo;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
