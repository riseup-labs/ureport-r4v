package com.risuplabs.ureport_r4v.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.risuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends ViewDataBinding> extends DaggerFragment {

    @Inject
    public SharedPrefManager prefManager;

    public T binding;
    public final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        onViewReady();
        return  binding.getRoot();
    }

    public abstract @LayoutRes int getLayoutId();
    public abstract void onViewReady();

    public void visible(View view){
        view.setVisibility(View.VISIBLE);
    }

    public void gone(View view){
        view.setVisibility(View.GONE);
    }

    public void showToast(String value){
        Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
    }

}
