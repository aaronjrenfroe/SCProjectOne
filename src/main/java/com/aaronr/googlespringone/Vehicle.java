package com.aaronr.googlespringone;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

/**
 * Created by AaronR on 11/4/17.
 * for ?
 */
public class Vehicle implements Serializable{

    private String makeModel;
    private int year;
    private double msrp;
    private int id;

    public Vehicle(){

    }

    public Vehicle(int id, String makeModel, int year, double msrp){
        this.id = id;
        this.makeModel = makeModel;
        this.year = year;
        this.msrp = msrp;

    }

    public double getMsrp() {
        return msrp;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}