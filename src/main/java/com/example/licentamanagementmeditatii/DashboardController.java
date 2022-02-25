package com.example.licentamanagementmeditatii;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private DatePicker calendar_interval;
    @FXML
    private Button button_interval;
    @FXML
    private Button button_manage_students;
    @FXML
    private Button button_manage_sessions;
    @FXML
    private Label label_lunare;
    @FXML
    private Label label_neplatite;
    @FXML
    private Label label_platite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populate_statistici();

        button_manage_students.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changeScene(event, "Students.fxml","Log in!", null);
            }
        });
        button_manage_sessions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changeScene(event, "Sessions.fxml", "Sessions",null);
            }
        });

        button_interval.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             //   CurrentUser.current_date = Date.valueOf(calendar_interval.getValue());
                LocalDate value = calendar_interval.getValue();
                value = value.minusDays(value.getDayOfMonth()-1);
                Calendar cal = Calendar.getInstance();
                cal.set(value.getYear(), value.getMonthValue(), value.getDayOfMonth());
                int res = cal.getActualMaximum(Calendar.DATE);
                CurrentUser.current_date = Date.valueOf(value);
                value = value.plusDays(res-value.getDayOfMonth());
                CurrentUser.endMonth = Date.valueOf(value);
                    Utils.changeScene(event, "Dashboard.fxml","Log in!", null);
                }


        });

    }

    private void populate_statistici() {
        List<Session> sessions = Utils.getAllSessions().stream().toList();
        double total = 0;
        double platite = 0;
        double neplatite = 0;

        for(Session s:sessions){
            total = total +s.getPret_total();
            if(s.getPlatit() == 1){
                platite = platite + s.getPret_total();
            }
        }
        neplatite = total - platite;

        label_lunare.setText(String.valueOf(total)+" RON");
        label_platite.setText(String.valueOf(platite)+" RON");
        label_neplatite.setText(String.valueOf(neplatite)+" RON");
    }


}


