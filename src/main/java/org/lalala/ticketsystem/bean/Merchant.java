package org.lalala.ticketsystem.bean;

public class Merchant extends User{
    private String merchantName;
    private String address;

    public Merchant() {
    }

    public Merchant(String userAcctNum, String userName, String passWord, String phone,
                    double acctBalance, AcctType acctType, String merchantName, String address) {
        super(userAcctNum, userName, passWord, phone, acctBalance, acctType);
        this.merchantName = merchantName;
        this.address = address;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
