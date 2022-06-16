package com.example.kharchapani;

public class DateFormatter {
    String date;

    public DateFormatter() {

    }

    public String dateformat(String datetext)
    {
        String dateString = datetext.substring(0,2);
        String yearString = datetext.substring(4,8);
        String month = datetext.substring(2,4);
        String monthString="";
        switch(month)
        {
            case "01":
                monthString= "Jan";
                break;
            case "02":
                monthString= "Feb";
                break;
            case "03":
                monthString= "Mar";
                break;
            case "04":
                monthString= "Apr";
                break;
            case "05":
                monthString= "May";
                break;
            case "06":
                monthString= "Jun";
                break;
            case "07":
                monthString= "Jul";
                break;
            case "08":
                monthString= "Aug";
                break;
            case "09":
                monthString= "Sept";
                break;
            case "10":
                monthString= "Oct";
                break;
            case "11":
                monthString= "Nov";
                break;
            case "12":
                monthString= "Dec";
                break;
        }

        date = monthString + " " + dateString+ ", " + yearString;

        return date;
    }
}
