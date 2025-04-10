package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import service.BoFactory;
import service.custom.UserBo;
import util.BoType;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    UserBo userBo = BoFactory.getInstance().getBoType(BoType.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        User user = new User();
        user.setEmail(txtEmail.getText());
        user.setPassword(txtPassword.getText());
        User userReceived = userBo.searchUser(user);
        if (userReceived!=null){
//            new Alert(Alert.AlertType.INFORMATION, "Welcome " + userReceived.getUserName() + " !!!").show();

            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
                stage.show();

                // Close login window
                Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect User Credentials!!").show();
            txtPassword.setText("");
        }
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

    @FXML
    public void initialize() {
        Platform.runLater(() -> btnLogin.requestFocus());
    }

}
