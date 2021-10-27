package com.riseuplabs.ureport_r4v.ui.stories.details;

import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.StorageUtils;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.getMD5;
import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class StoryDetailsActivity extends AppCompatActivity {

    final String TAG = "Story_content";

    WebView webStory;
    String imagePath = "";
    String image_name = "";
    ImageView speechButton;
    LottieAnimationView speechButtonAnim;

    String content_file_name;
    int content_id = 0;
    String data;
    String title = "";
    String date = "";
    String content = "";

    TextToSpeech ttsEngine = null;

    String lang_code = "en";

    boolean isOpen = false;

    SharedPrefManager prefManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        getWindow().setEnterTransition(null);

        Bundle b = new Bundle();
        if (getIntent().getBundleExtra(IntentConstant.INTENT_DATA) != null) {
            b = getIntent().getBundleExtra(IntentConstant.INTENT_DATA);
            content_id = b.getInt(IntentConstant.CONTENT_ID);
            title = b.getString(IntentConstant.TITLE);
            date = b.getString(IntentConstant.STORY_DATE);
            this.image_name = b.getString(IntentConstant.IMAGE_NAME);
        }

        getImagePath();

        prefManager = new SharedPrefManager(this);

        content_file_name = prefManager.getInt(PrefKeys.ORG_ID, 37) + "_" + content_id;

        isOpen = true;

        ((TextView) findViewById(R.id.activityName)).setText(R.string.v1_story_details);

        // Enable Hardware Acceleration
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        // Set Back Button
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        speechButton = findViewById(R.id.btn_tts);
        speechButtonAnim = findViewById(R.id.btn_tts_lottie);

        speechButton.setVisibility(View.VISIBLE);
        speechButtonAnim.setVisibility(View.GONE);


        webStory = findViewById(R.id.webStory);

        webStory.setWebChromeClient(new ChromeClient());

        webStory.setBackgroundColor(Color.TRANSPARENT);
        webStory.getSettings().setDomStorageEnabled(true);
        webStory.getSettings().setJavaScriptEnabled(true);
        webStory.getSettings().setAllowFileAccess(true);
        WebSettings settings = webStory.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        ttsEngine = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                ttsEngine.setLanguage(Locale.UK);
                ttsInit();
            }
        }, "com.google.android.tts");

        Handler handler = new Handler();
        Runnable r = () -> {
            content = StorageUtils.getContent(StoryDetailsActivity.this, content_file_name);

//            content = content.replaceAll("(?s)style=\".*?\"", "");
//            content = content.replaceAll("(?s)href=\".*?\"", "");
//            content = content.replaceAll("(?s)<iframe.*?</iframe>", "");
//            content = content.replaceAll("(?s)<p.*?>", "");
//            content = content.replaceAll("<p>", "");
//            content = content.replaceAll("</p>", "");
//            content = content.replaceAll("</p>", "");
//            content = content.replaceAll("<s>", "");
//            content = content.replaceAll("</s>", "");
//            content = content.replaceAll("</s>", "");
//            content = content.replaceAll(".png", "");


            data = Html.fromHtml(content).toString();
            data = data.replace("\n\n\n\n\n","\n\n");
            data = data.replace("\n\n\n\n","\n\n");
            data = data.replace("\n\n\n","\n\n");
            data = data.replace("\n", "<br>");

            handler.post(new Runnable() {
                public void run() {
                    String WebContent = LoadData("pages/story.html");
                    WebContent = WebContent.replace("#DateData", date);
                    WebContent = WebContent.replace("#ImageFile", imagePath);
                    WebContent = WebContent.replace("#TitleData", title);
                    WebContent = WebContent.replace("#TextData", content);
                    WebContent = WebContent.replace("::current_lang::", "en");
                    WebContent = WebContent.replace("::spliter::", ".");
                    webStory.loadDataWithBaseURL("file:///android_asset/pages/story.html", WebContent, "text/html; charset=utf-8", "UTF-8", null);

                }
            });
        };

        Thread t = new Thread(r);
        t.start();

        webStory.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webStory.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webStory.addJavascriptInterface(this, "SurveyorStory");


        findViewById(R.id.btn_tts).setOnClickListener(view -> toggleSound());

        findViewById(R.id.btn_tts_lottie).setOnClickListener(view -> toggleSound());

    }

    public void getImagePath() {
        File file = new File(new ContextWrapper(this).getDir("Images", 0), this.image_name);
        this.imagePath = file.getAbsolutePath();
        if (!file.exists()) {
            this.imagePath = "file:///android_asset/images/no-image.png";
        }
    }

    public void reloadTTS() {
            ttsEngine = new TextToSpeech(this, status -> {
                if (status != TextToSpeech.ERROR) {
                    ttsEngine.setLanguage(Locale.UK);
                    ttsInit();
                }
            }, "com.google.android.tts");
    }

    public void toggleSound() {
        runOnUiThread(() -> {
            if (ttsEngine == null) {
                return;
            }
            if (ttsEngine.isSpeaking()) {
                ttsEngine.stop();
                speechButton.setVisibility(View.VISIBLE);
                speechButtonAnim.setVisibility(View.GONE);
                speechButtonAnim.pauseAnimation();

                // Log Event
                Bundle logBundle = new Bundle();
                logBundle.putString("action", "stop_speech");
            } else {
                webStory.loadUrl("javascript:vueApp.playNow()");
                speechButton.setVisibility(View.GONE);
                speechButtonAnim.setVisibility(View.VISIBLE);
                speechButtonAnim.playAnimation();

                // Log Event
                Bundle logBundle = new Bundle();
                logBundle.putString("action", "play_speech");
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (isOpen) {
            isOpen = false;
        } else {
            return;
        }
        if (ttsEngine != null) {
            ttsEngine.stop();
        }
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        super.onBackPressed();
    }

    void ttsInit() {
        ttsEngine.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onDone(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webStory.loadUrl("javascript:vueApp.playNext()");
                        speechButtonAnim.setVisibility(View.GONE);
                        speechButtonAnim.pauseAnimation();
                    }
                });
            }

            @Override
            public void onError(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speechButton.setVisibility(View.GONE);
                        speechButtonAnim.setVisibility(View.VISIBLE);
                        speechButtonAnim.pauseAnimation();
                    }
                });
            }

            @Override
            public void onStart(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speechButtonAnim.setVisibility(View.VISIBLE);
                        speechButtonAnim.playAnimation();
                    }
                });
            }
        });
    }

    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }


    @JavascriptInterface
    public void ttsData(String tts_text) {
        if (ttsEngine.isSpeaking()) {
            ttsEngine.stop();
        }

        new Handler().postDelayed(() -> {
            runOnUiThread(() -> {
                speechButton.setVisibility(View.VISIBLE);
                speechButtonAnim.setVisibility(View.GONE);
                speechButtonAnim.pauseAnimation();
            });
        }, 250);

        String utterance_id = getMD5(tts_text);
        ttsEngine.speak(tts_text, TextToSpeech.QUEUE_FLUSH, null, utterance_id);
    }

    @JavascriptInterface
    public void reloadLang(String lang) {
        this.lang_code = lang;
        if (ttsEngine == null) {
            return;
        }
        if (ttsEngine.isSpeaking()) {
            ttsEngine.stop();

            new Handler().postDelayed(() -> {
                runOnUiThread(() -> {
                    speechButton.setVisibility(View.VISIBLE);
                    speechButtonAnim.setVisibility(View.GONE);
                    speechButtonAnim.pauseAnimation();
                });
            }, 250);

        }
        reloadTTS();
    }

    // Extend ChromeClient
    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {

        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }

            return BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.v1_card_background);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }



}