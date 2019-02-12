package com.example.worldskills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

public class LoginDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_login_popup, null))
                .setMessage(R.string.login_title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), UserProfileActivity.class));
                        /*
                        if (getLogin() != "" && getPassword() != "") {
                            RepositoryProfile.getUserToken(
                                    getLogin(),
                                    getPassword(),
                                    new LoginDataListener() {
                                        @Override
                                        public void onGetToken(String token) {
                                            if (token != null) {
                                                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                                                startActivity(intent);
                                            } else {

                                            }
                                        }
                                    });
                        }
                        */
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00A0FF"));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#00A0FF"));
    }

    @NonNull
    private String getPassword() {
        EditText passwordView = getDialog().findViewById(R.id.login_editText_password);
        return passwordView.getText().toString();
    }

    @NonNull
    private String getLogin() {
        EditText loginView = getDialog().findViewById(R.id.login_editText_login);
        return loginView.getText().toString();
    }
}
