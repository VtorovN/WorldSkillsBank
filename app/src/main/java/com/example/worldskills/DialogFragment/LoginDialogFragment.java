package com.example.worldskills.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.worldskills.Utility.App;
import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.Listener.Listener;
import com.example.worldskills.R;
import com.example.worldskills.Repository.RepositoryProfile;
import com.example.worldskills.Utility.SuccessBundle;

public class LoginDialogFragment extends DialogFragment {

    private Listener listener;

    public void setListener(Listener listener) { this.listener = listener; }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_login, null))
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
                        //is overridden in onStart method
                        //should stay empty
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        //for further use:

        final FragmentManager manager = getFragmentManager();

        final SuccessInfoDialogFragment successDialogFragment = new SuccessInfoDialogFragment();

        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();

        final Listener successListener = new Listener() { //closes login dialog
            @Override
            public void onCompletion() {
                dismiss();
            }
        };

        AlertDialog dialog = (AlertDialog)getDialog();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#00A0FF"));
        positiveButton.setOnClickListener(new View.OnClickListener() { //this Listener doesn't dismiss
            @Override                                                  //dialog when PB's clicked
            public void onClick(View v) {
                progressDialogFragment.show(getFragmentManager(), "progressDialog");
                if (!getLogin().isEmpty() && !getPassword().isEmpty()) { //checks fields emptiness
                    RepositoryProfile.getUserToken(
                            getLogin(),
                            getPassword(),
                            new DataListener() {
                                @Override
                                public void onGetData(boolean isValid, String info) {
                                    progressDialogFragment.dismiss();
                                    if (isValid) { //if data is ok
                                        listener.onCompletion(); //goto UserProfileActivity

                                        //SuccessInfoDialog isn't necessary as
                                        //Intent starts in the next moment

                                        successListener.onCompletion(); //closes login dialog
                                    } else { //bad data, login failed, dialog isn't closed

                                        successDialogFragment.setArguments(SuccessBundle
                                                .assemble(false, info));
                                        successDialogFragment.show(getFragmentManager(),
                                                "infoDialog");
                                    }
                                }
                            });
                } else if (getLogin().isEmpty() && !getPassword().isEmpty()) { //else == dialog
                    progressDialogFragment.dismiss();                           //isn't closed


                    successDialogFragment.setArguments(SuccessBundle.assemble(false,
                            App.getContext().getString(R.string.message_enter_login)));
                    successDialogFragment.show(manager, "infoDialog");
                }
                else if (!getLogin().isEmpty() && getPassword().isEmpty()) {
                    progressDialogFragment.dismiss();

                    successDialogFragment.setArguments(SuccessBundle.assemble(false,
                            App.getContext().getString(R.string.message_enter_password)));
                    successDialogFragment.show(manager, "infoDialog");
                }
                else {
                    progressDialogFragment.dismiss();

                    successDialogFragment.setArguments(SuccessBundle.assemble(false,
                            App.getContext().getString(R.string.message_enter_login_and_password)));
                    successDialogFragment.show(manager, "infoDialog");
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00A0FF"));
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
