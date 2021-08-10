package com.risuplabs.ureport_r4v.ui.opinions.media_capture;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseSurveyorActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityCaptureAudioBinding;
import com.risuplabs.ureport_r4v.utils.surveyor.Logger;
import com.risuplabs.ureport_r4v.utils.surveyor.SurveyorIntent;
import com.risuplabs.ureport_r4v.utils.ui.IconTextView;

public class CaptureAudioActivity extends BaseSurveyorActivity<ActivityCaptureAudioBinding> {

    private boolean isRecording = false;
    private MediaRecorder mediaRecorder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture_audio;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public boolean requireLogin() {
        return false;
    }

    public void recordAudio() {
        isRecording = true;

        String output = getIntent().getStringExtra(SurveyorIntent.EXTRA_MEDIA_FILE);

        Logger.d("Recording audio to " + output + "...");

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOutputFile(output);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.prepare();

        } catch (Exception e) {
            Logger.e("Unable to create media recorder", e);
        }

        mediaRecorder.start();
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
        releaseMediaRecorder();
        isRecording = false;

        Intent returnIntent = new Intent();
        returnIntent.putExtra(SurveyorIntent.EXTRA_MEDIA_FILE, getIntent().getStringExtra(SurveyorIntent.EXTRA_MEDIA_FILE));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void toggleRecording(View view) {
        if (!isRecording) {
            Resources res = getResources();

            IconTextView button = (IconTextView) getViewCache().getView(R.id.button_capture);
            button.setTextColor(res.getColor(R.color.recording));

            TextView instructions = (TextView) getViewCache().getView(R.id.text_instructions);
            instructions.setText(R.string.tap_to_stop);

            getViewCache().getView(R.id.content_view).setBackgroundColor(res.getColor(R.color.warning));
            recordAudio();
        } else {
            stopRecording();
        }
    }
}