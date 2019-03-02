package com.example.worldskills.Model;

public class Rate {
    private double USD, RUB; //RUB == EUR in RUB; Add other currencies if necessary

    public double getCurrency(String code) {
        try {
            switch (code) {
                case "USD":
                    return toRub(USD);

                case "RUB":
                    return toRub(RUB);

                case "EUR":
                    return RUB;

                default:
                    return 0;
            }
        }
        catch (NullPointerException ex) {
            return(0);
        }
    }

    private double toRub(double currency) { //base currency is always EUR -> need to convert
        try {
        return RUB / currency;
        } catch (java.lang.ArithmeticException divideByZeroException) {
            return 0;
        }
    }
}
