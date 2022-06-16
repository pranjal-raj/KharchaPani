package com.example.kharchapani;

import java.util.ArrayList;

public class RecordsView {


    String datetitle;
     ArrayList<Record> ChildItemRecords;

    public RecordsView() {
    }

    public RecordsView(String datetitle, ArrayList<Record> childItemRecords) {
        this.datetitle = datetitle;
        ChildItemRecords = childItemRecords;
    }

    public String getDatetitle() {
        return datetitle;
    }

    public void setDatetitle(String datetitle) {
        this.datetitle = datetitle;
    }

    public ArrayList<Record> getChildItemRecords() {
        return ChildItemRecords;
    }

    public void setChildItemRecords(ArrayList<Record> childItemRecords) {
        ChildItemRecords = childItemRecords;
    }
}
