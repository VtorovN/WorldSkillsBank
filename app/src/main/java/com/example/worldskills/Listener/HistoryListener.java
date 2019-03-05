package com.example.worldskills.Listener;

import com.example.worldskills.Model.Operation;

public interface HistoryListener {
    public void onGetHistory(Operation[] operations, boolean isValid, String info);
}
