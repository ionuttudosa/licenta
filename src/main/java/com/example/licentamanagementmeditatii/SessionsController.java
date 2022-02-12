package com.example.licentamanagementmeditatii;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;

import java.sql.Date;
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
    @FXML
    private Button button_add;
    @FXML
    private Button button_update;
    @FXML
    private Button button_delete;

    @FXML
    private TextField tf_materie;
    @FXML
    private TextField tf_pret_per_ora;
    @FXML
    private DatePicker datepicker_calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox_durata.getItems().add(60);
        combobox_durata.getItems().add(90);
        combobox_durata.getItems().add(120);

        ObservableList<UseriCati> students = Utils.getDatausers();
        for (UseriCati student:students) {
            combobox_student.getItems().add(student.getId()+" "+student.getNume()+" "+student.getPrenume());
        }

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(tf_materie.getText());
                System.out.println(tf_pret_per_ora.getText());
                System.out.println(combobox_student.getValue().substring(0,combobox_student.getValue().indexOf(" ")));
                System.out.println(combobox_durata.getValue());
                System.out.println(datepicker_calendar.getValue());
                Session s = new Session(tf_materie.getText(),
                        CurrentUser.id,
                        Integer.parseInt(combobox_student.getValue().substring(0,combobox_student.getValue().indexOf(" "))),
                        Integer.parseInt(tf_pret_per_ora.getText()),
                        combobox_durata.getValue(),
                        Date.valueOf(datepicker_calendar.getValue()));
                Utils.AddSession(s);
            }
        });





        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                search_user();
//                UpdateTable();
                Utils.changeScene(event, "Landing.fxml", "Welcome!", null);
            }
        });
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
