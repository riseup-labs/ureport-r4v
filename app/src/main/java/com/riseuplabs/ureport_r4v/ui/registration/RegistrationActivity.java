package com.riseuplabs.ureport_r4v.ui.registration;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greysonparrelli.permiso.Permiso;
import com.nyaruka.goflow.mobile.Environment;
import com.nyaruka.goflow.mobile.Event;
import com.nyaruka.goflow.mobile.Hint;
import com.nyaruka.goflow.mobile.MsgIn;
import com.nyaruka.goflow.mobile.Resume;
import com.nyaruka.goflow.mobile.SessionAssets;
import com.nyaruka.goflow.mobile.Trigger;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseSubmissionActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityRegistrationBinding;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.surveyor.data.Submission;
import com.riseuplabs.ureport_r4v.surveyor.engine.Contact;
import com.riseuplabs.ureport_r4v.surveyor.engine.Engine;
import com.riseuplabs.ureport_r4v.surveyor.engine.EngineException;
import com.riseuplabs.ureport_r4v.surveyor.engine.Session;
import com.riseuplabs.ureport_r4v.surveyor.engine.Sprint;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow;
import com.riseuplabs.ureport_r4v.surveyor.task.DownloadOrgTask;
import com.riseuplabs.ureport_r4v.surveyor.task.SubmitSubmissionsTask;
import com.riseuplabs.ureport_r4v.ui.opinions.media_capture.CaptureAudioActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.media_capture.CaptureLocationActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.media_capture.CaptureVideoActivity;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.riseuplabs.ureport_r4v.utils.ImageUtils;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialog;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialogInterface;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.riseuplabs.ureport_r4v.utils.surveyor.Logger;
import com.riseuplabs.ureport_r4v.utils.surveyor.SurveyUtils;
import com.riseuplabs.ureport_r4v.utils.surveyor.SurveyorIntent;
import com.riseuplabs.ureport_r4v.utils.ui.BlockingProgress;
import com.riseuplabs.ureport_r4v.utils.ui.IconTextView;
import com.riseuplabs.ureport_r4v.utils.ui.ViewCache;
import com.riseuplabs.ureport_r4v.utils.widget.ChatBubbleView;
import com.riseuplabs.ureport_r4v.utils.widget.IconLinkView;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class RegistrationActivity extends BaseSubmissionActivity<ActivityRegistrationBinding> {

    public DownloadOrgTask donx;
    private Org org;
    String orgUUID = "invalid";
    List<Flow> registration_flows = new ArrayList<>();

    // the different types of requests for media
    public static final String REQUEST_IMAGE = "image";
    public static final String REQUEST_AUDIO = "audio";
    public static final String REQUEST_VIDEO = "video";
    public static final String REQUEST_GPS = "geo";

    // custom request codes passed to media capture activities
    private static final int RESULT_IMAGE = 1;
    private static final int RESULT_VIDEO = 2;
    private static final int RESULT_AUDIO = 3;
    private static final int RESULT_GPS = 4;

    private static final int MAX_IMAGE_DIMENSION = 1024;
    private static final int MAX_THUMB_DIMENSION = 600;

    private LinearLayout chatHistory;
    private IconTextView sendButtom;
    private EditText chatCompose;
    private ScrollView scrollView;

    private Session session;
    private Submission submission;
    ExoPlayer exoPlayer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        binding.backButton.setOnClickListener(v->{
            onBackPressed();
        });

        orgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);
        refresh();
        if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BRAZIL_LABEL)){
            registration_flows.add(new Flow(AppConstant.REG_FLOW_UUID_BRASIL));
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.ECUADOR_LABEL)){
            registration_flows.add(new Flow(AppConstant.REG_FLOW_UUID_ECUADOR));
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BOLIVIA_LABEL)){
            registration_flows.add(new Flow(AppConstant.REG_FLOW_UUID_BOLIVIA));
        }

        download(registration_flows);

    }

    @Override
    public boolean requireLogin() {
        return false;
    }

    com.riseuplabs.ureport_r4v.surveyor.data.Flow flow;

    public void download(List<Flow> flows) {

        final BlockingProgress progressModal = new BlockingProgress(RegistrationActivity.this, R.string.one_moment, R.string.refresh_org);
        progressModal.setOnDismissListener(dialogInterface -> {
            donx.cancel(true);
        });
        progressModal.show();



        donx = new DownloadOrgTask(new DownloadOrgTask.Listener() {
            @Override
            public void onProgress(int percent) {
                progressModal.setProgress(percent);
            }

            @Override
            public void onComplete() {
                try {
                    playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);
                    StaticMethods.setLocalUpdateDate(prefManager, "surveyor_last_updated_local");

                    String orgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);
                    initUI();

                    try {
                        org = getSurveyor().getOrgService().get(orgUUID,"reg");
                        Environment environment = Engine.createEnvironment(org);
                        SessionAssets assets = Engine.createSessionAssets(environment, Engine.loadAssets(org.getAssets("reg")));



                        if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BRAZIL_LABEL)){
                            flow = org.getFlow(AppConstant.REG_FLOW_UUID_BRASIL);
                        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.ECUADOR_LABEL)){
                            flow = org.getFlow(AppConstant.REG_FLOW_UUID_ECUADOR);
                        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BOLIVIA_LABEL)){
                            flow = org.getFlow(AppConstant.REG_FLOW_UUID_BOLIVIA);
                        }


                        setTitle(flow.getName());

                        Trigger trigger = Engine.createManualTrigger(environment, Contact.createEmpty(assets), flow.toReference());

                        Pair<Session, Sprint> ss = Engine.getInstance().newSession(assets, trigger);
                        session = ss.getLeft();
                        submission = getSurveyor().getSubmissionService().newSubmission(org, flow);

                        handleEngineSprint(ss.getRight());

                    } catch (EngineException | IOException e) {
                        handleProblem("Unable to start flow", e);
                    }


                } catch (Exception e) {
                    //
                }
                progressModal.dismiss();
            }

            @Override
            public void onFailure() {
                progressModal.dismiss();
                Toast.makeText(RegistrationActivity.this, getString(R.string.error_org_refresh), Toast.LENGTH_SHORT).show();
            }
        },flows,"reg");
        donx.execute(getOrg());
    }

    @Override
    protected List<Submission> getPendingSubmissions() {
        return SurveyorApplication.get().getSubmissionService().getCompleted(getOrg());
    }

    @Override
    protected Org getOrg() {
        return org;
    }

    @Override
    protected void refresh() {
        if (orgUUID.equals("invalid")) {
            return;
        } // Not Logged In

        if (org == null) {
            try {
                org = SurveyorApplication.get().getOrgService().get(orgUUID,"reg");
            } catch (Exception e) {
                Logger.e("Unable to load org", e);
//                showBugReportDialog();
                finish();
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initUI();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            scrollView.smoothScrollTo(0, scrollView.getBottom());
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            scrollView.smoothScrollTo(0, scrollView.getBottom());
        }
    }

    private void initUI() {
        chatHistory = findViewById(R.id.chat_history);
        chatCompose = findViewById(R.id.chat_compose);
        sendButtom = findViewById(R.id.button_send);
        scrollView = findViewById(R.id.scroll);


        // allow messages to be sent with the enter key
        chatCompose.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    onActionSend(sendButtom);
                    return true;
                }
                return false;
            }
        });

        // or the send button on the keyboard
        chatCompose.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && actionId == EditorInfo.IME_ACTION_SEND && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onActionSend(sendButtom);
                    return true;
                }
                return false;
            }
        });

        // change the color of the send button when there is text in the compose box
        chatCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    sendButtom.setIconColor(R.color.magenta);
                } else {
                    sendButtom.setIconColor(R.color.light_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        confirmDiscardRun();
    }

    /**
     * User pressed the media request button
     */
    public void onActionMedia(View view) {
        View media = getViewCache().getView(R.id.media_icon);
        if (session.isWaiting()) {
            if (REQUEST_IMAGE.equals(media.getTag())) {
                captureImage();
            } else if (REQUEST_VIDEO.equals(media.getTag())) {
                captureVideo();
            } else if (REQUEST_AUDIO.equals(media.getTag())) {
                captureAudio();
            } else if (REQUEST_GPS.equals(media.getTag())) {
                captureLocation();
            }
        }
    }

    /**
     * Captures an image from the camera
     */
    private void captureImage() {

        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            @SuppressWarnings("ResourceType")
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.areAllPermissionsGranted()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ComponentName cameraPkg = intent.resolveActivity(getPackageManager());

                    if (cameraPkg == null) {
                        handleProblem("Can't find camera device", null);
                        return;
                    }
                    Logger.d("Camera package is " + cameraPkg.toString());

                    File cameraOutput = getCameraOutput();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getSurveyor().getUriForFile(cameraOutput));
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(intent, RESULT_IMAGE);
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                RegistrationActivity.this.showRationaleDialog(R.string.permission_camera, callback);
            }

        }, Manifest.permission.CAMERA);
    }

    /**
     * Captures a video from the camera
     */
    private void captureVideo() {
        Intent intent = new Intent(this, CaptureVideoActivity.class);
        intent.putExtra(SurveyorIntent.EXTRA_MEDIA_FILE, getVideoOutput().getAbsolutePath());
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, RESULT_VIDEO);
    }

    /**
     * Captures an audio recording from the microphone
     */
    private void captureAudio() {
        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            @SuppressWarnings("ResourceType")
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.areAllPermissionsGranted()) {
                    Intent intent = new Intent(RegistrationActivity.this, CaptureAudioActivity.class);
                    intent.putExtra(SurveyorIntent.EXTRA_MEDIA_FILE, getAudioOutput().getAbsolutePath());
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(intent, RESULT_AUDIO);
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                RegistrationActivity.this.showRationaleDialog(R.string.permission_record, callback);
            }

        }, Manifest.permission.RECORD_AUDIO);
    }

    /**
     * Captures the current location
     */
    private void captureLocation() {
        Intent intent = new Intent(this, CaptureLocationActivity.class);
        startActivityForResult(intent, RESULT_GPS);
    }

    private File getCameraOutput() {
        return new File(getSurveyor().getExternalCacheDir(), "camera.jpg");
    }

    private File getVideoOutput() {
        return new File(getSurveyor().getExternalCacheDir(), "video.mp4");
    }

    private File getAudioOutput() {
        return new File(getSurveyor().getExternalCacheDir(), "audio.m4a");
    }

    /**
     * @see Activity#onActivityResult(int, int, Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        MsgIn msg = null;
        try {
            if (requestCode == RESULT_IMAGE) {
                File output = getCameraOutput();
                if (output.exists()) {
                    Bitmap full = BitmapFactory.decodeFile(output.getAbsolutePath());
                    Bitmap scaled = ImageUtils.scaleToMax(full, MAX_IMAGE_DIMENSION);
                    // correct rotation if necessary
                    int rotation = ImageUtils.getExifRotation(output.getAbsolutePath());
                    if (rotation != 0) {
                        Logger.d("Correcting EXIF rotation of " + rotation + " degrees");
                        scaled = ImageUtils.rotateImage(scaled, rotation);
                    }
                    // encode as JPEG and save to submission
                    byte[] asJpg = ImageUtils.convertToJPEG(scaled);
                    Uri uri = submission.saveMedia(asJpg, "jpg");
                    Logger.d("Saved image capture to " + uri);
                    // create thumbnail and add to chat
                    Bitmap thumb = ImageUtils.scaleToMax(scaled, MAX_THUMB_DIMENSION);
                    addMedia(thumb, uri.toString(), R.string.media_image);
                    msg = Engine.createMsgIn("", "image/jpeg:" + uri);
                    output.delete();
                }
            } else if (requestCode == RESULT_VIDEO) {
                File output = getVideoOutput();
                if (output.exists()) {
                    Bitmap thumb = ImageUtils.thumbnailFromVideo(output);
                    Uri uri = submission.saveMedia(output);
                    addMedia(thumb, uri.toString(), R.string.media_video);
                    Logger.d("Saved video capture to " + uri);
                    msg = Engine.createMsgIn("", "video/mp4:" + uri);
                    output.delete();
                }

            } else if (requestCode == RESULT_AUDIO) {
                File output = getAudioOutput();
                if (output.exists()) {
                    Uri uri = submission.saveMedia(output);
                    Logger.d("Saved audio capture to " + uri);
                    addMediaLink(getString(R.string.made_recording), uri.toString(), R.string.media_audio);
                    msg = Engine.createMsgIn("", "audio/mp4:" + uri);
                    output.delete();
                }
            } else if (requestCode == RESULT_GPS) {
                double latitude = data.getDoubleExtra("latitude", 0.0);
                double longitude = data.getDoubleExtra("longitude", 0.0);
                String coords = "geo:" + latitude + "," + longitude;
                String url = coords + "?q=" + latitude + "," + longitude + "(Location)";
                addMediaLink(latitude + "," + longitude, url, R.string.media_location);
                msg = Engine.createMsgIn("", coords);
            }
        } catch (IOException e) {
            handleProblem("Unable capture media", e);
        }
        // if we have a message we can try to resume now...
        if (msg != null) {
            resumeSession(msg);
        }
    }

    /**
     * Something has gone wrong... show the user the big report dialog
     */
    private void handleProblem(String toastMsg, Throwable e) {
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();

        if (e != null) {
            Logger.e("Error running flow", e);
            showBugReportDialog();
        }

        finish();
    }

    private void resumeSession(MsgIn msg) {
        try {
            Resume resume = Engine.createMsgResume(null, null, msg);
            Sprint sprint = session.resume(resume);

            handleEngineSprint(sprint);

        } catch (EngineException | IOException e) {
            handleProblem("Couldn't handle message", e);
        }

        // scroll us to the bottom
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.setSmoothScrollingEnabled(true);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                // put the focus back on the chat box
                chatCompose.requestFocus();
            }
        }, 100);

        // refresh our menu
        invalidateOptionsMenu();
    }

    /**
     * User pressed the send button
     */
    public void onActionSend(View sendButton) {
        if (!session.getStatus().equals("waiting")) {
            return;
        }

        // Remove All Quick Response
        getViewCache().hide(R.id.quick_replies);
        ((LinearLayout) findViewById(R.id.quick_replies)).removeAllViews();
        getViewCache().show(R.id.chat_box);

        EditText chatBox = findViewById(R.id.chat_compose);
        String message = chatBox.getText().toString();
        hideKeyboard(this);

        if (message.trim().length() > 0){
            chatBox.setText("");
            final MsgIn msg = Engine.createMsgIn(message);
            addMessage(message, true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    resumeSession(msg);
                    playNotification(prefManager, getApplicationContext(), R.raw.receive_message_sound);
                }
            }, (int)(Math.random() * 1500 + 700));


        }
    }

    public void CreateExternalID() {
        if (!session.getStatus().equals("waiting")) {
            return;
        }

        // Remove All Quick Response
        getViewCache().hide(R.id.quick_replies);
        ((LinearLayout) findViewById(R.id.quick_replies)).removeAllViews();
        getViewCache().show(R.id.chat_box);

        String message = StaticMethods.getAlphaNumericString(15);
        hideKeyboard(this);

        if (message.trim().length() > 0){
            prefManager.putString(PrefKeys.EXTERNAL_ID,message);
            final MsgIn msg = Engine.createMsgIn(message);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resumeSession(msg);
                    playNotification(prefManager, getApplicationContext(), R.raw.receive_message_sound);
                }
            }, (int)(Math.random() * 1500 + 700));
        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Handles new session state and events after interaction with the flow engine
     *
     * @param sprint the sprint from the engine
     */
    private void handleEngineSprint(Sprint sprint) throws IOException, EngineException {
        for (Event event : sprint.getEvents()) {
            Logger.d("Event: " + event.payload());

            JsonObject asObj = new JsonParser().parse(event.payload()).getAsJsonObject();

            if (event.type().equals("msg_created")) {
                JsonObject msg = asObj.get("msg").getAsJsonObject();
                if(msg.get("text").getAsString().contains("<UUID>")){
                    CreateExternalID();
                }else{
                    addMessage(msg.get("text").getAsString(), false);
                }

                if(msg.get("quick_replies") != null){
                    JsonArray quick_replies = msg.get("quick_replies").getAsJsonArray();
                    LinearLayout quick_reply_box = findViewById(R.id.quick_replies);

                    for(int i = 0; i < quick_replies.size(); i++) {
                        String reply_data = quick_replies.get(i).getAsString();

                        View quickTemplate = LayoutInflater.from(this).inflate(R.layout.v1_quick_reply_button, null);

                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, getResources().getDisplayMetrics());
                        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
                        layoutParams.setMargins(0, space, 0, space);

                        ((Button) quickTemplate).setText(reply_data);
                        quick_reply_box.addView(quickTemplate, layoutParams);
                    }

                    getViewCache().show(R.id.quick_replies);
                }
            }
        }

        if (!session.isWaiting()) {
            addLogMessage(R.string.log_flow_complete);

            ViewCache cache = getViewCache();
            cache.hide(R.id.chat_box, true);
            cache.hide(R.id.container_request_media);
            cache.show(R.id.completed_session_actions);
        } else {
            waitForInput(session.getWait().hint());
        }

        submission.saveSession(session);
        submission.saveNewModifiers(sprint.getModifiers());
        submission.saveNewEvents(sprint.getEvents());

        Logger.d("Persisted new events and modifiers after engine sprint");
    }

    private void waitForInput(Hint hint) {
        ViewCache vc = getViewCache();
        TextView mediaButton = vc.getTextView(R.id.media_icon);
        TextView mediaText = vc.getTextView(R.id.media_text);

        String mediaType = hint != null ? hint.type() : "";
        switch (mediaType) {
            case "image":
                mediaButton.setText(getString(R.string.icon_photo_camera));
                mediaButton.setTag(REQUEST_IMAGE);
                mediaText.setText(getString(R.string.request_image));
                vc.hide(R.id.chat_box, true);
                vc.show(R.id.container_request_media);
                break;
            case "video":
                mediaButton.setText(getString(R.string.icon_videocam));
                mediaButton.setTag(REQUEST_VIDEO);
                mediaText.setText(getString(R.string.request_video));
                vc.hide(R.id.chat_box, true);
                vc.show(R.id.container_request_media);
                break;
            case "audio":
                mediaButton.setText(getString(R.string.icon_mic));
                mediaButton.setTag(REQUEST_AUDIO);
                mediaText.setText(getString(R.string.request_audio));
                vc.hide(R.id.chat_box, true);
                vc.show(R.id.container_request_media);
                break;
            case "location":
                mediaButton.setText(getString(R.string.icon_place));
                mediaButton.setTag(REQUEST_GPS);
                mediaText.setText(getString(R.string.request_gps));
                vc.hide(R.id.chat_box, true);
                vc.show(R.id.container_request_media);
                break;
            default:
                vc.show(R.id.chat_box);
                vc.hide(R.id.container_request_media);
                break;
        }
    }

    private void addLogMessage(int message) {
        getLayoutInflater().inflate(R.layout.item_log_message, chatHistory);
        TextView view = (TextView) chatHistory.getChildAt(chatHistory.getChildCount() - 1);
        view.setText(getString(message));
    }

    private void addMessage(String text, boolean inbound) {
        getLayoutInflater().inflate(R.layout.item_chat_bubble, chatHistory);
        ChatBubbleView bubble = (ChatBubbleView) chatHistory.getChildAt(chatHistory.getChildCount() - 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bubble.setTransitionName(getString(R.string.transition_chat));
        }

        bubble.setMessage(text, inbound);
        scrollToBottom();
    }

    private void addMedia(Bitmap image, String url, int type) {
        getLayoutInflater().inflate(R.layout.item_chat_bubble, chatHistory);
        ChatBubbleView bubble = (ChatBubbleView) chatHistory.getChildAt(chatHistory.getChildCount() - 1);
        bubble.setThumbnail(image, url, type);
        scrollToBottom();
    }

    private void addMediaLink(String title, String url, int type) {
        getLayoutInflater().inflate(R.layout.item_icon_link, chatHistory);
        IconLinkView icon = (IconLinkView) chatHistory.getChildAt(chatHistory.getChildCount() - 1);
        icon.initialize(title, type, url);
        scrollToBottom();
    }

    private void scrollToBottom() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    /**
     * User pressed the save button
     *
     * @param view the button
     */
    public void onActionSave(View view) {
        try {
            submission.complete();
            if (ConnectivityCheck.isConnected(this)) {
                onActionSubmit(null);
            } else {
                new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                    @Override
                    public void retry() {
                        submitToServer();
                    }

                    @Override
                    public void cancel() {}
                });
            }
        } catch (IOException e) {
            Logger.e("unable to complete submission", e);
        }
    }

    public void submitToServer() {
        if (ConnectivityCheck.isConnected(this)) {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            doSubmit();
        } else {
            new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                @Override
                public void retry() {
                    submitToServer();
                }

                @Override
                public void cancel() {
                    // None
                }
            });
        }
    }

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
            public void onComplete(int total) throws IOException {

                prefManager.putString(SurveyorPreferences.AUTH_USERNAME,AppConstant.GUEST);

                refresh();

                progressModal.dismiss();
                playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);

                CharSequence toast = res.getQuantityString(R.plurals.submissions_sent, total, total);
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();

                removing_registration_flow();

                finish();
            }

            @Override
            public void onFailure(int numFailed) {
                progressModal.dismiss();

                Toast.makeText(getApplicationContext(), getString(R.string.error_submissions_send), Toast.LENGTH_SHORT).show();
            }
        });

        task.execute(asArray);
    }

    public void removing_registration_flow() throws IOException {

        File directory = SurveyUtils.mkdir(getFilesDir(), "orgs/"+AppConstant.ORG_UUID+"/assets.json" + SharedPrefManager.getORGID());
        Log.d(TAG, "removing_registration_flow: "+directory.getAbsolutePath());

        directory.delete();

    }

    public void onActionQuickReply(View view) {
        // Send Quick Reply
        sendButtom = findViewById(R.id.button_send);
        EditText chatBox = findViewById(R.id.chat_compose);
        String reply_text = ((Button) view).getText().toString();

        chatBox.setText(reply_text);
        onActionSend(sendButtom);


    }

    /**
     * User pressed the discard button - prompt user to confirm if they want to lose this submission
     *
     * @param view the button
     */
    public void onActionDiscard(View view) {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no);
        confirmDiscardRun();
    }

    /**
     * User pressed the cancel menu item - prompt user to confirm if they want to lose this submission
     *
     * @param item the menu item
     */
    public void onActionCancel(MenuItem item) {
        confirmDiscardRun();
    }

    private void confirmDiscardRun() {

        final Dialog dialog6 = new Dialog(this);
        dialog6.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog6.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog6.setContentView(R.layout.submission_dialog);
        dialog6.findViewById(R.id.textSubText).setVisibility(View.GONE);
        ((TextView) dialog6.findViewById(R.id.textMainText)).setText(R.string.cancel_registration);
        ((TextView) dialog6.findViewById(R.id.button_yes_text)).setText(getText(R.string.yes));
        ((TextView) dialog6.findViewById(R.id.button_no_text)).setText(getText(R.string.no));

        dialog6.findViewById(R.id.button_yes).setOnClickListener(view -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, view);
            try {
                removing_registration_flow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            submission.delete();
            dialog6.dismiss();
            finish();
        });

        dialog6.findViewById(R.id.button_no).setOnClickListener(view -> {

            playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, view);
            dialog6.dismiss();
        });

        dialog6.show();


    }

    public void onClickMedia(View view) {

        String url = (String) view.getTag(R.string.tag_url);
        int mediaType = (int) view.getTag(R.string.tag_media_type);

        // Intercept Media to Play Locally
        if(mediaType == R.string.media_video){
            playMediaDialog(url, R.string.media_video);
            return;
        }

        if(mediaType == R.string.media_audio){
            playMediaDialog(url, R.string.media_audio);
            return;
        }

        if(mediaType == R.string.media_image){
            displayImage(url);
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        switch (mediaType) {
            case R.string.media_image:
                intent.setDataAndType(Uri.parse(url), "image/*");
                break;
            case R.string.media_video:
                intent.setDataAndType(Uri.parse(url), "video/*");
                break;
            case R.string.media_audio:
                intent.setDataAndType(Uri.parse(url), "audio/*");
                break;
            case R.string.media_location:
                intent.setDataAndType(Uri.parse(url), null);
                break;
        }

        startActivity(intent);
    }

    public void playMediaDialog(String media_path, int media_type) {
        final Dialog dialog = new Dialog(this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(media_type == R.string.media_audio){
            dialog.setContentView(R.layout.audio_player_dialog);
        }else if(media_type == R.string.media_video){
            dialog.setContentView(R.layout.video_player_dialog);
        }

        dialog.setOnDismissListener(dialogInterface -> exoPlayer.release());
        dialog.show();

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Log.v("Media-Path", media_path);

        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        PlayerView playerView = dialog.findViewById(R.id.videoPlayer);
        ((SurfaceView) playerView.getVideoSurfaceView()).setZOrderOnTop(true);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(exoPlayer);

        Uri uri = Uri.parse(media_path);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getApplicationInfo().name));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        concatenatingMediaSource.addMediaSource(mediaSource);

        exoPlayer.prepare(concatenatingMediaSource);
        exoPlayer.setPlayWhenReady(false);
    }

    public void displayImage(String image_path) {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);

        //Yes Button
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.imageview_popup, null);

        ImageView dialog_image = dialoglayout.findViewById(R.id.dialogImageView);

        Glide.with(this)
                .load(image_path)
                .into(dialog_image);

        builder.setView(dialoglayout);
        builder.show();

    }
}