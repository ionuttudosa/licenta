package com.example.licentamanagementmeditatii;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class Utils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;
        System.out.println(username);
        System.out.println(fxmlFile);
        if(username!=null){

            try {
                FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlFile));
                root = loader.load();
                DashboardController dashboardController = loader.getController();
          //      LandingController.setUserInformation(username);
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(Utils.class.getResource(fxmlFile));
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)  ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 900,600));
        stage.show();


    }

    public static void signUpUser(ActionEvent event, String username, String password, String nume, String prenume, String email, String telefon){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        //connectiune cu baza de date locale mySQL si algoritmul de creeare user nou in SQL cu metoda de detectare a datelor necorespunzatoare(fara spatii in username si parola)
        //si a cautare daca userul nou nu exista deja inregistrat
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/licenta_manag_meditatii", "root", "Abc123ABC?!!");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM meditatori WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setContentText("Nu puteti folosi acest username");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO meditatori(username, password, nume, prenume, email, telefon) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,nume);
                psInsert.setString(4,prenume);
                psInsert.setString(5,email);
                psInsert.setString(6,telefon);
                psInsert.executeUpdate();
                changeScene(event, "Dashboard.fxml", "Welcome "+username, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {

                try {psCheckUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    //connexiune cu baza de date locale mySQL si algoritmul de logare user existent in SQL si detectare de data invalide / user / parola. folosit mult try catch
    public  static  void  logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/licenta_manag_meditatii", "root", "Abc123ABC?!!");
            preparedStatement = connection.prepareStatement("SELECT ID,password FROM meditatori WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("Utilizatorul nu a fost gasit");
                Alert alert  = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("verificati integritatea datelor");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        int id = resultSet.getInt("ID");
                        System.out.println(id);
                        CurrentUser.id = id;
                        changeScene(event,"Dashboard.fxml", "Bine ati venit "+ username+"!",  username);
                    } else {
                        System.out.println("parola incorecta");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("verificati integritatea datelor");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // connexiun directa la baza de data apelata dupa logara pentru administrare useri din aplicatie direct
    public static Connection ConnectDb(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/licenta_manag_meditatii", "root", "Abc123ABC?!!");
//            JOptionPane.showMessageDialog(null, "Legatura reusita");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }

    }

    public static ObservableList<Session> getPendingSessions(){

        Connection conn = ConnectDb();
        ObservableList<Session> list = FXCollections.observableArrayList();
        try{
            System.out.println(CurrentUser.current_date==null);
            System.out.println("linia care vine");
            if(CurrentUser.current_date == null){
                CurrentUser.setCurrent_date();
                System.out.println("blablabla");
            }
            System.out.println("superceva");
            PreparedStatement ps = conn.prepareStatement("select m.platit, m.ID, m.materie, m.ID_student, m.pret_per_ora, m.durata, m.data, s.nume AS student_ln, s.prenume AS student_fn from meditatii AS m \n" +
                    "INNER JOIN student AS s ON m.ID_student = s.ID\n" +
                    "where ID_meditator = ? AND m.platit = 0 AND m.data <= ?");
            ps.setInt(1,CurrentUser.id);
            ps.setDate(2, Date.valueOf(LocalDate.now()));

            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            while (rs.next()){
                System.out.println("bbbbbbbb");
                Session session = new Session(
                        rs.getInt("ID"),
                        rs.getString("materie"),
                        CurrentUser.id,
                        rs.getInt("ID_student"),
                        rs.getString("student_ln") + " " + rs.getString("student_fn"),

                        rs.getInt("pret_per_ora"),
                        rs.getInt("durata"),
                        rs.getDate("data"),
                        rs.getInt("platit")

                );
                session.setPret_total(session.getPret_per_ora() * ((double)session.getDurata()/60));
                list.add(session);
            }
        }catch (Exception e){
        }
        return list;
    }

    public static ObservableList<Session> getAllSessions(){

        Connection conn = ConnectDb();
        ObservableList<Session> list = FXCollections.observableArrayList();
        try{
            System.out.println(CurrentUser.current_date==null);
            System.out.println("linia care vine");
            if(CurrentUser.current_date == null){
                CurrentUser.setCurrent_date();
                System.out.println("blablabla");
            }
            System.out.println("superceva");
            PreparedStatement ps = conn.prepareStatement("select m.platit, m.ID, m.materie, m.ID_student, m.pret_per_ora, m.durata, m.data, s.nume AS student_ln, s.prenume AS student_fn from meditatii AS m \n" +
                    "INNER JOIN student AS s ON m.ID_student = s.ID\n" +
                    "where ID_meditator = ? AND m.data >= ? AND m.data <= ?");
            ps.setInt(1,CurrentUser.id);
            ps.setDate(2, CurrentUser.current_date);
            ps.setDate(3,CurrentUser.endMonth);
            System.out.println(CurrentUser.endMonth);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("NAE");
                Session session = new Session(
                        rs.getInt("ID"),
                        rs.getString("materie"),
                        CurrentUser.id,
                        rs.getInt("ID_student"),
                        rs.getString("student_ln") + " " + rs.getString("student_fn"),

                        rs.getInt("pret_per_ora"),
                        rs.getInt("durata"),
                        rs.getDate("data"),
                        rs.getInt("platit")

                );
                System.out.println(session.getStudent_nume());
                session.setPret_total(session.getPret_per_ora() * ((double)session.getDurata()/60));
                list.add(session);
            }
        }catch (Exception e){
        }
        return list;
    }


    //lista folosita pentru a extrage datele din baza de date
    public static ObservableList<UseriCati> getDatausers(){

        Connection conn = ConnectDb();
        ObservableList<UseriCati> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from student where idMeditator = ?");
            ps.setInt(1,CurrentUser.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new UseriCati(
                        Integer.parseInt(rs.getString("ID")),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getString("telefon")));
            }
        }catch (Exception e){
        }
        return list;
    }

    public static void AddSession(Session session){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/licenta_manag_meditatii", "root", "Abc123ABC?!!");
            PreparedStatement ps = connection.prepareStatement("insert into meditatii (materie, ID_meditator, ID_student, pret_per_ora, durata, data, platit) values (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, session.getMaterie());
            ps.setInt(2,session.getIdMeditator());
            ps.setInt(3,session.getIdStudent());
            ps.setInt(4, session.getPret_per_ora());
            ps.setInt(5,session.getDurata());
            ps.setDate(6,session.getDate());
            ps.setInt(7, session.getPlatit());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    //tranformam pe asta in defapt management studenti
    public static void CheckAddUser(ActionEvent event, String nume, String prenume, String email, String telefon){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/licenta_manag_meditatii", "root", "Abc123ABC?!!");

            psCheckUserExists = connection.prepareStatement("SELECT * FROM student WHERE nume = ?");
            psCheckUserExists.setString(1, nume);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst() && nume!=null) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                int idMeditator = CurrentUser.id;
                psInsert = connection.prepareStatement("INSERT INTO student (nume, prenume, email, telefon, idMeditator) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1, nume);
                psInsert.setString(2, prenume);
                psInsert.setString(3, email);
                psInsert.setString(4, telefon);
                psInsert.setInt(5, idMeditator);
                System.out.println(psInsert);
                psInsert.execute();
                JOptionPane.showMessageDialog(null, "Studentul a fost adaugat");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {

                try {psCheckUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }


}
