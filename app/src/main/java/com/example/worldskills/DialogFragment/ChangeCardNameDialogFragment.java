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

import com.example.worldskills.Model.Card;
import com.example.worldskills.Utility.App;
import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.Listener.Listener;
import com.example.worldskills.R;
import com.example.worldskills.Repository.RepositoryProfile;
import com.example.worldskills.Utility.SuccessBundle;

public class ChangeCardNameDialogFragment extends DialogFragment {
    private Card card;
    private DataListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Dialog_Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_change_card_name, null))
                .setMessage(R.string.rename)
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
                if (!getName().isEmpty()) {
                    RepositoryProfile.changeCardName(getName(), card.getNumber(),
                            new DataListener() {
                                @Override
                                public void onGetData(boolean isValid, String info) {
                                    progressDialogFragment.dismiss();

                                    successDialogFragment.setArguments(SuccessBundle
                                            .assemble(isValid, info));
                                    successDialogFragment.show(manager,
                                            "infoDialog");
                                    if (isValid) {
                                        card.setName(getName());
                                        listener.onGetData(true, getName());
                                        successListener.onCompletion();
                                    }
                                }
                            });
                } else {
                    progressDialogFragment.dismiss();

                    successDialogFragment.setArguments(SuccessBundle
                            .assemble(false,
                                    App.getContext().getString(R.string.message_enter_login)));
                    successDialogFragment.show(manager, "infoDialog");
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00A0FF"));
    }

    @NonNull
    private String getName() {
        EditText nameView = getDialog().findViewById(R.id.change_card_name_edit);
        return nameView.getText().toString();
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setListener(DataListener listener) {
        this.listener = listener;
    }
}
