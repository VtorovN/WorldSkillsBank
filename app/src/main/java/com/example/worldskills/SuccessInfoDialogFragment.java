package com.example.worldskills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessInfoDialogFragment extends DialogFragment {

    private String info;
    private boolean isSuccessful;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        isSuccessful = args.getBoolean("success", false);
        info = args.getString("info",
                isSuccessful ?
                        App.getContext().getString(R.string.message_success) :
                        App.getContext().getString(R.string.message_unknown_error));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_Material_Light_Dialog);

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_success_info, null);
        ImageView imageView = view.findViewById(R.id.fragment_success_image);
        TextView textView = view.findViewById(R.id.fragment_success_text);
        imageView.setImageResource(isSuccessful ? R.drawable.icon_success : R.drawable.icon_fail);
        textView.setText(info);
        builder.setView(view);
        builder.setPositiveButton(R.string.success_info_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
