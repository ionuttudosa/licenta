package com.example.licentamanagementmeditatii;

public class UseriCati {
    int id;
    String nume, prenume, email, telefon;

    public UseriCati(int id, String nume, String prenume, String email, String telefon) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
    }

    public UseriCati() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}