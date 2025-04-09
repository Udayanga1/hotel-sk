package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Customer;
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
        if (txtDiscount.getText().isEmpty()){
            txtDiscount.setText("0");
        }

        if (!txtInvoiceNo.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please clear the fields before making a new payment").show();
        } else {
            String isPaymentMade = billingBo.makePayment(
                    new Payment(
                            null,
                            Integer.parseInt(txtReservationId.getText()),
                            cmbPmtMethod.getValue().toString(),
                            txtPayDate.getValue(),
                            Double.parseDouble(txtTotalDue.getText()),
                            Double.parseDouble(txtDiscount.getText()),
                            Double.parseDouble(txtTaxes.getText())
                    )
            );
            if (isPaymentMade!=null) {
                if (isPaymentMade.equals("already paid")){
                    new Alert(Alert.AlertType.ERROR, "Reservation is already paid..!!").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Made!!").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Made!!").show();

            }

//            loadTable();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearInputFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!txtInvoiceNo.getText().isEmpty()){
            Payment payment = new Payment();
            payment.setReservationNo(Integer.parseInt(txtReservationId.getText()));
            payment.setId(Integer.parseInt(txtInvoiceNo.getText()));
            boolean isPaymentDeleted = billingBo.deletePayment(payment);

            if (isPaymentDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Deleted!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Deleted!!").show();

            }
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (!txtReservationId.getText().isEmpty()){
            Payment payment = billingBo.searchPayment(Integer.parseInt(txtReservationId.getText()));

            if (payment!=null) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Found!!").show();
                System.out.println("BillingFormController:  " + payment);
                txtInvoiceNo.setText(String.valueOf(payment.getId()));
                cmbPmtMethod.setValue(payment.getType());
                txtPayDate.setValue(payment.getPayDate());
                txtDiscount.setText(String.valueOf(payment.getDiscount()));
                txtTaxes.setText(String.valueOf(payment.getTax()));
                txtTotalDue.setText(String.valueOf(payment.getTotalDue()));

            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Found!!").show();
                clearInputFields();
            }
        }
    }


    @FXML
    public void initialize() {
        Double taxRate = 0.1;
        txtReservationId.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {

                Integer reservationNo = Integer.parseInt(txtReservationId.getText());
                Payment payment = billingBo.getDetails(reservationNo);

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

    private void clearInputFields(){
//        txtReservationId.setText("");
//        txtCusID.setText("");
//        txtCusName.setText("");
//        txtRoomNo.setText("");
//        txtCheckInDate.setValue(null);
//        txtCheckOutDate.setValue(null);
        txtInvoiceNo.setText("");
        txtReservationId.setText("");
        cmbPmtMethod.setValue(null);
        txtPayDate.setValue(null);
        txtDiscount.setText("");
        txtTaxes.setText("");
        txtTotalDue.setText("");
    }

}
