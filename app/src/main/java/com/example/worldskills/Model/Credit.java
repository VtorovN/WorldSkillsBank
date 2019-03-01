package com.example.worldskills.Model;

public class Credit {
    private String creditName, paymentDate;
    private long sum;

    public Credit(String creditName, String paymentDate, long sum) {
        this.creditName = creditName;
        this.paymentDate = paymentDate;
        this.sum = sum;
    }

    public String getCreditName() {
        return creditName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public long getSum() {
        return sum;
    }
}
