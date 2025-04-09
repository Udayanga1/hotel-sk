package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reservation;
import service.BoFactory;
import service.custom.ReservationBo;
import util.BoType;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class ReservationFormController {

    @FXML
    private TableColumn colCheckIn;

    @FXML
    private TableColumn colCheckOut;

    @FXML
    private TableColumn colCusId;

    @FXML
    private TableColumn colResId;

    @FXML
    private TableColumn colRoomNo;

    @FXML
    private TableView tblReservation;

    @FXML
    private DatePicker txtCheckInDate;

    @FXML
    private DatePicker txtCheckOutDate;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtRoomNo;

    @FXML
    private TextField txtTotalAmount;

    ReservationBo reservationBo = BoFactory.getInstance().getBoType(BoType.RESERVATION);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtReservationId.getText().length()>0) {
            new Alert(Alert.AlertType.ERROR, "Please clear the fields before adding a new reservation").show();
        } else if (txtCusID.getText().length()==0 ||
                txtCusName.getText().length()==0 ||
                txtRoomNo.getText().length()==0 ||
                txtCheckInDate.getValue()==null ||
                txtCheckOutDate.getValue()==null ||
                txtCheckInDate.getValue().isAfter(txtCheckOutDate.getValue()) ||
                !Objects.equals(reservationBo.getRoomStatus(Integer.parseInt(txtRoomNo.getText())), "Available")
        ) {
            new Alert(Alert.AlertType.ERROR, "Please input valid data!!").show();
        } else {
            boolean isReservationAdd = reservationBo.addReservation(
                new Reservation(
                        null,
                        Integer.parseInt(txtCusID.getText()),
                        Integer.parseInt(txtRoomNo.getText()),
                        txtCheckInDate.getValue(),
                        txtCheckOutDate.getValue()
                )
            );
            if (isReservationAdd) {
                reservationBo.makeRoomOccupied(Integer.parseInt(txtRoomNo.getText()));
                displayTotalAmount();
                new Alert(Alert.AlertType.INFORMATION, "Reservation Added!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Reservation Not Added!!").show();

            }

            loadTable();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearInputFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String reservationStatus = reservationBo.deleteReservation(Integer.parseInt(txtReservationId.getText()));

        if (reservationStatus != null) {
            if(reservationStatus.equals("paid")){
                new Alert(Alert.AlertType.ERROR, "Reservation is paid.. Remove payment before delete!!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Reservation Deleted!!").show();
                displayTotalAmount();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Reservation Not Deleted!!").show();
        }

        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Reservation reservationSearching = new Reservation(
                null,
                Integer.parseInt(txtCusID.getText()),
                Integer.parseInt(txtRoomNo.getText()),
                txtCheckInDate.getValue(),
                txtCheckOutDate.getValue()
        );

        Reservation reservation = reservationBo.searchReservation(reservationSearching);
        if (reservation!=null) {
            new Alert(Alert.AlertType.INFORMATION, "Reservation Found!!").show();
            txtReservationId.setText(String.valueOf(reservation.getReservationId()));
            txtCusID.setText(String.valueOf(reservation.getCustomerId()));
            txtRoomNo.setText(String.valueOf(reservation.getRoomNo()));
            txtCheckInDate.setValue(reservation.getCheckInDate());
            txtCheckOutDate.setValue(reservation.getCheckOutDate());

            displayTotalAmount();

        } else {
            new Alert(Alert.AlertType.ERROR, "Reservation Not Found!!").show();
            txtReservationId.setText("");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String reservationStatus = reservationBo.updateReservation(
                new Reservation(
                        Integer.parseInt(txtReservationId.getText()),
                        Integer.parseInt(txtCusID.getText()),
                        Integer.parseInt(txtRoomNo.getText()),
                        txtCheckInDate.getValue(),
                        txtCheckOutDate.getValue()
                )
        );
        if (reservationStatus != null) {
            if(reservationStatus.equals("paid")){
                new Alert(Alert.AlertType.ERROR, "Reservation is paid.. Remove payment before update!!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Reservation Updated!!").show();
                displayTotalAmount();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Reservation Not Updated!!").show();
        }

        loadTable();
    }

    @FXML
    public void initialize() {
        txtCusID.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String cusName = reservationBo.getCustomerName(Integer.parseInt(txtCusID.getText()));
                if (cusName!=null){
                    txtCusName.setText(cusName);
                } else {
                    txtCusName.setText("");
                    new Alert(Alert.AlertType.ERROR, "Customer Not Available!!").show();
                }

            }
        });

        loadTable();
    }

    private void clearInputFields(){
        txtReservationId.setText("");
        txtCusID.setText("");
        txtCusName.setText("");
        txtRoomNo.setText("");
        txtCheckInDate.setValue(null);
        txtCheckOutDate.setValue(null);
    }

    private void loadTable(){
        colResId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

        ArrayList<Reservation> reservationArrayList = new ArrayList<>();
        ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();

        reservationBo.getAll().forEach(reservation -> {
            reservationArrayList.add(reservation);
        });


        reservationArrayList.forEach(room -> {
            reservationObservableList.add(room);
        });

        tblReservation.setItems(reservationObservableList);
    }

    private void displayTotalAmount(){
        long daysBetween = ChronoUnit.DAYS.between(txtCheckInDate.getValue(), txtCheckOutDate.getValue());
        Double totalAmount = reservationBo.getTotalAmount(daysBetween, Integer.parseInt(txtRoomNo.getText()));

        txtTotalAmount.setText("Rs " + totalAmount);
    }




}
