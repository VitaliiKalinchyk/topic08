package com.epam.rd.java.basic.topic08.container;

public class Currency {

    private int id;

    private String name;

    private double rate;

    private String abr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }
}