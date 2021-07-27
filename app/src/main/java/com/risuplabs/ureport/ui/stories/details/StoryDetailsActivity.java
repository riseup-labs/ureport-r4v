package com.risuplabs.ureport.ui.stories.details;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.base.BaseActivity;
import com.risuplabs.ureport.databinding.ActivityStoryDetailsBinding;
import com.risuplabs.ureport.utils.IntentConstant;
import com.risuplabs.ureport.utils.StorageUtils;
import com.risuplabs.ureport.utils.pref_manager.PrefKeys;

import java.util.Locale;

public class StoryDetailsActivity extends BaseActivity<ActivityStoryDetailsBinding> {

    String content_file_name;
    int content_id = 0;
    TextToSpeech tts;
    String data;
    String sentence = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_story_details;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        Bundle b = new Bundle();
        if (getIntent().getBundleExtra(IntentConstant.INTENT_DATA) != null) {
            b = getIntent().getBundleExtra(IntentConstant.INTENT_DATA);
            content_id = b.getInt(IntentConstant.CONTENT_ID);
        }

        content_file_name = prefManager.getInt(PrefKeys.ORG_ID,37) + "_" + content_id;

        WebView webview = findViewById(R.id.webStory);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webview.setWebChromeClient(new WebChromeClient());

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.d(TAG, "onInit: Language not support");
                    }else{
                        Log.d(TAG, "onInit: success");
                    }
                }else{
                    Log.d(TAG, "onInit: failed");
                }
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                Log.d("Utterance", "onStart: "+utteranceId);
            }

            @Override
            public void onDone(String utteranceId) {
                Log.d("Utterance", "onDone: "+utteranceId);
            }

            @Override
            public void onError(String utteranceId) {
                Log.d("Utterance", "onError: "+utteranceId);
            }

            @Override
            public void onRangeStart(String utteranceId,
                                     final int start,
                                     final int end,
                                     int frame) {
                Log.i("XXX", "onRangeStart() ... utteranceId: " + utteranceId + ", start: " + start
                        + ", end: " + end + ", frame: " + frame);

                // onRangeStart (and all UtteranceProgressListener callbacks) do not run on main thread
                // ... so we explicitly manipulate views on the main thread:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=start ; i<end ; i++){
                            sentence = sentence+data.charAt(i);
                        }
                        Log.d("Utterance", "reading: "+sentence);
                    }
                });

            }
        });

        binding.btnTtsLottie.setOnClickListener(v ->{
            tts.speak(data,TextToSpeech.QUEUE_FLUSH,null);
        });

        Handler handler = new Handler();
        Runnable r = () -> {
            String content = StorageUtils.getContent(StoryDetailsActivity.this, content_file_name);
            data = Html.fromHtml(content).toString();
            Log.d(TAG, "StoryData: "+data);
            handler.post(new Runnable() {
                public void run() {
                    webview.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                }
            });
        };



        Thread t = new Thread(r);
        t.start();

        binding.backButton.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}