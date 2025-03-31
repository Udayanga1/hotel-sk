package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import model.Reservation;
import model.Room;
import service.BoFactory;
import service.custom.ReservationBo;
import service.custom.RoomBo;
import util.BoType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        if (txtCusName.getText().length()>0) {
            System.out.println("availabe cus");
        } else {
            System.out.println("Unavailable");
        }
        if (txtReservationId.getText().length()>0) {
            new Alert(Alert.AlertType.ERROR, "Please clear the fields before adding a new reservation").show();
        } else if (txtCusID.getText().length()==0 ||
                txtCusName.getText().length()==0 ||
                txtRoomNo.getText().length()==0 ||
                txtCheckInDate.getValue()==null ||
                txtCheckOutDate.getValue()==null ||
                txtCheckInDate.getValue().isAfter(txtCheckOutDate.getValue())
        ) {
            new Alert(Alert.AlertType.ERROR, "Please input valid data!!").show();
        } else {
            boolean isReservationAdd = reservationBo.addReservation(
                new Reservation(
                        null,
                        Integer.parseInt(txtCusID.getText()),
                        Integer.parseInt(txtRoomNo.getText()),
                        txtCheckInDate.getValue(),
                        txtCheckInDate.getValue()
                )
            );
            if (isReservationAdd) {
                reservationBo.makeRoomOccupied(Integer.parseInt(txtRoomNo.getText()));
                long daysBetween = ChronoUnit.DAYS.between(txtCheckInDate.getValue(), txtCheckOutDate.getValue());
                Double totalAmount = reservationBo.getTotalAmount(daysBetween, Integer.parseInt(txtRoomNo.getText()));

                txtTotalAmount.setText("Rs " + totalAmount);
//                System.out.println(totalAmount);

                new Alert(Alert.AlertType.INFORMATION, "Reservation Added!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Reservation Not Added!!").show();

            }

//            loadTable();
//            clearInputFields();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        if (txtCheckInDate.getValue() != null && txtCheckOutDate.getValue() != null) {
            long daysBetween = ChronoUnit.DAYS.between(txtCheckInDate.getValue(), txtCheckOutDate.getValue());
            System.out.println("Number of days: " + daysBetween);
        } else {
            System.out.println("Please select both check-in and check-out dates.");
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

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

        txtRoomNo.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String roomStatus = reservationBo.getRoomStatus(Integer.parseInt(txtRoomNo.getText()));
                if (roomStatus==null){
                    new Alert(Alert.AlertType.ERROR, "Room: " + txtRoomNo.getText() +" is not Available!!").show();
                    txtRoomNo.setText("");
                } else if (roomStatus.equals("Occupied")) {
                    new Alert(Alert.AlertType.ERROR, "Room: " + txtRoomNo.getText() +" is already Occupied!!").show();
                    txtRoomNo.setText("");
                }
            }
        });

    }




}
