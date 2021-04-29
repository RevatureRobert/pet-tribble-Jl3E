package com.revature.pettribble.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tribble")
public class Tribble {

    @Id
    @Column(name = "tribble_id")
    private int id;

    @Column(name = "tribble_name")
    private String tribbleName;

    @Column(name = "tribble_weight")
    private int tribbleWeight;

    @Column(name = "tribble_height")
    private int tribbleHeight;

    @Column(name = "tribble_color")
    private String tribbleColor;

    public Tribble(int id, String tribbleName, int tribbleWeight, int tribbleHeight, String tribbleColor) {
        this.id = id;
        this.tribbleName = tribbleName;
        this.tribbleWeight = tribbleWeight;
        this.tribbleHeight = tribbleHeight;
        this.tribbleColor = tribbleColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTribbleName() {
        return tribbleName;
    }

    public void setTribbleName(String tribbleName) {
        this.tribbleName = tribbleName;
    }

    public int getTribbleWeight() {
        return tribbleWeight;
    }

    public void setTribbleWeight(int tribbleWeight) {
        this.tribbleWeight = tribbleWeight;
    }

    public int getTribbleHeight() {
        return tribbleHeight;
    }

    public void setTribbleHeight(int tribbleHeight) {
        this.tribbleHeight = tribbleHeight;
    }

    public String getTribbleColor() {
        return tribbleColor;
    }

    public void setTribbleColor(String tribbleColor) {
        this.tribbleColor = tribbleColor;
    }

    @Override
    public String toString() {
        return "Tribble{" +
                "id=" + id +
                ", tribbleName='" + tribbleName + '\'' +
                ", tribbleWeight=" + tribbleWeight +
                ", tribbleHeight=" + tribbleHeight +
                ", tribbleColor='" + tribbleColor + '\'' +
                '}';
    }
}
