package com.riseuplabs.ureport_r4v.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter< T , VB extends ViewDataBinding>
        extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<VB>> {

    public final String TAG = this.getClass().getSimpleName();
    public SharedPrefManager prefManager;

    public List<T> items = new ArrayList<>();

    public void addItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getLayoutId(),
                parent,
                false
        ));
    }

    public abstract @LayoutRes int getLayoutId();

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class BaseViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public VB binding;

        public BaseViewHolder(VB binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}