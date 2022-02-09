package com.example.licentamanagementmeditatii;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;

import java.util.ResourceBundle;
public class SessionsController implements Initializable {
    @FXML
    private ComboBox<Integer> combobox_durata;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox_durata.getItems().add(60);
        combobox_durata.getItems().add(90);
        combobox_durata.getItems().add(120);
    }
}
