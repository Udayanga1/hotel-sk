package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        System.out.println("btnLoginOnAction");
    }

    @FXML
    void txtCreateAccountOnMouseReleased(MouseEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/register_form.fxml"))));
            stage.show();

            // Close login window
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
