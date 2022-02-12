package com.example.licentamanagementmeditatii;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

public class Session {
    private int ID;
    private String materie;
    private int idMeditator;
    private int idStudent;
    private int pret_per_ora;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private int durata;
    private Date date;

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

    public Session(int ID, String materie, int idMeditator, int idStudent, int pret_per_ora, int durata, Date date) {
        this.ID = ID;
        this.materie = materie;
        this.idMeditator = idMeditator;
        this.idStudent = idStudent;
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


}
