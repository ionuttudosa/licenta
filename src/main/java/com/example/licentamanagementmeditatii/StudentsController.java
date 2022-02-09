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
import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    @FXML
    private  TextField tf_filter;
    @FXML
    private Button button_logout;
    @FXML
    private  Button button_back;
    @FXML
    private  Button button_add;
    @FXML
    private  Button button_update;
    @FXML
    private  Button button_delete;
    @FXML
    private Label label_id;
    @FXML
    private TextField tf_nume;
    @FXML
    private TextField tf_prenume;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_telefon;

    @FXML
    private TableView<UseriCati> table_users;
    @FXML
    private TableColumn<UseriCati, Integer> col_db_user_id;
    @FXML
    private TableColumn<UseriCati,String> col_db_nume ;
    @FXML
    private TableColumn<UseriCati,String> col_db_prenume ;
    @FXML
    private TableColumn<UseriCati,String> col_db_email ;
    @FXML
    private TableColumn<UseriCati,String> col_db_telefon ;

    //liste folosite pentru populare si cautare
    ObservableList<UseriCati> dataList;
    ObservableList<UseriCati> listM;

    int index = -1;
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/licenta_manag_meditatii", "root", "Abc123ABC?!!");

    ResultSet rs = null;
    PreparedStatement pst = null;

    public StudentsController() throws SQLException {

    }
    //metoda pentru selectare user din tabel si populare campuri cu valorile din celule
    public void getSelected () {

        index = table_users.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }
        label_id.setText(col_db_user_id.getCellData(index).toString());
        tf_nume.setText(col_db_nume.getCellData(index).toString());
        tf_prenume.setText(col_db_prenume.getCellData(index).toString());
        tf_email.setText(col_db_email.getCellData(index).toString());
        tf_telefon.setText(col_db_telefon.getCellData(index).toString());
    }
    //metoda pentru reimprospatarea tabelului dupa ce a fost modificat
    public void UpdateTable(){
        col_db_user_id.setCellValueFactory(new PropertyValueFactory<UseriCati, Integer>("id"));
        col_db_nume.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("nume"));
        col_db_prenume.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("prenume"));
        col_db_email.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("email"));
        col_db_telefon.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("telefon"));

        listM = Utils.getDatausers();
        table_users.setItems(listM);
    }
    //metoda si algoritm pentru cautare/filtrare unei bucat de date din campurile tabelei
    @FXML
    void search_user(){
        col_db_user_id.setCellValueFactory(new PropertyValueFactory<UseriCati, Integer>("id"));
        col_db_nume.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("nume"));
        col_db_prenume.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("prenume"));
        col_db_email.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("email"));
        col_db_telefon.setCellValueFactory(new PropertyValueFactory<UseriCati, String>("telefon"));

        dataList = Utils.getDatausers();
        table_users.setItems(dataList);
        FilteredList<UseriCati> filteredData = new FilteredList<>(dataList, b -> true);
        tf_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if(newValue ==null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(person.getNume().indexOf(lowerCaseFilter) != -1){
                    return true; // Filtru corespunde username
                }else if (String.valueOf(person.getPrenume()).indexOf(lowerCaseFilter) != -1){
                    return true; // filtru corespdune parola
                }else if(String.valueOf(person.getEmail()).indexOf(lowerCaseFilter) != -1){
                    return true; // filtru corespdune nume
                }
                else if(String.valueOf(person.getTelefon()).indexOf(lowerCaseFilter) != -1)
                    return true; // filtru corespdune PREnume
                else
                    return false; // nu gasim nimic

            });
        });
        SortedList<UseriCati> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    @Override
    public void  initialize(URL url, ResourceBundle resourceBundle) {
//buton de delogare
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search_user();
                UpdateTable();
                Utils.changeScene(event, "Landing.fxml", "Welcome!", null);
            }
        });
////but de a merge inapoi la pagina cu aplicatii.

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search_user();
                UpdateTable();
                Utils.changeScene(event, "Dashboard.fxml", "Welcome!", null);
            }

        });
////buton si metoda pentru a sterge un utilizator
        button_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                conn = Utils.ConnectDb();
                String sql = "DELETE from student where ID = ?";
                try{
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, label_id.getText());
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Utilizator sters");
                    search_user();
                    UpdateTable();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });
////buton si metoda pentru a actualiza un utilizator si datele sale.
        button_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    conn = Utils.ConnectDb();
                    String value1 = label_id.getText();
                    String value2 = tf_email.getText();
                    String value3 = tf_telefon.getText();
                    String value4 = tf_nume.getText();
                    String value5 = tf_prenume.getText();
                    if(!(value2==null) && !(value2.contains(" ")) && !(value3==null )&& !(value3.contains(" "))){
                        String sql = "UPDATE users set  nume='"+value2+"',prenume='"+value3+"',email='"+value4+"',telefon='"+value5+"' where user_id= '"+value1+"' ";
                        pst = conn.prepareStatement(sql);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "User actualizat");
                        search_user();
                        UpdateTable();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Verificati integritate datelor introduse");
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
////buton si metoda pentru a adauga un utilizator manual, se apeleaza metode din DBUTILS + conditie de validare a datelor introduse
        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(tf_nume.getText() ==null) && !(tf_nume.getText().contains(" ")) && !(tf_prenume==null)&& !(tf_prenume.getText().contains(" "))){
                    Utils.CheckAddUser(event, tf_nume.getText(), tf_prenume.getText(), tf_email.getText(), tf_telefon.getText());
                    search_user();
                    UpdateTable();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Verificati integritate datelor introduse");
                    alert.show();
                }
            }
        });
//
        UpdateTable();
        search_user();
    }

}