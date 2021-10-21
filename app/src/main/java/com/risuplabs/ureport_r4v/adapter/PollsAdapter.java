package com.risuplabs.ureport_r4v.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.risuplabs.ureport_r4v.databinding.ItemPollGenderBinding;
import com.risuplabs.ureport_r4v.databinding.ItemPollLocationBinding;
import com.risuplabs.ureport_r4v.databinding.ItemPollsBinding;
import com.risuplabs.ureport_r4v.model.results.ModelQuestion;
import com.risuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.risuplabs.ureport_r4v.ui.results.polls.SetupAgeResult.setUpAge;
import static com.risuplabs.ureport_r4v.ui.results.polls.SetupGenderResult.setupGenderResult;
import static com.risuplabs.ureport_r4v.ui.results.polls.SetupLocationResult.setupLocation;
import static com.risuplabs.ureport_r4v.ui.results.polls.SetupPieChart.setUpPieChart;
import static com.risuplabs.ureport_r4v.ui.results.polls.SetupPollStatistics.setUpStatistics;
import static com.risuplabs.ureport_r4v.ui.results.polls.SetupWordCloud.setUpWordCloud;

public class PollsAdapter extends BaseRecyclerViewAdapter<ModelQuestion, ItemPollsBinding> {

    Context context;
    String pollDate;
    TextView summeryText;

    public PollsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_polls;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemPollsBinding> holder, int position) {


            prefManager = new SharedPrefManager(context);
            ModelQuestion item = items.get(position);
            ItemPollsBinding binding = holder.binding;
            binding.question.setText(item.title);
            Log.d(TAG, "onBindViewHolder: " + item.title);
            performClickOnTab(binding);
            int numSub = item.results.set;
            int numSup = item.results.unset + numSub;
            binding.textViewResponded.setText(
                    context.getString(R.string.v1_ureport_out_of)
                            .replace("%sup", String.valueOf(numSup))
                            .replace("%sub", String.valueOf(numSub))
            );
            binding.textViewDate.setText(pollDate);

            if (item.results_by_age != null) {
                binding.layoutWordCloud.chartParent.setVisibility(View.GONE);
                binding.layoutStatistics.stateParent.setVisibility(View.VISIBLE);
                setUpAge(context, prefManager, item, binding.layoutAge);
                setUpStatistics(context, prefManager, items.get(position), binding.layoutStatistics);
                setupGenderResult(context, prefManager, item, binding.layoutGender);
                setupLocation(item, binding.layoutLocation, context);
            }else{

                binding.layoutWordCloud.chartParent.setVisibility(View.VISIBLE);
                binding.layoutStatistics.stateParent.setVisibility(View.GONE);
                binding.linearLayout3.setVisibility(View.GONE);
                binding.linearLayout4.setVisibility(View.GONE);

                setUpWordCloud(items.get(position), binding.layoutWordCloud, binding);
            }

    }

    public void performClickOnTab(ItemPollsBinding binding) {
        binding.textViewStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationsOnClickingTAb(binding, 0);
            }
        });

        binding.textViewlocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationsOnClickingTAb(binding, 1);
            }
        });
        binding.textViewGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationsOnClickingTAb(binding, 2);
            }
        });
        binding.textViewAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationsOnClickingTAb(binding, 3);
            }
        });
    }

    public void operationsOnClickingTAb(ItemPollsBinding binding, int tab) {

        switch (tab) {
            case 0:
                binding.textViewStatistics.setTextColor(context.getResources().getColor(R.color.white));
                binding.textViewStatistics.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));

                binding.textViewlocations.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewlocations.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewGender.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewGender.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewAge.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewAge.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.layoutStatistics.stateParent.setVisibility(View.VISIBLE);
                binding.layoutAge.ageParent.setVisibility(View.GONE);
                binding.layoutGender.genderParent.setVisibility(View.GONE);
                binding.layoutLocation.parentLocation.setVisibility(View.GONE);
                binding.linearLayout3.setBackground(context.getResources().getDrawable(R.drawable.poll_selector_border));
                break;

            case 1:
                binding.textViewStatistics.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewStatistics.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewlocations.setTextColor(context.getResources().getColor(R.color.white));
                binding.textViewlocations.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));

                binding.textViewGender.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewGender.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewAge.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewAge.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.layoutStatistics.stateParent.setVisibility(View.GONE);
                binding.layoutAge.ageParent.setVisibility(View.GONE);
                binding.layoutGender.genderParent.setVisibility(View.GONE);
                binding.layoutLocation.parentLocation.setVisibility(View.VISIBLE);
                binding.linearLayout3.setBackground(context.getResources().getDrawable(R.drawable.poll_selector_border));
                break;

            case 2:
                binding.textViewStatistics.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewStatistics.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewlocations.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewlocations.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewGender.setTextColor(context.getResources().getColor(R.color.white));
                binding.textViewGender.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));

                binding.textViewAge.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewAge.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.layoutStatistics.stateParent.setVisibility(View.GONE);
                binding.layoutAge.ageParent.setVisibility(View.GONE);
                binding.layoutGender.genderParent.setVisibility(View.VISIBLE);
                binding.layoutLocation.parentLocation.setVisibility(View.GONE);
                binding.linearLayout3.setBackground(context.getResources().getDrawable(R.drawable.poll_selector_border));
                break;

            case 3:
                binding.textViewStatistics.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewStatistics.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewlocations.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewlocations.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewGender.setTextColor(context.getResources().getColor(R.color.dark_gray));
                binding.textViewGender.setBackgroundColor(context.getResources().getColor(R.color.white));

                binding.textViewAge.setTextColor(context.getResources().getColor(R.color.white));
                binding.textViewAge.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));

                binding.layoutAge.ageParent.setVisibility(View.VISIBLE);
                binding.layoutStatistics.stateParent.setVisibility(View.GONE);
                binding.layoutGender.genderParent.setVisibility(View.GONE);
                binding.layoutLocation.parentLocation.setVisibility(View.GONE);
                binding.linearLayout3.setBackground(context.getResources().getDrawable(R.drawable.poll_selector_border));
                break;
        }

    }

    public void setPollDate(String pollDate) {
        this.pollDate = pollDate;
        notifyDataSetChanged();
    }


}
