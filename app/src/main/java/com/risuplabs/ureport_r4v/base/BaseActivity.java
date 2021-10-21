package com.risuplabs.ureport_r4v.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.risuplabs.ureport_r4v.utils.StaticMethods;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.risuplabs.ureport_r4v.utils.ui.ViewCache;
import com.risuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    private ViewCache m_viewCache;

    public T binding;
    public ProgressDialog progressDialog;


    public abstract @LayoutRes int getLayoutId();

    @Inject
    public SharedPrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");

        performDependencyInjection();
        performDatabinding();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        onViewReady(savedInstanceState);


    }

    private void performDatabinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    private void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void hideLoading() { progressDialog.dismiss(); }

    public void visible(View view){
        view.setVisibility(View.VISIBLE);
    }

    public void gone(View view){
        view.setVisibility(View.GONE);
    }

    public abstract void onViewReady(@Nullable Bundle savedInstanceState);

    public ViewCache getViewCache() {
        if (m_viewCache == null) {
            m_viewCache = new ViewCache(this, findViewById(android.R.id.content));
        }
        return m_viewCache;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE),prefManager.getString(PrefKeys.SELECTED_COUNTRY,"es"));
    }
}
