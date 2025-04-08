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
    private Button btnLoadBillingt;

    @FXML
    private Button txtLogout;

    @FXML
    private Button btnLoadRCustomerMgt;

    @FXML
    private Button btnLoadReservationMgt;

    @FXML
    private Button btnLoadRoomMgt;

    @FXML
    private AnchorPane loadFormContent;

    @FXML
    void btnBillingOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/billing_form.fxml");

        assert resource !=null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

        setBtnBackgroundCol(btnLoadBillingt);
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

        setBtnBackgroundCol(btnLoadRCustomerMgt);
    }

    @FXML
    void btnReservationMgtOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/reservation_form.fxml");

        assert resource !=null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

        setBtnBackgroundCol(btnLoadReservationMgt);
    }

    @FXML
    void btnRoomMgtOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/room_form.fxml");

        assert resource !=null;

        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

        setBtnBackgroundCol(btnLoadRoomMgt);

    }

    private void setBtnBackgroundCol(Button button){

        btnLoadBillingt.setStyle("-fx-background-color: #C56B82;");
        btnLoadRCustomerMgt.setStyle("-fx-background-color: #C56B82;");
        btnLoadReservationMgt.setStyle("-fx-background-color: #C56B82;");
        btnLoadRoomMgt.setStyle("-fx-background-color: #C56B82;");

        button.setStyle("-fx-background-color: #253494;");
    }

}
