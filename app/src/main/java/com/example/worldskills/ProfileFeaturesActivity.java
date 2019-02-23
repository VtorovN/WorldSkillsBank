package com.example.worldskills;

import android.content.Intent;
import android.provider.ContactsContract;
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
        final ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.show(getSupportFragmentManager(), "progressDialog");
        RepositoryProfile.logout(new DataListener() {
            @Override
            public void onGetData(boolean isValid, String info) {
                fragment.dismiss();
                if (isValid) {
                    Intent intent = new Intent(ProfileFeaturesActivity.this,
                            MainActivity.class);
                    //clear previous activities to unable BackButton to return
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    SuccessInfoDialogFragment dialogFragment = new SuccessInfoDialogFragment();
                    dialogFragment.setArguments(SuccessBundle.assemble(false, info));
                    dialogFragment.show(getSupportFragmentManager(), "successDialog");
                }
            }
        });
    }
}
