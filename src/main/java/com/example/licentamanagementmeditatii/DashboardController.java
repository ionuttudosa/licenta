package com.example.licentamanagementmeditatii;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;

import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Button button_manage_students;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_manage_students.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changeScene(event, "Students.fxml","Log in!", null);
            }
        });

    }


    }


