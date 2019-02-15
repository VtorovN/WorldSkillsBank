package com.example.worldskills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginDialogFragment extends DialogFragment {

    private Context context;
    private Listener listener;

    public void setListener(Listener listener) { this.listener = listener; }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_login_popup, null))
                .setMessage(R.string.login_title)
                .setNegativeButton(R.string.login_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.login_enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getLogin() != "" && getPassword() != "") {
                            RepositoryProfile.getUserToken(
                                    getLogin(),
                                    getPassword(),
                                    new LoginDataListener() {
                                        @Override
                                        public void onGetToken(boolean isValid, String info) {
                                            if (isValid) {
                                                listener.onGetData();
                                                //startActivity(new Intent(context, UserProfileActivity.class));
                                            } else {
                                                Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
