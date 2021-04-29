package com.revature.pettribble.model;

import javax.persistence.*;

@Table(name = "lab")
public class Lab {

    @Id
    @Column(name = "lab_id")
    private int labId;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "tribble_id")
    private int tribbleId;

    public Lab(int labId, String labName, int tribbleId) {
        this.labId = labId;
        this.labName = labName;
        this.tribbleId = tribbleId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public int getTribbleId() {
        return tribbleId;
    }

    public void setTribbleId(int tribbleId) {
        this.tribbleId = tribbleId;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "labId=" + labId +
                ", labName='" + labName + '\'' +
                ", tribbleId=" + tribbleId +
                '}';
    }
}
