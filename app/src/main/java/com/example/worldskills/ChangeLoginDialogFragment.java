package com.example.worldskills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeLoginDialogFragment extends DialogFragment {
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_change_login, null))
                .setMessage(R.string.change_login)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //should stay empty
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() { //check LoginDialogFragment.java for code explanation
        super.onStart();
        final FragmentManager manager = getFragmentManager();
        AlertDialog dialog = (AlertDialog)getDialog();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#00A0FF"));
        final SuccessInfoDialogFragment successDialogFragment = new SuccessInfoDialogFragment();
        final Bundle successBundle = new Bundle();
        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        final Listener successListener = new Listener() {
            @Override
            public void onCompletion() {
                dismiss();
            }
        };
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogFragment.show(getFragmentManager(), "progressDialog");
                if (!getLogin().isEmpty()) {
                    RepositoryProfile.changeLogin(getLogin(),
                            new DataListener() {
                                @Override
                                public void onGetData(boolean isValid, String info) {
                                    if (isValid) {
                                        progressDialogFragment.dismiss();

                                        successBundle.putBoolean("success", true);
                                        successBundle.putString("info", info);
                                        successDialogFragment.setArguments(successBundle);
                                        successDialogFragment.show(getFragmentManager(),
                                                "infoDialog");

                                        successListener.onCompletion();
                                    } else {
                                        progressDialogFragment.dismiss();

                                        successBundle.putBoolean("success", false);
                                        successBundle.putString("info", info);
                                        successDialogFragment.setArguments(successBundle);
                                        successDialogFragment.show(getFragmentManager(),
                                                "infoDialog");
                                    }
                                }
                            });
                } else {
                    progressDialogFragment.dismiss();

                    successBundle.putBoolean("success", false);
                    successBundle.putString("info",
                            App.getContext().getString(R.string.message_enter_login));
                    successDialogFragment.setArguments(successBundle);
                    successDialogFragment.show(manager, "infoDialog");
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00A0FF"));
    }

    @NonNull
    private String getLogin() {
        EditText loginView = getDialog().findViewById(R.id.change_login_edit);
        return loginView.getText().toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
