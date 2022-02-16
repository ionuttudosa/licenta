package com.example.licentamanagementmeditatii;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

public class Session {
    private int ID;
    private String materie;
    private int idMeditator;
    private int idStudent;
    private String student_nume;
    private int pret_per_ora;
    private int durata;
    private Date date;
    private double pret_total;

    @Override
    public String toString() {
        return "Session{" +
                "ID=" + ID +
                ", materie='" + materie + '\'' +
                ", idMeditator=" + idMeditator +
                ", idStudent=" + idStudent +
                ", student_nume='" + student_nume + '\'' +
                ", pret_per_ora=" + pret_per_ora +
                ", durata=" + durata +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    public Session() {
    }

    public Session(String materie, int idMeditator, int idStudent, int pret_per_ora, int durata, Date date) {
        this.materie = materie;
        this.idMeditator = idMeditator;
        this.idStudent = idStudent;
        this.pret_per_ora = pret_per_ora;
        this.durata = durata;
        this.date = date;
    }

    public Session(int ID, String materie, int idMeditator, int idStudent, String student_nume, int pret_per_ora, int durata, Date date) {
        this.ID = ID;
        this.materie = materie;
        this.idMeditator = idMeditator;
        this.idStudent = idStudent;
        this.student_nume = student_nume;
        this.pret_per_ora = pret_per_ora;
        this.durata = durata;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public int getIdMeditator() {
        return idMeditator;
    }

    public void setIdMeditator(int idMeditator) {
        this.idMeditator = idMeditator;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getPret_per_ora() {
        return pret_per_ora;
    }

    public void setPret_per_ora(int pret_per_ora) {
        this.pret_per_ora = pret_per_ora;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setStudent_nume(String student_nume) {
        this.student_nume = student_nume;
    }

    public String getStudent_nume() {
        return student_nume;
    }

    public double getPret_total() {
        return pret_total;
    }

    public void setPret_total(double pret_total) {
        this.pret_total = pret_total;
    }
}
