package com.example.licentamanagementmeditatii;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
    @FXML
    private TableView<Session> table_nePlatit;

    @FXML
    private TableColumn<Session, String> column_materie;
    @FXML
    private TableColumn<Session, String> column_id_student;
    @FXML
    private TableColumn<Session, String> column_student;
    @FXML
    private TableColumn<Session, Integer> column_pret_per_ora;
    @FXML
    private TableColumn<Session, Integer> column_durata;
    @FXML
    private TableColumn<Session, Date> column_data;
    @FXML
    private TableColumn<Session, Double> column_pret_total;
    @FXML
    private Label label_total_neplatit;

    ObservableList<Session> sessions;
    public void UpdateTableNePlatit(){
        //column_id.setCellValueFactory(new PropertyValueFactory<Session, Integer>("ID"));
        column_materie.setCellValueFactory(new PropertyValueFactory<Session, String>("materie"));
        column_id_student.setCellValueFactory(new PropertyValueFactory<Session, String>("idStudent"));
        column_student.setCellValueFactory(new PropertyValueFactory<Session, String>("student_nume"));
        column_pret_per_ora.setCellValueFactory(new PropertyValueFactory<Session, Integer>("pret_per_ora"));
        column_durata.setCellValueFactory(new PropertyValueFactory<Session, Integer>("durata"));
        column_data.setCellValueFactory(new PropertyValueFactory<Session, Date>("date"));
        column_pret_total.setCellValueFactory(new PropertyValueFactory<Session, Double>("pret_total"));

        sessions = Utils.getPendingSessions();
        table_nePlatit.setItems(sessions);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double sum = Utils.getPendingSessions().stream().toList().stream().mapToDouble(x -> x.getPret_total()).sum();
        label_total_neplatit.setText((sum+" RON"));
        populate_statistici();
        UpdateTableNePlatit();
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


