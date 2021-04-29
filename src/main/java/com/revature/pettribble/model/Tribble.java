package com.revature.pettribble.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tribble")
public class Tribble {

    @Id
    @Column(name = "tribble_id")
    private int tribble_id;

    @Column(name = "tribble_name")
    private String tribble_name;

    @Column(name = "tribble_weight")
    private int tribble_weight;

    @Column(name = "tribble_height")
    private int tribble_height;

    @Column(name = "tribble_color")
    private String tribble_color;

    public Tribble(int tribble_id, String tribble_name, int tribble_weight, int tribble_height, String tribble_color) {
        this.tribble_id = tribble_id;
        this.tribble_name = tribble_name;
        this.tribble_weight = tribble_weight;
        this.tribble_height = tribble_height;
        this.tribble_color = tribble_color;
    }

    public int getTribble_id() {
        return tribble_id;
    }

    public void setTribble_id(int tribble_id) {
        this.tribble_id = tribble_id;
    }

    public String getTribble_name() {
        return tribble_name;
    }

    public void setTribble_name(String tribble_name) {
        this.tribble_name = tribble_name;
    }

    public int getTribble_weight() {
        return tribble_weight;
    }

    public void setTribble_weight(int tribble_weight) {
        this.tribble_weight = tribble_weight;
    }

    public int getTribble_height() {
        return tribble_height;
    }

    public void setTribble_height(int tribble_height) {
        this.tribble_height = tribble_height;
    }

    public String getTribble_color() {
        return tribble_color;
    }

    public void setTribble_color(String tribble_color) {
        this.tribble_color = tribble_color;
    }

    @Override
    public String toString() {
        return "Tribble{" +
                "tribble_id=" + tribble_id +
                ", tribble_name='" + tribble_name + '\'' +
                ", tribble_weight=" + tribble_weight +
                ", tribble_height=" + tribble_height +
                ", tribble_color='" + tribble_color + '\'' +
                '}';
    }
}
