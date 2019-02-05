package com.example.worldskills;

public class Credit {

    private String type, paymentDate; //Date format DD.MM.YYYY
    private double money;

    public Credit(String type, String paymentDate, double money) {
        this.type = type;
        this.paymentDate = paymentDate;
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getMoney() {
        return money;
    }
}
