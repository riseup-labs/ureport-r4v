package com.riseuplabs.ureport_r4v.utils.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.riseuplabs.ureport_r4v.R;

public class BlockingProgress extends ProgressDialog {

    public BlockingProgress(Context context, int title, int message) {
        super(context);

        setTitle(title);
        setMessage(getContext().getString(message));
        setIndeterminate(false);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.cancel), (dialog, which) -> {
            dismiss();
        });
        setProgress(0);
        setMax(100);
    }
}
