package com.example.kharchapani;

public class UserDetails {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public  String name, email, pswd;
    static int cash_bal;
    static int bank_bal;

    public UserDetails() {
    }

    public UserDetails(String name, String email, String pswd) {
        this.name = name;
        this.email = email;
        this.pswd = pswd;
    }

    public static int getCash_bal() {
        return cash_bal;
    }

    public static void setCash_bal(int cash_bal) {
        UserDetails.cash_bal = cash_bal;
    }

    public static int getBank_bal() {
        return bank_bal;
    }

    public static void setBank_bal(int bank_bal) {
        UserDetails.bank_bal = bank_bal;
    }
}
