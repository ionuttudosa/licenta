package com.example.licentamanagementmeditatii;
import javafx.collections.ObservableList;
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
    @FXML
    private ComboBox<String> combobox_student;
    @FXML
    private Button button_logout;
    @FXML
    private Button button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox_durata.getItems().add(60);
        combobox_durata.getItems().add(90);
        combobox_durata.getItems().add(120);

        ObservableList<UseriCati> students = Utils.getDatausers();
        for (UseriCati student:students) {
            combobox_student.getItems().add(student.getId()+" "+student.getNume()+" "+student.getPrenume());
        }




        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                search_user();
//                UpdateTable();
                Utils.changeScene(event, "Landing.fxml", "Welcome!", null);
            }
        });
////but de a merge inapoi la pagina cu aplicatii.

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                search_user();
//                UpdateTable();
                Utils.changeScene(event, "Dashboard.fxml", "Welcome!", null);
            }

        });

    }





}
