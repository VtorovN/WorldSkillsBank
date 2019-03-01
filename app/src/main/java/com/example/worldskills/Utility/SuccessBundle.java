package com.example.worldskills.Utility;

import android.os.Bundle;

public class SuccessBundle {

    public static Bundle assemble(boolean success, String info) {
        Bundle bundle = new Bundle();
        bundle.putString("info", info);
        bundle.putBoolean("success", success);
        return bundle;
    }
}
