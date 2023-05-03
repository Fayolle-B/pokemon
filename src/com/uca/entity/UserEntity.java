package com.uca.entity;

import java.time.LocalDate;
import java.util.Date;

public class UserEntity {
    private String firstName;
    private String lastName;
    private int id;
    private String login;
    private String pwd;
    private String Email;
    private int points;
    private LocalDate dateConnexion;


    public UserEntity() {
        //Ignored !
    }

    public int getId() {
        return this.id;
    }

    public int getPoints() {
        return this.points;
    }

    public String getLogin() {
        return this.login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return this.Email;
    }

    public LocalDate getDateConnexion() {
        System.out.println("This.date est une "+ this.dateConnexion.getClass());
        return this.dateConnexion;
    }

    public String getPwd() {
        return this.pwd;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setEmail(String E) {
        this.Email = E;
    }

    public void setDateConnexion(LocalDate date) {
        // voir plus tard comment creer l'objet date
        this.dateConnexion = date;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPoints(int p) {
        this.points = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;

        if (!getPwd().equals(that.getPwd())) return false;
        return getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        int result = getPwd().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }
}
