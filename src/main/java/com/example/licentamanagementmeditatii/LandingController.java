package com.example.licentamanagementmeditatii;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingController implements Initializable {
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_login;
    @FXML
    private Button button_signup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.logInUser(event, tf_username.getText(), tf_password.getText());
            }
        });
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changeScene(event,"SignUp.fxml","welcomeeeee", null);
            }
        });
    }


    public TextField getTf_username() {
        return tf_username;
    }

    public TextField getTf_password() {
        return tf_password;
    }

    public void setTf_username(TextField tf_username) {
        this.tf_username = tf_username;
    }

    public void setTf_password(TextField tf_password) {
        this.tf_password = tf_password;
    }
}
