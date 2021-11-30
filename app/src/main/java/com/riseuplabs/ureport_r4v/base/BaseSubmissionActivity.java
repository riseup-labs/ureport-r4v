package com.riseuplabs.ureport_r4v.base;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ShareCompat;
import androidx.databinding.ViewDataBinding;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.notification.ReminderBroadcast;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.surveyor.data.Submission;
import com.riseuplabs.ureport_r4v.surveyor.task.SubmitSubmissionsTask;
import com.riseuplabs.ureport_r4v.utils.surveyor.Logger;
import com.riseuplabs.ureport_r4v.utils.ui.BlockingProgress;

import java.io.IOException;
import java.util.List;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public abstract class BaseSubmissionActivity<T extends ViewDataBinding> extends BaseSurveyorActivity<T> {

    /**
     * User has clicked a submit button
     *
     * @param view the button
     */
    public void onActionSubmit(View view) {

        final Dialog dialog7 = new Dialog(this);
        dialog7.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog7.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog7.setContentView(R.layout.submission_dialog);
        dialog7.findViewById(R.id.textSubText).setVisibility(View.GONE);
        ((TextView) dialog7.findViewById(R.id.textMainText)).setText(getString(R.string.confirm_send_submissions));
        ((TextView) dialog7.findViewById(R.id.button_yes_text)).setText(getText(R.string.yes));
        ((TextView) dialog7.findViewById(R.id.button_no_text)).setText(getText(R.string.no));

        dialog7.findViewById(R.id.button_yes).setOnClickListener(view1 -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, view1);
            dialog7.dismiss();
            doSubmit();
        });

        dialog7.findViewById(R.id.button_no).setOnClickListener(view12 -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, view12);
            dialog7.dismiss();
        });

        dialog7.show();
    }

    /**
     * Does the actual invoking of the submissions task
     */
    public void doSubmit() {
        final BlockingProgress progressModal = new BlockingProgress(this, R.string.one_moment, R.string.submit_body);
        progressModal.show();

        final List<Submission> pending = getPendingSubmissions();
        final Submission[] asArray = pending.toArray(new Submission[0]);
        final Resources res = getResources();

        SubmitSubmissionsTask task = new SubmitSubmissionsTask(new SubmitSubmissionsTask.Listener() {
            @Override
            public void onProgress(int percent) {
                progressModal.setProgress(percent);
            }

            @Override
            public void onComplete(int total) {
                
                cancelAlarm(getApplicationContext());
                
                refresh();

                progressModal.dismiss();
                playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);

                CharSequence toast = res.getQuantityString(R.plurals.submissions_sent, total, total);
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int numFailed) {
                progressModal.dismiss();

                Toast.makeText(getApplicationContext(), getString(R.string.error_submissions_send), Toast.LENGTH_SHORT).show();
            }
        });

        task.execute(asArray);
    }

    protected abstract List<Submission> getPendingSubmissions();

    protected abstract Org getOrg();

    protected abstract void refresh();

    public void showBugReportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirm_bug_report))
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> sendBugReport())
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel())
                .show();
    }

    public void sendBugReport() {
        try {
            Uri outputUri = SurveyorApplication.get().generateLogDump();

            ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .addEmailTo(getString(R.string.support_email))
                    .setSubject("Surveyor Bug Report")
                    .setText("Please include what you were doing prior to sending this report and specific details on the error you encountered.")
                    .setStream(outputUri)
                    .setChooserTitle("Send Email")
                    .startChooser();

        } catch (IOException e) {
            Logger.e("Failed to generate bug report", e);
        }
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, ReminderBroadcast.class);
        intent.putExtra("intent",1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,422,intent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        Log.d(TAG, "cancelAlarm: called");
        
    }

}
