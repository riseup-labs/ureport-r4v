package com.risuplabs.ureport.ui.results.polls;

import android.content.Context;
import android.view.View;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.databinding.ItemPollAgeBinding;
import com.risuplabs.ureport.databinding.ItemPollStatisticsBinding;
import com.risuplabs.ureport.model.results.ModelQuestion;
import com.risuplabs.ureport.model.results.ModelQuestionCategory;
import com.risuplabs.ureport.model.results.ModelResultsByAge;
import com.risuplabs.ureport.utils.AppConstant;
import com.risuplabs.ureport.utils.pref_manager.PrefKeys;
import com.risuplabs.ureport.utils.pref_manager.SharedPrefManager;


public class SetupAgeResult {

    public static void setUpAge(Context context, SharedPrefManager prefManager, ModelQuestion item, ItemPollAgeBinding binding) {

        switch(prefManager.getInt(PrefKeys.ORG_ID)){
            case AppConstant.BRAZIL_ORG_ID :
                setProgressColor(binding, R.color.color_brasil,context);
                break;
            case AppConstant.ECUADOR_ORG_ID :
                setProgressColor(binding,R.color.color_ecuador,context);
                break;
            case AppConstant.BOLIVIA_ORG_ID :
                setProgressColor(binding,R.color.color_bolivia,context);
                break;

        }

        setUpAge0(item.results_by_age.get(0),binding);
        setUpAge15(item.results_by_age.get(1),binding);
        setUpAge20(item.results_by_age.get(2),binding);
        setUpAge25(item.results_by_age.get(2),binding);
        setUpAge31(item.results_by_age.get(4),binding);
        setUpAge35(item.results_by_age.get(5),binding);

    }

    static void setUpAge0(ModelResultsByAge model, ItemPollAgeBinding binding) {

        int size = model.categories.size();
        int percentage0 = 0;
        int percentage1 = 0;
        int percentage2 = 0;
        int percentage3 = 0;
        int percentage4 = 0;

        if (size == 1) {

            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){
                percentage0 = (progressValue * 100) / maxValue;
            }

            binding.textViewLabel1a0.setText(label);
            binding.rounded1a0.setMax(maxValue);
            binding.rounded1a0.setProgress(progressValue);
            binding.textViewPercentage1a0.setText("" + percentage0 + "%");


            binding.layoutA03.setVisibility(View.GONE);
            binding.layoutA04.setVisibility(View.GONE);
            binding.layoutA05.setVisibility(View.GONE);

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a0.setText(label);
            binding.rounded1a0.setMax(maxValue);
            binding.rounded1a0.setProgress(progressValue);
            binding.textViewPercentage1a0.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a0.setText(label2);
            binding.rounded2a0.setMax(maxValue2);
            binding.rounded2a0.setProgress(progressValue2);
            binding.textViewPercentage2a0.setText("" + percentage1 + "%");

            binding.layoutA03.setVisibility(View.GONE);
            binding.layoutA04.setVisibility(View.GONE);
            binding.layoutA05.setVisibility(View.GONE);

        }
        if (size == 3) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a0.setText(label);
            binding.rounded1a0.setMax(maxValue);
            binding.rounded1a0.setProgress(progressValue);
            binding.textViewPercentage1a0.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a0.setText(label2);
            binding.rounded2a0.setMax(maxValue2);
            binding.rounded2a0.setProgress(progressValue2);
            binding.textViewPercentage2a0.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a0.setText(label3);
            binding.rounded3a0.setMax(maxValue3);
            binding.rounded3a0.setProgress(progressValue3);
            binding.textViewPercentage3a0.setText("" + percentage2 + "%");

            binding.layoutA04.setVisibility(View.GONE);
            binding.layoutA05.setVisibility(View.GONE);

        }
        if (size == 4) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a0.setText(label);
            binding.rounded1a0.setMax(maxValue);
            binding.rounded1a0.setProgress(progressValue);
            binding.textViewPercentage1a0.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a0.setText(label2);
            binding.rounded2a0.setMax(maxValue2);
            binding.rounded2a0.setProgress(progressValue2);
            binding.textViewPercentage2a0.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a0.setText(label3);
            binding.rounded3a0.setMax(maxValue3);
            binding.rounded3a0.setProgress(progressValue3);
            binding.textViewPercentage3a0.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a0.setText(label4);
            binding.rounded4a0.setMax(maxValue4);
            binding.rounded4a0.setProgress(progressValue4);
            binding.textViewPercentage4a0.setText("" + percentage3 + "%");

            binding.layoutA05.setVisibility(View.GONE);

        }
        if (size == 5) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a0.setText(label);
            binding.rounded1a0.setMax(maxValue);
            binding.rounded1a0.setProgress(progressValue);
            binding.textViewPercentage1a0.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a0.setText(label2);
            binding.rounded2a0.setMax(maxValue2);
            binding.rounded2a0.setProgress(progressValue2);
            binding.textViewPercentage2a0.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a0.setText(label3);
            binding.rounded3a0.setMax(maxValue3);
            binding.rounded3a0.setProgress(progressValue3);
            binding.textViewPercentage3a0.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue4 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a0.setText(label4);
            binding.rounded4a0.setMax(maxValue4);
            binding.rounded4a0.setProgress(progressValue4);
            binding.textViewPercentage4a0.setText("" + percentage3 + "%");

            //Item 4
            ModelQuestionCategory item5 = model.categories.get(4);
            String label5 = item5.label;
            int maxValue5 = model.set;
            int progressValue5 = item5.count;
            if(maxValue5 > 0){ percentage4 = (progressValue5 * 100) / maxValue5; }
            binding.textViewLabel5a0.setText(label5);
            binding.rounded5a0.setMax(maxValue5);
            binding.rounded5a0.setProgress(progressValue5);
            binding.textViewPercentage5a0.setText("" + percentage4 + "%");


        }

    }

    static void setUpAge15(ModelResultsByAge model, ItemPollAgeBinding binding) {

        int size = model.categories.size();
        int percentage0 = 0;
        int percentage1 = 0;
        int percentage2 = 0;
        int percentage3 = 0;
        int percentage4 = 0;

        if (size == 1) {

            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){
                percentage0 = (progressValue * 100) / maxValue;
            }

            binding.textViewLabel1a15.setText(label);
            binding.rounded1a15.setMax(maxValue);
            binding.rounded1a15.setProgress(progressValue);
            binding.textViewPercentage1a15.setText("" + percentage0 + "%");


            binding.layoutA153.setVisibility(View.GONE);
            binding.layoutA154.setVisibility(View.GONE);
            binding.layoutA155.setVisibility(View.GONE);

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a15.setText(label);
            binding.rounded1a15.setMax(maxValue);
            binding.rounded1a15.setProgress(progressValue);
            binding.textViewPercentage1a15.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a15.setText(label2);
            binding.rounded2a15.setMax(maxValue2);
            binding.rounded2a15.setProgress(progressValue2);
            binding.textViewPercentage2a15.setText("" + percentage1 + "%");

            binding.layoutA153.setVisibility(View.GONE);
            binding.layoutA154.setVisibility(View.GONE);
            binding.layoutA155.setVisibility(View.GONE);

        }
        if (size == 3) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a15.setText(label);
            binding.rounded1a15.setMax(maxValue);
            binding.rounded1a15.setProgress(progressValue);
            binding.textViewPercentage1a15.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a15.setText(label2);
            binding.rounded2a15.setMax(maxValue2);
            binding.rounded2a15.setProgress(progressValue2);
            binding.textViewPercentage2a15.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a15.setText(label3);
            binding.rounded3a15.setMax(maxValue3);
            binding.rounded3a15.setProgress(progressValue3);
            binding.textViewPercentage3a15.setText("" + percentage2 + "%");

            binding.layoutA154.setVisibility(View.GONE);
            binding.layoutA155.setVisibility(View.GONE);

        }
        if (size == 4) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a15.setText(label);
            binding.rounded1a15.setMax(maxValue);
            binding.rounded1a15.setProgress(progressValue);
            binding.textViewPercentage1a15.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a15.setText(label2);
            binding.rounded2a15.setMax(maxValue2);
            binding.rounded2a15.setProgress(progressValue2);
            binding.textViewPercentage2a15.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a15.setText(label3);
            binding.rounded3a15.setMax(maxValue3);
            binding.rounded3a15.setProgress(progressValue3);
            binding.textViewPercentage3a15.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a15.setText(label4);
            binding.rounded4a15.setMax(maxValue4);
            binding.rounded4a15.setProgress(progressValue4);
            binding.textViewPercentage4a15.setText("" + percentage3 + "%");

            binding.layoutA155.setVisibility(View.GONE);

        }
        if (size == 5) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a15.setText(label);
            binding.rounded1a15.setMax(maxValue);
            binding.rounded1a15.setProgress(progressValue);
            binding.textViewPercentage1a15.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a15.setText(label2);
            binding.rounded2a15.setMax(maxValue2);
            binding.rounded2a15.setProgress(progressValue2);
            binding.textViewPercentage2a15.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a15.setText(label3);
            binding.rounded3a15.setMax(maxValue3);
            binding.rounded3a15.setProgress(progressValue3);
            binding.textViewPercentage3a15.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a15.setText(label4);
            binding.rounded4a15.setMax(maxValue4);
            binding.rounded4a15.setProgress(progressValue4);
            binding.textViewPercentage4a15.setText("" + percentage3 + "%");

            //Item 4
            ModelQuestionCategory item5 = model.categories.get(4);
            String label5 = item5.label;
            int maxValue5 = model.set;
            int progressValue5 = item5.count;
            if(maxValue5 > 0){ percentage4 = (progressValue5 * 100) / maxValue5; }
            binding.textViewLabel5a15.setText(label5);
            binding.rounded5a15.setMax(maxValue5);
            binding.rounded5a15.setProgress(progressValue5);
            binding.textViewPercentage5a15.setText("" + percentage4 + "%");


        }

    }

    static void setUpAge20(ModelResultsByAge model, ItemPollAgeBinding binding) {

        int size = model.categories.size();
        int percentage0 = 0;
        int percentage1 = 0;
        int percentage2 = 0;
        int percentage3 = 0;
        int percentage4 = 0;

        if (size == 1) {

            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){
                percentage0 = (progressValue * 100) / maxValue;
            }

            binding.textViewLabel1a20.setText(label);
            binding.rounded1a20.setMax(maxValue);
            binding.rounded1a20.setProgress(progressValue);
            binding.textViewPercentage1a20.setText("" + percentage0 + "%");


            binding.layoutA203.setVisibility(View.GONE);
            binding.layoutA204.setVisibility(View.GONE);
            binding.layoutA205.setVisibility(View.GONE);

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a20.setText(label);
            binding.rounded1a20.setMax(maxValue);
            binding.rounded1a20.setProgress(progressValue);
            binding.textViewPercentage1a20.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a20.setText(label2);
            binding.rounded2a20.setMax(maxValue2);
            binding.rounded2a20.setProgress(progressValue2);
            binding.textViewPercentage2a20.setText("" + percentage1 + "%");

            binding.layoutA203.setVisibility(View.GONE);
            binding.layoutA204.setVisibility(View.GONE);
            binding.layoutA205.setVisibility(View.GONE);

        }
        if (size == 3) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a20.setText(label);
            binding.rounded1a20.setMax(maxValue);
            binding.rounded1a20.setProgress(progressValue);
            binding.textViewPercentage1a20.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a20.setText(label2);
            binding.rounded2a20.setMax(maxValue2);
            binding.rounded2a20.setProgress(progressValue2);
            binding.textViewPercentage2a20.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a20.setText(label3);
            binding.rounded3a20.setMax(maxValue3);
            binding.rounded3a20.setProgress(progressValue3);
            binding.textViewPercentage3a20.setText("" + percentage2 + "%");

            binding.layoutA204.setVisibility(View.GONE);
            binding.layoutA205.setVisibility(View.GONE);

        }
        if (size == 4) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a20.setText(label);
            binding.rounded1a20.setMax(maxValue);
            binding.rounded1a20.setProgress(progressValue);
            binding.textViewPercentage1a20.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a20.setText(label2);
            binding.rounded2a20.setMax(maxValue2);
            binding.rounded2a20.setProgress(progressValue2);
            binding.textViewPercentage2a20.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a20.setText(label3);
            binding.rounded3a20.setMax(maxValue3);
            binding.rounded3a20.setProgress(progressValue3);
            binding.textViewPercentage3a20.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a20.setText(label4);
            binding.rounded4a20.setMax(maxValue4);
            binding.rounded4a20.setProgress(progressValue4);
            binding.textViewPercentage4a20.setText("" + percentage3 + "%");

            binding.layoutA205.setVisibility(View.GONE);

        }
        if (size == 5) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a20.setText(label);
            binding.rounded1a20.setMax(maxValue);
            binding.rounded1a20.setProgress(progressValue);
            binding.textViewPercentage1a20.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a20.setText(label2);
            binding.rounded2a20.setMax(maxValue2);
            binding.rounded2a20.setProgress(progressValue2);
            binding.textViewPercentage2a20.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a20.setText(label3);
            binding.rounded3a20.setMax(maxValue3);
            binding.rounded3a20.setProgress(progressValue3);
            binding.textViewPercentage3a20.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a20.setText(label4);
            binding.rounded4a20.setMax(maxValue4);
            binding.rounded4a20.setProgress(progressValue4);
            binding.textViewPercentage4a20.setText("" + percentage3 + "%");

            //Item 4
            ModelQuestionCategory item5 = model.categories.get(4);
            String label5 = item5.label;
            int maxValue5 = model.set;
            int progressValue5 = item5.count;
            if(maxValue5 > 0){ percentage4 = (progressValue5 * 100) / maxValue5; }
            binding.textViewLabel5a20.setText(label5);
            binding.rounded5a20.setMax(maxValue5);
            binding.rounded5a20.setProgress(progressValue5);
            binding.textViewPercentage5a20.setText("" + percentage4 + "%");


        }

    }

    static void setUpAge25(ModelResultsByAge model, ItemPollAgeBinding binding) {

        int size = model.categories.size();
        int percentage0 = 0;
        int percentage1 = 0;
        int percentage2 = 0;
        int percentage3 = 0;
        int percentage4 = 0;

        if (size == 1) {

            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){
                percentage0 = (progressValue * 100) / maxValue;
            }

            binding.textViewLabel1a25.setText(label);
            binding.rounded1a25.setMax(maxValue);
            binding.rounded1a25.setProgress(progressValue);
            binding.textViewPercentage1a25.setText("" + percentage0 + "%");


            binding.layoutA253.setVisibility(View.GONE);
            binding.layoutA254.setVisibility(View.GONE);
            binding.layoutA255.setVisibility(View.GONE);

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a25.setText(label);
            binding.rounded1a25.setMax(maxValue);
            binding.rounded1a25.setProgress(progressValue);
            binding.textViewPercentage1a25.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a25.setText(label2);
            binding.rounded2a25.setMax(maxValue2);
            binding.rounded2a25.setProgress(progressValue2);
            binding.textViewPercentage2a25.setText("" + percentage1 + "%");

            binding.layoutA253.setVisibility(View.GONE);
            binding.layoutA254.setVisibility(View.GONE);
            binding.layoutA255.setVisibility(View.GONE);

        }
        if (size == 3) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a25.setText(label);
            binding.rounded1a25.setMax(maxValue);
            binding.rounded1a25.setProgress(progressValue);
            binding.textViewPercentage1a25.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a25.setText(label2);
            binding.rounded2a25.setMax(maxValue2);
            binding.rounded2a25.setProgress(progressValue2);
            binding.textViewPercentage2a25.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a25.setText(label3);
            binding.rounded3a25.setMax(maxValue3);
            binding.rounded3a25.setProgress(progressValue3);
            binding.textViewPercentage3a25.setText("" + percentage2 + "%");

            binding.layoutA254.setVisibility(View.GONE);
            binding.layoutA255.setVisibility(View.GONE);

        }
        if (size == 4) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a25.setText(label);
            binding.rounded1a25.setMax(maxValue);
            binding.rounded1a25.setProgress(progressValue);
            binding.textViewPercentage1a25.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a25.setText(label2);
            binding.rounded2a25.setMax(maxValue2);
            binding.rounded2a25.setProgress(progressValue2);
            binding.textViewPercentage2a25.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a25.setText(label3);
            binding.rounded3a25.setMax(maxValue3);
            binding.rounded3a25.setProgress(progressValue3);
            binding.textViewPercentage3a25.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a25.setText(label4);
            binding.rounded4a25.setMax(maxValue4);
            binding.rounded4a25.setProgress(progressValue4);
            binding.textViewPercentage4a25.setText("" + percentage3 + "%");

            binding.layoutA255.setVisibility(View.GONE);

        }
        if (size == 5) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a25.setText(label);
            binding.rounded1a25.setMax(maxValue);
            binding.rounded1a25.setProgress(progressValue);
            binding.textViewPercentage1a25.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a25.setText(label2);
            binding.rounded2a25.setMax(maxValue2);
            binding.rounded2a25.setProgress(progressValue2);
            binding.textViewPercentage2a25.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a25.setText(label3);
            binding.rounded3a25.setMax(maxValue3);
            binding.rounded3a25.setProgress(progressValue3);
            binding.textViewPercentage3a25.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a25.setText(label4);
            binding.rounded4a25.setMax(maxValue4);
            binding.rounded4a25.setProgress(progressValue4);
            binding.textViewPercentage4a25.setText("" + percentage3 + "%");

            //Item 4
            ModelQuestionCategory item5 = model.categories.get(4);
            String label5 = item5.label;
            int maxValue5 = model.set;
            int progressValue5 = item5.count;
            if(maxValue5 > 0){ percentage4 = (progressValue5 * 100) / maxValue5; }
            binding.textViewLabel5a25.setText(label5);
            binding.rounded5a25.setMax(maxValue5);
            binding.rounded5a25.setProgress(progressValue5);
            binding.textViewPercentage5a25.setText("" + percentage4 + "%");


        }

    }

    static void setUpAge31(ModelResultsByAge model, ItemPollAgeBinding binding) {

        int size = model.categories.size();
        int percentage0 = 0;
        int percentage1 = 0;
        int percentage2 = 0;
        int percentage3 = 0;
        int percentage4 = 0;

        if (size == 1) {

            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){
                percentage0 = (progressValue * 100) / maxValue;
            }

            binding.textViewLabel1a31.setText(label);
            binding.rounded1a31.setMax(maxValue);
            binding.rounded1a31.setProgress(progressValue);
            binding.textViewPercentage1a31.setText("" + percentage0 + "%");


            binding.layoutA313.setVisibility(View.GONE);
            binding.layoutA314.setVisibility(View.GONE);
            binding.layoutA315.setVisibility(View.GONE);

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a31.setText(label);
            binding.rounded1a31.setMax(maxValue);
            binding.rounded1a31.setProgress(progressValue);
            binding.textViewPercentage1a31.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a31.setText(label2);
            binding.rounded2a31.setMax(maxValue2);
            binding.rounded2a31.setProgress(progressValue2);
            binding.textViewPercentage2a31.setText("" + percentage1 + "%");

            binding.layoutA313.setVisibility(View.GONE);
            binding.layoutA314.setVisibility(View.GONE);
            binding.layoutA315.setVisibility(View.GONE);

        }
        if (size == 3) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a31.setText(label);
            binding.rounded1a31.setMax(maxValue);
            binding.rounded1a31.setProgress(progressValue);
            binding.textViewPercentage1a31.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a31.setText(label2);
            binding.rounded2a31.setMax(maxValue2);
            binding.rounded2a31.setProgress(progressValue2);
            binding.textViewPercentage2a31.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a31.setText(label3);
            binding.rounded3a31.setMax(maxValue3);
            binding.rounded3a31.setProgress(progressValue3);
            binding.textViewPercentage3a31.setText("" + percentage2 + "%");

            binding.layoutA314.setVisibility(View.GONE);
            binding.layoutA315.setVisibility(View.GONE);

        }
        if (size == 4) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a31.setText(label);
            binding.rounded1a31.setMax(maxValue);
            binding.rounded1a31.setProgress(progressValue);
            binding.textViewPercentage1a31.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a31.setText(label2);
            binding.rounded2a31.setMax(maxValue2);
            binding.rounded2a31.setProgress(progressValue2);
            binding.textViewPercentage2a31.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a31.setText(label3);
            binding.rounded3a31.setMax(maxValue3);
            binding.rounded3a31.setProgress(progressValue3);
            binding.textViewPercentage3a31.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a31.setText(label4);
            binding.rounded4a31.setMax(maxValue4);
            binding.rounded4a31.setProgress(progressValue4);
            binding.textViewPercentage4a31.setText("" + percentage3 + "%");

            binding.layoutA315.setVisibility(View.GONE);

        }
        if (size == 5) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a31.setText(label);
            binding.rounded1a31.setMax(maxValue);
            binding.rounded1a31.setProgress(progressValue);
            binding.textViewPercentage1a31.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a31.setText(label2);
            binding.rounded2a31.setMax(maxValue2);
            binding.rounded2a31.setProgress(progressValue2);
            binding.textViewPercentage2a31.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a31.setText(label3);
            binding.rounded3a31.setMax(maxValue3);
            binding.rounded3a31.setProgress(progressValue3);
            binding.textViewPercentage3a31.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a31.setText(label4);
            binding.rounded4a31.setMax(maxValue4);
            binding.rounded4a31.setProgress(progressValue4);
            binding.textViewPercentage4a31.setText("" + percentage3 + "%");

            //Item 4
            ModelQuestionCategory item5 = model.categories.get(4);
            String label5 = item5.label;
            int maxValue5 = model.set;
            int progressValue5 = item5.count;
            if(maxValue5 > 0){ percentage4 = (progressValue5 * 100) / maxValue5; }
            binding.textViewLabel5a31.setText(label5);
            binding.rounded5a31.setMax(maxValue5);
            binding.rounded5a31.setProgress(progressValue5);
            binding.textViewPercentage5a31.setText("" + percentage4 + "%");


        }

    }

    static void setUpAge35(ModelResultsByAge model, ItemPollAgeBinding binding) {

        int size = model.categories.size();
        int percentage0 = 0;
        int percentage1 = 0;
        int percentage2 = 0;
        int percentage3 = 0;
        int percentage4 = 0;

        if (size == 1) {

            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){
                percentage0 = (progressValue * 100) / maxValue;
            }

            binding.textViewLabel1a35.setText(label);
            binding.rounded1a35.setMax(maxValue);
            binding.rounded1a35.setProgress(progressValue);
            binding.textViewPercentage1a35.setText("" + percentage0 + "%");


            binding.layoutA353.setVisibility(View.GONE);
            binding.layoutA354.setVisibility(View.GONE);
            binding.layoutA355.setVisibility(View.GONE);

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a35.setText(label);
            binding.rounded1a35.setMax(maxValue);
            binding.rounded1a35.setProgress(progressValue);
            binding.textViewPercentage1a35.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a35.setText(label2);
            binding.rounded2a35.setMax(maxValue2);
            binding.rounded2a35.setProgress(progressValue2);
            binding.textViewPercentage2a35.setText("" + percentage1 + "%");

            binding.layoutA353.setVisibility(View.GONE);
            binding.layoutA354.setVisibility(View.GONE);
            binding.layoutA355.setVisibility(View.GONE);

        }
        if (size == 3) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a35.setText(label);
            binding.rounded1a35.setMax(maxValue);
            binding.rounded1a35.setProgress(progressValue);
            binding.textViewPercentage1a35.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a35.setText(label2);
            binding.rounded2a35.setMax(maxValue2);
            binding.rounded2a35.setProgress(progressValue2);
            binding.textViewPercentage2a35.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a35.setText(label3);
            binding.rounded3a35.setMax(maxValue3);
            binding.rounded3a35.setProgress(progressValue3);
            binding.textViewPercentage3a35.setText("" + percentage2 + "%");

            binding.layoutA354.setVisibility(View.GONE);
            binding.layoutA355.setVisibility(View.GONE);

        }
        if (size == 4) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a35.setText(label);
            binding.rounded1a35.setMax(maxValue);
            binding.rounded1a35.setProgress(progressValue);
            binding.textViewPercentage1a35.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a35.setText(label2);
            binding.rounded2a35.setMax(maxValue2);
            binding.rounded2a35.setProgress(progressValue2);
            binding.textViewPercentage2a35.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a35.setText(label3);
            binding.rounded3a35.setMax(maxValue3);
            binding.rounded3a35.setProgress(progressValue3);
            binding.textViewPercentage3a35.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a35.setText(label4);
            binding.rounded4a35.setMax(maxValue4);
            binding.rounded4a35.setProgress(progressValue4);
            binding.textViewPercentage4a35.setText("" + percentage3 + "%");

            binding.layoutA355.setVisibility(View.GONE);

        }
        if (size == 5) {

            //Item 0
            ModelQuestionCategory item = model.categories.get(0);
            String label = item.label;
            int maxValue = model.set;
            int progressValue = item.count;
            if(maxValue > 0){ percentage0 = (progressValue * 100) / maxValue; }
            binding.textViewLabel1a35.setText(label);
            binding.rounded1a35.setMax(maxValue);
            binding.rounded1a35.setProgress(progressValue);
            binding.textViewPercentage1a35.setText("" + percentage0 + "%");

            //Item 1
            ModelQuestionCategory item2 = model.categories.get(1);
            String label2 = item2.label;
            int maxValue2 = model.set;
            int progressValue2 = item2.count;
            if(maxValue2 > 0){ percentage1 = (progressValue2 * 100) / maxValue2; }
            binding.textViewLabel2a35.setText(label2);
            binding.rounded2a35.setMax(maxValue2);
            binding.rounded2a35.setProgress(progressValue2);
            binding.textViewPercentage2a35.setText("" + percentage1 + "%");

            //Item 2
            ModelQuestionCategory item3 = model.categories.get(2);
            String label3 = item3.label;
            int maxValue3 = model.set;
            int progressValue3 = item3.count;
            if(maxValue3 > 0){ percentage2 = (progressValue3 * 100) / maxValue3; }
            binding.textViewLabel3a35.setText(label3);
            binding.rounded3a35.setMax(maxValue3);
            binding.rounded3a35.setProgress(progressValue3);
            binding.textViewPercentage3a35.setText("" + percentage2 + "%");

            //Item 3
            ModelQuestionCategory item4 = model.categories.get(3);
            String label4 = item4.label;
            int maxValue4 = model.set;
            int progressValue4 = item4.count;
            if(maxValue3 > 0){ percentage3 = (progressValue4 * 100) / maxValue4; }
            binding.textViewLabel4a35.setText(label4);
            binding.rounded4a35.setMax(maxValue4);
            binding.rounded4a35.setProgress(progressValue4);
            binding.textViewPercentage4a35.setText("" + percentage3 + "%");

            //Item 4
            ModelQuestionCategory item5 = model.categories.get(4);
            String label5 = item5.label;
            int maxValue5 = model.set;
            int progressValue5 = item5.count;
            if(maxValue5 > 0){ percentage4 = (progressValue5 * 100) / maxValue5; }
            binding.textViewLabel5a35.setText(label5);
            binding.rounded5a35.setMax(maxValue5);
            binding.rounded5a35.setProgress(progressValue5);
            binding.textViewPercentage5a35.setText("" + percentage4 + "%");


        }

    }


    static void setProgressColor(ItemPollAgeBinding binding , int color , Context context){
        binding.rounded1a0.setProgressColor(context.getResources().getColor(color));
        binding.rounded2a0.setProgressColor(context.getResources().getColor(color));
        binding.rounded3a0.setProgressColor(context.getResources().getColor(color));
        binding.rounded4a0.setProgressColor(context.getResources().getColor(color));
        binding.rounded5a0.setProgressColor(context.getResources().getColor(color));

        binding.rounded1a15.setProgressColor(context.getResources().getColor(color));
        binding.rounded2a15.setProgressColor(context.getResources().getColor(color));
        binding.rounded3a15.setProgressColor(context.getResources().getColor(color));
        binding.rounded4a15.setProgressColor(context.getResources().getColor(color));
        binding.rounded5a15.setProgressColor(context.getResources().getColor(color));

        binding.rounded1a20.setProgressColor(context.getResources().getColor(color));
        binding.rounded2a20.setProgressColor(context.getResources().getColor(color));
        binding.rounded3a20.setProgressColor(context.getResources().getColor(color));
        binding.rounded4a20.setProgressColor(context.getResources().getColor(color));
        binding.rounded5a20.setProgressColor(context.getResources().getColor(color));

        binding.rounded1a25.setProgressColor(context.getResources().getColor(color));
        binding.rounded2a25.setProgressColor(context.getResources().getColor(color));
        binding.rounded3a25.setProgressColor(context.getResources().getColor(color));
        binding.rounded4a25.setProgressColor(context.getResources().getColor(color));
        binding.rounded5a25.setProgressColor(context.getResources().getColor(color));

        binding.rounded1a31.setProgressColor(context.getResources().getColor(color));
        binding.rounded2a31.setProgressColor(context.getResources().getColor(color));
        binding.rounded3a31.setProgressColor(context.getResources().getColor(color));
        binding.rounded4a31.setProgressColor(context.getResources().getColor(color));
        binding.rounded5a31.setProgressColor(context.getResources().getColor(color));

        binding.rounded1a35.setProgressColor(context.getResources().getColor(color));
        binding.rounded2a35.setProgressColor(context.getResources().getColor(color));
        binding.rounded3a35.setProgressColor(context.getResources().getColor(color));
        binding.rounded4a35.setProgressColor(context.getResources().getColor(color));
        binding.rounded5a35.setProgressColor(context.getResources().getColor(color));


    }

}
