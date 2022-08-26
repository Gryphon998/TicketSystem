package org.lalala.ticketsystem.bean;

import java.util.Map;

public class Customer extends User{
    private char gender;
    private Map<Movie, Boolean> purchasedMovie;

    public Customer() {
    }

    public Customer(String userAcctNum, String userName, String passWord, String phone,
                    double acctBalance, AcctType acctType, char gender, Map<Movie, Boolean> purchasedMovie) {
        super(userAcctNum, userName, passWord, phone, acctBalance, acctType);
        this.gender = gender;
        this.purchasedMovie = purchasedMovie;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Map<Movie, Boolean> getPurchasedMovie() {
        return purchasedMovie;
    }

    public void setPurchasedMovie(Map<Movie, Boolean> purchasedMovie) {
        this.purchasedMovie = purchasedMovie;
    }
}
