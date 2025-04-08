package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Payment;
import service.BoFactory;
import service.custom.BillingBo;
import util.BoType;

public class BillingFormController {

    @FXML
    private ComboBox cmbPmtMethod;

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
    private TableView tblBilling;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtInvoiceNo;

    @FXML
    private DatePicker txtPayDate;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtTaxes;

    @FXML
    private TextField txtTotalDue;

    BillingBo billingBo = BoFactory.getInstance().getBoType(BoType.BILL);

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

    @FXML
    public void initialize() {
        Double taxRate = 0.1;
        txtReservationId.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {

                Integer reservationNo = Integer.parseInt(txtReservationId.getText());
                Payment payment = billingBo.getDetails(reservationNo);
                System.out.println("payment received to controller: " + payment);

//                txtDiscount.focusedProperty().addListener((observable, oldValue, newValue) -> {
//                    if (!newValue) {
//
//                    }
//                });

                if (payment!=null){
                    Double totalPerReservation = payment.getTotalDue();

                    txtDiscount.focusedProperty().addListener((observable2, oldValue2, newValue2) -> {
                        if (!newValue2) {
                            Double discount = Double.parseDouble(txtDiscount.getText());
                            Double tax = (totalPerReservation - discount)*taxRate;
                            txtTaxes.setText(String.valueOf(tax));
                            txtTotalDue.setText(String.valueOf(totalPerReservation + tax - discount));
                        }
                    });

                    if (txtDiscount.getText().length()==0){
                        Double tax = totalPerReservation*taxRate;
                        txtTaxes.setText(String.valueOf(tax));
                        txtTotalDue.setText(String.valueOf(totalPerReservation + tax));
                    } else if (txtDiscount.getText().length() > 0) {
                        Double discount = Double.parseDouble(txtDiscount.getText());
                        Double tax = (totalPerReservation - discount)*taxRate;
                        txtTaxes.setText(String.valueOf(tax));
                        txtTotalDue.setText(String.valueOf(totalPerReservation + tax - discount));
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "Reservation is not available!!!").show();
                }

            }

        });

//        loadTable();
    }

}
