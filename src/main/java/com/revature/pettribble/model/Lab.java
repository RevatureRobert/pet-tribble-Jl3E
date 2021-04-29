package com.revature.pettribble.model;

import javax.persistence.*;

@Table(name = "lab")
public class Lab {

    @Id
    @Column(name = "lab_id")
    private int lab_id;

    @Column(name = "lab_name")
    private String lab_name;

    @Column(name = "tribble_id")
    private int tribble_id;

    public Lab(int lab_id, String lab_name, int tribble_id) {
        this.lab_id = lab_id;
        this.lab_name = lab_name;
        this.tribble_id = tribble_id;
    }

    public int getLab_id() {
        return lab_id;
    }

    public void setLab_id(int lab_id) {
        this.lab_id = lab_id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public int getTribble_id() {
        return tribble_id;
    }

    public void setTribble_id(int tribble_id) {
        this.tribble_id = tribble_id;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "lab_id=" + lab_id +
                ", lab_name='" + lab_name + '\'' +
                ", tribble_id=" + tribble_id +
                '}';
    }
}
