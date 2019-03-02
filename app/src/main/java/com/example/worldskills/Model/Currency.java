package com.example.worldskills.Model;

import com.example.worldskills.Listener.CurrencyValueDataListener;
import com.example.worldskills.Listener.Listener;
import com.example.worldskills.Repository.RepositoryCurrency;

public class Currency {
    private String  code, decoded;
    private double course, courseChange;
    private int flagId;
    private Listener listener;

    public Currency(String code, String decoded, int flagId, Listener listener) {
        this.code = code.toUpperCase();
        this.decoded = decoded;
        this.flagId = flagId;
        this.listener = listener;

        updateCourse();
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public String getCode() { return code; }

    public String getDecoded() { return decoded; }

    public double getCourseChange() { return courseChange; }

    public int getFlagId() { return flagId; }

    public double getCourse() { return course; }

    public void setCourse(double course) { this.course = course; }

    public void setCourseChange(double courseChange) { this.courseChange = courseChange; }

    public void updateCourse() { //Add historical update too
        RepositoryCurrency.getCurrencyValue(new CurrencyValueDataListener() {
            @Override
            public void onGetValueData(Rate rate) {
                Currency.this.setCourse(rate.getCurrency(code));
                listener.onCompletion();
            }
        });
    }
}
