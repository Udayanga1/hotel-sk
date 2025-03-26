package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {

    @FXML
    private Button btnColBillingtBtn;

    @FXML
    private Button btnColCheckInOutMgtBtn;

    @FXML
    private Button btnColRCustomerMgtBtn;

    @FXML
    private Button btnColResMgtBtn;

    @FXML
    private Button btnColRoomMgtBtn;

    @FXML
    private AnchorPane loadFormContent;

    @FXML
    void btnBillingOnAction(ActionEvent event) {

    }

    @FXML
    void btnCheckInCheckOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerMgtOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/customer_form.fxml");

        assert resource !=null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

        setBtnBackgroundCol(btnColRCustomerMgtBtn);
    }

    @FXML
    void btnReservationMgtOnAction(ActionEvent event) {

    }

    @FXML
    void btnRoomMgtOnAction(ActionEvent event) {

    }

    private void setBtnBackgroundCol(Button button){

        btnColBillingtBtn.setStyle("-fx-background-color: #C56B82;");
        btnColCheckInOutMgtBtn.setStyle("-fx-background-color: #C56B82;");
        btnColRCustomerMgtBtn.setStyle("-fx-background-color: #C56B82;");
        btnColResMgtBtn.setStyle("-fx-background-color: #C56B82;");
        btnColRoomMgtBtn.setStyle("-fx-background-color: #C56B82;");

        button.setStyle("-fx-background-color: #253494;");
    }

}
