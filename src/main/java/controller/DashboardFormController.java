package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {

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
    }

    @FXML
    void btnReservationMgtOnAction(ActionEvent event) {

    }

    @FXML
    void btnRoomMgtOnAction(ActionEvent event) {

    }

}
