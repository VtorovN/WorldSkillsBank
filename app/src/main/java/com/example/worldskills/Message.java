package com.example.worldskills;

import java.util.List;

public class Message {
    private boolean success;
    private String base;
    private List<Rate> rates;

    public boolean getSuccess() { return success; }

    public String getBase() { return base; }

    public List<Rate> getRates() { return rates; }
}
