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

import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.Listener.Listener;
import com.example.worldskills.Model.Card;
import com.example.worldskills.Model.User;
import com.example.worldskills.R;
import com.example.worldskills.Repository.RepositoryProfile;
import com.example.worldskills.Utility.App;
import com.example.worldskills.Utility.SuccessBundle;

public class BlockCardDialogFragment extends DialogFragment {
    private Card card;
    private Listener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_block_card, null))
                .setMessage(R.string.block)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.block, new DialogInterface.OnClickListener() {
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
                progressDialogFragment.show(manager, "progressDialog");
                if (!getPassword().isEmpty()) {
                    RepositoryProfile.getUserToken(User.getCurrentUser().getLogin(), getPassword(),
                            new DataListener() {
                        @Override
                        public void onGetData(boolean isValid, String info) {
                            if (isValid) {
                                RepositoryProfile.blockCard(card.getNumber(),
                                        new DataListener() {
                                            @Override
                                            public void onGetData(boolean isValid, String info) {
                                                progressDialogFragment.dismiss();

                                                successDialogFragment.setArguments(SuccessBundle
                                                        .assemble(isValid, info));
                                                successDialogFragment.show(manager,
                                                        "infoDialog");
                                                if (isValid) {
                                                    card.setBlocked(true);
                                                    listener.onCompletion();
                                                    successListener.onCompletion();
                                                }
                                            }
                                        });
                            } else {
                                progressDialogFragment.dismiss();
                                successDialogFragment.setArguments(SuccessBundle
                                        .assemble(false, info));
                                successDialogFragment.show(getFragmentManager(),
                                        "infoDialog");
                            }
                        }
                    });
                } else {
                    progressDialogFragment.dismiss();

                    successDialogFragment.setArguments(SuccessBundle
                            .assemble(false,
                                    App.getContext().getString(R.string.message_enter_password)));
                    successDialogFragment.show(manager, "infoDialog");
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00A0FF"));
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    private String getPassword() {
        EditText passwordView = getDialog().findViewById(R.id.block_card_password_edit);
        return passwordView.getText().toString();
    }
}
