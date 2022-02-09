package com.example.licentamanagementmeditatii;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button button_signup;
    @FXML
    private  Button button_back;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_nume;
    @FXML
    private TextField tf_prenume;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_telefon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//buton de inregistrare user nou, apeleaza metoda din DBUtils.
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    Utils.signUpUser(event, tf_username.getText(), tf_password.getText(), tf_nume.getText(), tf_prenume.getText(), tf_email.getText(), tf_telefon.getText());
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up");
                    alert.show();
                }
            }
        });
//but pentru pagina de login in caz ca butonul de sign up a fost apasat din greseala
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changeScene(event, "Landing.fxml","Log in!", null);
            }
        });

    }
}
