package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.User;

import javafx.scene.input.MouseEvent;
import service.BoFactory;
import service.custom.CustomerBo;
import service.custom.UserBo;
import util.BoType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

//        String SQL = "INSERT INTO users (username, email, password) VALUES (?,?,?)";
//
//        if(txtPassword.getText().equals(txtConfirmPassword.getText())){
//
//            Connection connection = DBConnection.getInstance().getConnection();
//
//            ResultSet resultSet = connection.createStatement().executeQuery("SELECT email FROM users WHERE email="+ "'"+ txtEmail.getText() +"'");
//
//            if (!resultSet.next()){
//                User user = new User(txtUserName.getText(), txtEmail.getText(), txtPassword.getText());
//
//                PreparedStatement psTm = connection.prepareStatement(SQL);
//                psTm.setString(1, user.getUserName());
//                psTm.setString(2, user.getEmail());
//                psTm.setString(3, user.getPassword());
//                psTm.executeUpdate();
//
//                txtUserName.setText("");
//                txtEmail.setText("");
//                txtPassword.setText("");
//                txtConfirmPassword.setText("");
//
//                new Alert(Alert.AlertType.CONFIRMATION, "User has been registered").show();
//
//            } else {
//                new Alert(Alert.AlertType.ERROR, "A user has already been registered with this email").show();
//            }
//
//
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Check your password").show();
//        }

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
