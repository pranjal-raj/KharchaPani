package com.example.kharchapani;

import android.icu.util.Calendar;

public class Record {
    String date;
    String account;
    int ammount;
    String category;
    int openingbal, closingbal;
    String id;
    String type;
    public Record() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Record(String date, String account, int ammount, int openingbal, int closingbal, String category, String id, String type) {
        this.date = date;
        this.ammount = ammount;
        this.category = category;
        this.id = id;
        this.account = account;
        this.openingbal = openingbal;
        this.closingbal = closingbal;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public int getOpeningbal() {
        return openingbal;
    }

    public void setOpeningbal(int openingbal) {
        this.openingbal = openingbal;
    }

    public int getClosingbal() {
        return closingbal;
    }

    public void setClosingbal(int closingbal) {
        this.closingbal = closingbal;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
