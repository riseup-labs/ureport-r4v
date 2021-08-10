package com.risuplabs.ureport_r4v.utils.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class BlockingProgress extends ProgressDialog {

    public BlockingProgress(Context context, int title, int message) {
        super(context);

        setTitle(title);
        setMessage(getContext().getString(message));
        setIndeterminate(false);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {
            dismiss();
        });
        setProgress(0);
        setMax(100);
    }
}
