package com.example.licentamanagementmeditatii;

public class UseriCati {
    int id;
    String email , telefon, nume, prenume;


    public UseriCati(int id, String email, String telefon, String nume, String prenume) {
        this.id = id;
        this.email = email;
        this.telefon = telefon;
        this.nume = nume;
        this.prenume = prenume;
    }
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
}
