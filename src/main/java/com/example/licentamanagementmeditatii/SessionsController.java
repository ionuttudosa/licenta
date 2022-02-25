package com.example.licentamanagementmeditatii;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.sql.Date;
import java.util.ResourceBundle;
public class SessionsController implements Initializable {

    @FXML
    private TextField tf_filter;
    @FXML
    private Label label_id;
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
    @FXML
    private RadioButton button_radio_platit;
    @FXML
    private TableView<Session> table_materie;
    @FXML
    private TableColumn<Session, Integer> column_id;
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
    private TableColumn<Session, Integer> column_platit;

    ObservableList<Session> listS;
    ObservableList<Session> dataList;
    int index = -1;
    public void getSelected () {

        index = table_materie.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }
        label_id.setText(column_id.getCellData(index).toString());
        tf_materie.setText(column_materie.getCellData(index).toString());
        combobox_student.setValue(column_student.getCellData(index));
        tf_pret_per_ora.setText(column_pret_per_ora.getCellData(index).toString());
        combobox_durata.setValue(column_durata.getCellData(index));
        datepicker_calendar.setValue(column_data.getCellData(index).toLocalDate());
    }


    public void UpdateTableMeditatii(){
        column_id.setCellValueFactory(new PropertyValueFactory<Session, Integer>("ID"));
        column_materie.setCellValueFactory(new PropertyValueFactory<Session, String>("materie"));
        column_id_student.setCellValueFactory(new PropertyValueFactory<Session, String>("idStudent"));
        column_student.setCellValueFactory(new PropertyValueFactory<Session, String>("student_nume"));
        column_pret_per_ora.setCellValueFactory(new PropertyValueFactory<Session, Integer>("pret_per_ora"));
        column_durata.setCellValueFactory(new PropertyValueFactory<Session, Integer>("durata"));
        column_data.setCellValueFactory(new PropertyValueFactory<Session, Date>("date"));
        column_pret_total.setCellValueFactory(new PropertyValueFactory<Session, Double>("pret_total"));
        column_platit.setCellValueFactory(new PropertyValueFactory<Session, Integer>("platit"));

        listS = Utils.getAllSessions();
        table_materie.setItems(listS);
    }

    @FXML
    void search_session(){
        column_id.setCellValueFactory(new PropertyValueFactory<Session, Integer>("ID"));
        column_materie.setCellValueFactory(new PropertyValueFactory<Session, String>("materie"));
        column_id_student.setCellValueFactory(new PropertyValueFactory<Session, String>("idStudent"));
        column_student.setCellValueFactory(new PropertyValueFactory<Session, String>("student_nume"));
        column_pret_per_ora.setCellValueFactory(new PropertyValueFactory<Session, Integer>("pret_per_ora"));
        column_durata.setCellValueFactory(new PropertyValueFactory<Session, Integer>("durata"));
        column_data.setCellValueFactory(new PropertyValueFactory<Session, Date>("date"));
        column_pret_total.setCellValueFactory(new PropertyValueFactory<Session, Double>("pret_total"));


        dataList = Utils.getAllSessions();
        table_materie.setItems(dataList);
        FilteredList<Session> filteredData = new FilteredList<>(dataList, b -> true);
        tf_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(session -> {
                if(newValue ==null || newValue.isEmpty()){
                    return true;
                }
                System.out.println(session.getMaterie());
                String lowerCaseFilter = newValue.toLowerCase();
                System.out.println(lowerCaseFilter);
                if(session.getStudent_nume().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (String.valueOf(session.getIdStudent()).contains(lowerCaseFilter)){
                    return true;

                }else if(session.getMaterie().toLowerCase().contains(lowerCaseFilter)) {
                    System.out.println("Am intrebat");
                    return true;
                }
                else if(String.valueOf(session.getDate()).contains(lowerCaseFilter))
                    return true;
                else
                    return false; // nu gasim nimic

            });
        });
        SortedList<Session> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_materie.comparatorProperty());
        table_materie.setItems(sortedData);
    }
//-------------------------------------------------------------------\\
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        combobox_durata.getItems().add(60);
        combobox_durata.getItems().add(90);
        combobox_durata.getItems().add(120);

        UpdateTableMeditatii();

        System.out.println("-------");
        table_materie.setItems(Utils.getAllSessions());
        Utils.getAllSessions().forEach(s->{
            System.out.println(s);
        });




        ObservableList<UseriCati> students = Utils.getDatausers();
        for (UseriCati student:students) {
            combobox_student.getItems().add(student.getId()+" "+student.getNume()+" "+student.getPrenume());
        }

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println(tf_materie.getText());
//                System.out.println(tf_pret_per_ora.getText());
//                System.out.println(combobox_student.getValue().substring(0,combobox_student.getValue().indexOf(" ")));
//                System.out.println(combobox_durata.getValue());
//                System.out.println(datepicker_calendar.getValue());
                int platit = button_radio_platit.isSelected()?1:0;
                Session s = new Session(tf_materie.getText(),
                        CurrentUser.id,
                        Integer.parseInt(combobox_student.getValue().substring(0,combobox_student.getValue().indexOf(" "))),
                        Integer.parseInt(tf_pret_per_ora.getText()),
                        combobox_durata.getValue(),
                        Date.valueOf(datepicker_calendar.getValue()),
                        platit
                        );

                Utils.AddSession(s);
                UpdateTableMeditatii();
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

        UpdateTableMeditatii();
        search_session();


    }
}
