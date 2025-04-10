package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import service.BoFactory;
import service.custom.UserBo;
import util.BoType;

import java.io.IOException;

public class RegisterFormController {

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    UserBo userBo = BoFactory.getInstance().getBoType(BoType.USER);

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        String userName = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "All fields are required!").show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format!").show();
            return;
        }

        try {
            boolean isUserAdded = userBo.addUser(new User(userName, email, password));

            if (isUserAdded) {
                new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully!").show();

                // Redirect to Login Form
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
                loginStage.show();

            } else {
                new Alert(Alert.AlertType.ERROR, "User registration failed! Email might already be taken.").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void txtLoginOnMouseRelease(MouseEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
            stage.show();

            // Close login window
            Stage registerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            registerStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
