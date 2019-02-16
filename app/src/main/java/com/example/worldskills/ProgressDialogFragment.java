package com.example.worldskills;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;


public class ProgressDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.setContentView(inflater.inflate(R.layout.fragment_progress_bar, null));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}