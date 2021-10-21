package com.risuplabs.ureport_r4v.ui.about;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityAboutBinding;
import com.risuplabs.ureport_r4v.ui.stories.details.StoryDetailsActivity;
import com.risuplabs.ureport_r4v.utils.StaticMethods;
import com.risuplabs.ureport_r4v.utils.StorageUtils;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {

    @Inject
    AboutViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onStart() {
        super.onStart();
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY,"rBO"));
        viewModel.getAbout("https://"+prefManager.getString(PrefKeys.ORG_LABEL)+".ureport.in/api/v1/dashblocks/org/"+prefManager.getInt(PrefKeys.ORG_ID)+"/?dashblock_type=about");
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        binding.backButton.setOnClickListener( v->{
            finish();
        });

        binding.webAbout.setWebChromeClient(new AboutActivity.ChromeClient());

        binding.webAbout.setBackgroundColor(Color.TRANSPARENT);
        binding.webAbout.getSettings().setDomStorageEnabled(true);
        binding.webAbout.getSettings().setJavaScriptEnabled(true);
        binding.webAbout.getSettings().setAllowFileAccess(true);
        WebSettings settings = binding.webAbout.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        viewModel.loadingStatus.observe(this, loading ->{
            if(loading){
                visible(binding.progressBar);
            }else{
                gone(binding.progressBar);
            }
        });

        if(prefManager.getString(PrefKeys.ABOUT_CONTENT,"").equals("")){
            viewModel.loadingStatus.setValue(true);
            viewModel.response.observe(this, response ->{
                if(response != null){
                    prefManager.putString(PrefKeys.ABOUT_TITLE,response.data.results.get(0).title);
                    prefManager.putString(PrefKeys.ABOUT_CONTENT,response.data.results.get(0).content);
                    String WebContent = LoadData("pages/about.html");
                    WebContent = WebContent.replace("#TextData", response.data.results.get(0).content);
                    WebContent = WebContent.replace("#Title", response.data.results.get(0).title);
                    binding.webAbout.loadDataWithBaseURL("file:///android_asset/pages/about.html", WebContent, "text/html; charset=utf-8", "UTF-8", null);
                }
            });
        }else{
            gone(binding.progressBar);
            String WebContent = LoadData("pages/about.html");
            WebContent = WebContent.replace("#TextData", prefManager.getString(PrefKeys.ABOUT_CONTENT));
            WebContent = WebContent.replace("#Title", prefManager.getString(PrefKeys.ABOUT_TITLE));
            binding.webAbout.loadDataWithBaseURL("file:///android_asset/pages/about.html", WebContent, "text/html; charset=utf-8", "UTF-8", null);

            viewModel.response.observe(this, response ->{
                if(response != null){
                    prefManager.putString(PrefKeys.ABOUT_TITLE,response.data.results.get(0).title);
                    prefManager.putString(PrefKeys.ABOUT_CONTENT,response.data.results.get(0).content);
                }
            });
        }





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

    // Extend ChromeClient
    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
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

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
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