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
import android.widget.Toast;

public class ChangePasswordDialogFragment extends DialogFragment {
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_change_password, null))
                .setMessage(R.string.change_password)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!getPassword().isEmpty())
                            RepositoryProfile.changePassword(getPassword(), new DataListener() {
                                @Override
                                public void onGetData(boolean isValid, String info) {
                                    //should stay empty
                                }
                            });
                        else Toast.makeText(context,
                                context.getString(R.string.message_enter_password),
                                Toast.LENGTH_LONG)
                                .show();
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
                if (!getPassword().isEmpty()) {
                    RepositoryProfile.changePassword(getPassword(), new DataListener() {
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
                            App.getContext().getString(R.string.message_enter_password));
                    successDialogFragment.setArguments(successBundle);
                    successDialogFragment.show(manager, "infoDialog");
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00A0FF"));
    }

    @NonNull
    private String getPassword() {
        EditText passwordView = getDialog().findViewById(R.id.change_password_edit);
        return passwordView.getText().toString();
    }
}
