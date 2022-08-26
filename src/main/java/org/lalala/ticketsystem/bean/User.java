package org.lalala.ticketsystem.bean;

/* This is a father class for Merchant and Customer */
public class User {
    public enum AcctType {
        business,  individual;
    }
    private String userAcctNum;
    private String userName;
    private String passWord;
    private String phone;
    private double acctBalance;
    private AcctType acctType;

    public User() {
    }

    public User(String userAcctNum, String userName, String passWord, String phone,
                double acctBalance, AcctType acctType) {
        this.userAcctNum = userAcctNum;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.acctBalance = acctBalance;
        this.acctType = acctType;
    }

    public AcctType getAcctType() {
        return acctType;
    }

    public void setAcctType(AcctType acctType) {
        this.acctType = acctType;
    }

    public String getUserAcctNum() {
        return userAcctNum;
    }

    public void setUserAcctNum(String userAcctNum) {
        this.userAcctNum = userAcctNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(double acctBalance) {
        this.acctBalance = acctBalance;
    }
}
