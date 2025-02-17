package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.User;

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

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {
        String SQL = "INSERT INTO users (username, email, password) VALUES (?,?,?)";

        if(txtPassword.getText().equals(txtConfirmPassword.getText())){

            Connection connection = DBConnection.getInstance().getConnection();

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT email FROM users WHERE email="+ "'"+ txtEmail.getText() +"'");

            if (!resultSet.next()){
                User user = new User(txtUserName.getText(), txtEmail.getText(), txtPassword.getText());

                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1, user.getUserName());
                psTm.setString(2, user.getEmail());
                psTm.setString(3, user.getPassword());
                psTm.executeUpdate();

                txtUserName.setText("");
                txtEmail.setText("");
                txtPassword.setText("");
                txtConfirmPassword.setText("");

                new Alert(Alert.AlertType.CONFIRMATION, "User has been registered").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "A user has already been registered with this email").show();
            }


        } else {
            new Alert(Alert.AlertType.ERROR, "Check your password").show();
        }

    }

}
