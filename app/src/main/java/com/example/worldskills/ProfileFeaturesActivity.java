package com.example.worldskills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class ProfileFeaturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_features);
    }

    public void onPasswordChangeClick(View view) {
        ChangePasswordDialogFragment dialogFragment = new ChangePasswordDialogFragment();
        /*
        dialogFragment.setListener(new Listener() {
            @Override
            public void onCompletion() {

            }
        });
        */
        dialogFragment.show(getSupportFragmentManager(), "changePassword");
    }

    public void onLoginChangeClick(View view) {
        ChangeLoginDialogFragment dialogFragment = new ChangeLoginDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "changeLogin");
    }

    public void onVisitsHistoryClick(View view) {

    }

    public void onAppInfoClick(View view) {

    }

    public void onExitClick(View view) {
        //delete user token here and solve problem with back button returning to this activity
        startActivity(new Intent(this, MainActivity.class));
    }
}
