package com.risuplabs.ureport.ui.results.polls;

import android.view.View;

import com.risuplabs.ureport.databinding.ItemPollGenderBinding;
import com.risuplabs.ureport.model.results.ModelQuestion;
import com.risuplabs.ureport.model.results.ModelQuestionCategory;
import com.risuplabs.ureport.model.results.ModelResultsByGender;

public class SetupGenderResult {

    public static void setupGenderResult(ModelQuestion item, ItemPollGenderBinding binding) {

        setUpMale(item.results_by_gender.get(0), binding);
        setUpFemale(item.results_by_gender.get(1), binding);

    }

    static void setUpMale(ModelResultsByGender item, ItemPollGenderBinding binding) {

        int size = item.categories.size();

        if (size == 1) {

            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1m.setText(label);
            binding.rounded1m.setMax(maxValue);
            binding.rounded1m.setProgress(progressValue);
            if (maxValue == 0) {
                binding.textViewPercentage1m.setText(((progressValue * 100) / maxValue) + " %");
            }

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1m.setText(label);
            binding.rounded1m.setMax(maxValue);
            binding.rounded1m.setProgress(progressValue);
            if (maxValue == 0) {
                binding.textViewPercentage1m.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2m.setText(label2);
            binding.rounded2m.setMax(maxValue2);
            binding.rounded2m.setProgress(progressValue2);
            if (maxValue2 == 0) {
                binding.textViewPercentage2m.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            binding.layoutM3.setVisibility(View.GONE);
            binding.layoutM4.setVisibility(View.GONE);
            binding.layoutM5.setVisibility(View.GONE);

        }
        if (size == 3) {
            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1m.setText(label);
            binding.rounded1m.setMax(maxValue);
            binding.rounded1m.setProgress(progressValue);
            if (maxValue == 0) {
                binding.textViewPercentage1m.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2m.setText(label2);
            binding.rounded2m.setMax(maxValue2);
            binding.rounded2m.setProgress(progressValue2);
            if (maxValue2 == 0) {
                binding.textViewPercentage2m.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            //Item 3
            ModelQuestionCategory model3 = item.categories.get(2);
            String label3 = model3.label;
            int maxValue3 = item.set;
            int progressValue3 = model3.count;

            binding.textViewLabel3m.setText(label3);
            binding.rounded3m.setMax(maxValue3);
            binding.rounded3m.setProgress(progressValue3);
            if (maxValue3 != 0) {
                binding.textViewPercentage3m.setText(((progressValue3 * 100) / maxValue3) + " %");
            }

            binding.layoutM4.setVisibility(View.GONE);
            binding.layoutM5.setVisibility(View.GONE);

        }
        if (size == 4) {
            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1m.setText(label);
            binding.rounded1m.setMax(maxValue);
            binding.rounded1m.setProgress(progressValue);
            if (maxValue == 0) {
                binding.textViewPercentage1m.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2m.setText(label2);
            binding.rounded2m.setMax(maxValue2);
            binding.rounded2m.setProgress(progressValue2);
            if (maxValue2 == 0) {
                binding.textViewPercentage2m.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            //Item 3
            ModelQuestionCategory model3 = item.categories.get(2);
            String label3 = model3.label;
            int maxValue3 = item.set;
            int progressValue3 = model3.count;

            binding.textViewLabel3m.setText(label3);
            binding.rounded3m.setMax(maxValue3);
            binding.rounded3m.setProgress(progressValue3);
            if (maxValue3 != 0) {
                binding.textViewPercentage3m.setText(((progressValue3 * 100) / maxValue3) + " %");
            }

            //Item 4
            ModelQuestionCategory model4 = item.categories.get(3);
            String label4 = model4.label;
            int maxValue4 = item.set;
            int progressValue4 = model4.count;

            binding.textViewLabel4m.setText(label4);
            binding.rounded4m.setMax(maxValue4);
            binding.rounded4m.setProgress(progressValue4);
            if (maxValue4 != 0) {
                binding.textViewPercentage4m.setText(((progressValue4 * 100) / maxValue4) + " %");
            }
            binding.layoutM5.setVisibility(View.GONE);

        }
        if (size >= 5) {
            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1m.setText(label);
            binding.rounded1m.setMax(maxValue);
            binding.rounded1m.setProgress(progressValue);
            if (maxValue == 0) {
                binding.textViewPercentage1m.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2m.setText(label2);
            binding.rounded2m.setMax(maxValue2);
            binding.rounded2m.setProgress(progressValue2);
            if (maxValue2 == 0) {
                binding.textViewPercentage2m.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            //Item 3
            ModelQuestionCategory model3 = item.categories.get(2);
            String label3 = model3.label;
            int maxValue3 = item.set;
            int progressValue3 = model3.count;

            binding.textViewLabel3m.setText(label3);
            binding.rounded3m.setMax(maxValue3);
            binding.rounded3m.setProgress(progressValue3);
            if (maxValue3 != 0) {
                binding.textViewPercentage3m.setText(((progressValue3 * 100) / maxValue3) + " %");
            }

            //Item 4
            ModelQuestionCategory model4 = item.categories.get(3);
            String label4 = model4.label;
            int maxValue4 = item.set;
            int progressValue4 = model4.count;

            binding.textViewLabel4m.setText(label4);
            binding.rounded4m.setMax(maxValue4);
            binding.rounded4m.setProgress(progressValue4);
            if (maxValue4 != 0) {
                binding.textViewPercentage4m.setText(((progressValue4 * 100) / maxValue4) + " %");
            }
            //Item 5
            ModelQuestionCategory model5 = item.categories.get(4);
            String label5 = model5.label;
            int maxValue5 = item.set;
            int progressValue5 = model5.count;

            binding.textViewLabel5m.setText(label5);
            binding.rounded5m.setMax(maxValue5);
            binding.rounded5m.setProgress(progressValue5);
            if (maxValue5 != 0) {
                binding.textViewPercentage5m.setText(((progressValue5 * 100) / maxValue5) + " %");
            }
        }
    }

    static void setUpFemale(ModelResultsByGender item, ItemPollGenderBinding binding) {

        int size = item.categories.size();

        if (size == 1) {

            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1f.setText(label);
            binding.rounded1f.setMax(maxValue);
            binding.rounded1f.setProgress(progressValue);
            if(maxValue != 0) {
                binding.textViewPercentagef.setText(((progressValue * 100) / maxValue) + " %");
            }

        }
        if (size == 2) {

            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1f.setText(label);
            binding.rounded1f.setMax(maxValue);
            binding.rounded1f.setProgress(progressValue);
            if(maxValue != 0) {
                binding.textViewPercentagef.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2f.setText(label2);
            binding.rounded2f.setMax(maxValue2);
            binding.rounded2f.setProgress(progressValue2);
            if(maxValue2 != 0) {
                binding.textViewPercentage2f.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            binding.layoutF3.setVisibility(View.GONE);
            binding.layoutF4.setVisibility(View.GONE);
            binding.layoutF5.setVisibility(View.GONE);

        }
        if (size == 3) {
            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1f.setText(label);
            binding.rounded1f.setMax(maxValue);
            binding.rounded1f.setProgress(progressValue);
            if(maxValue != 0) {
                binding.textViewPercentagef.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2f.setText(label2);
            binding.rounded2f.setMax(maxValue2);
            binding.rounded2f.setProgress(progressValue2);
            if(maxValue2 != 0) {
                binding.textViewPercentage2f.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            //Item 3
            ModelQuestionCategory model3 = item.categories.get(2);
            String label3 = model3.label;
            int maxValue3 = item.set;
            int progressValue3 = model3.count;

            binding.textViewLabel3f.setText(label3);
            binding.rounded3f.setMax(maxValue3);
            binding.rounded3f.setProgress(progressValue3);
             if(maxValue3 != 0) {
                binding.textViewPercentage3f.setText(((progressValue3 * 100) / maxValue3) + " %");
            }

            binding.layoutF4.setVisibility(View.GONE);
            binding.layoutF5.setVisibility(View.GONE);

        }
        if (size == 4) {
            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1f.setText(label);
            binding.rounded1f.setMax(maxValue);
            binding.rounded1f.setProgress(progressValue);
            if(maxValue != 0) {
                binding.textViewPercentagef.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2f.setText(label2);
            binding.rounded2f.setMax(maxValue2);
            binding.rounded2f.setProgress(progressValue2);
            if(maxValue2 != 0) {
                binding.textViewPercentage2f.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            //Item 3
            ModelQuestionCategory model3 = item.categories.get(2);
            String label3 = model3.label;
            int maxValue3 = item.set;
            int progressValue3 = model3.count;

            binding.textViewLabel3f.setText(label3);
            binding.rounded3f.setMax(maxValue3);
            binding.rounded3f.setProgress(progressValue3);
             if(maxValue3 != 0) {
                binding.textViewPercentage3f.setText(((progressValue3 * 100) / maxValue3) + " %");
            }

            //Item 4
            ModelQuestionCategory model4 = item.categories.get(3);
            String label4 = model4.label;
            int maxValue4 = item.set;
            int progressValue4 = model4.count;

            binding.textViewLabel4f.setText(label4);
            binding.rounded4f.setMax(maxValue4);
            binding.rounded4f.setProgress(progressValue4);
            if(maxValue4 != 0) {
                binding.textViewPercentage4f.setText(((progressValue4 * 100) / maxValue4) + " %");
            }
            binding.layoutF5.setVisibility(View.GONE);

        }
        if (size >= 5) {
            //Item 0
            ModelQuestionCategory model = item.categories.get(0);
            String label = model.label;
            int maxValue = item.set;
            int progressValue = model.count;

            binding.textViewLabel1f.setText(label);
            binding.rounded1f.setMax(maxValue);
            binding.rounded1f.setProgress(progressValue);
            if(maxValue != 0) {
                binding.textViewPercentagef.setText(((progressValue * 100) / maxValue) + " %");
            }

            //Item 2
            ModelQuestionCategory model2 = item.categories.get(1);
            String label2 = model2.label;
            int maxValue2 = item.set;
            int progressValue2 = model2.count;

            binding.textViewLabel2f.setText(label2);
            binding.rounded2f.setMax(maxValue2);
            binding.rounded2f.setProgress(progressValue2);
            if(maxValue2 != 0) {
                binding.textViewPercentage2f.setText(((progressValue2 * 100) / maxValue2) + " %");
            }

            //Item 3
            ModelQuestionCategory model3 = item.categories.get(2);
            String label3 = model3.label;
            int maxValue3 = item.set;
            int progressValue3 = model3.count;

            binding.textViewLabel3f.setText(label3);
            binding.rounded3f.setMax(maxValue3);
            binding.rounded3f.setProgress(progressValue3);
             if(maxValue3 != 0) {
                binding.textViewPercentage3f.setText(((progressValue3 * 100) / maxValue3) + " %");
            }

            //Item 4
            ModelQuestionCategory model4 = item.categories.get(3);
            String label4 = model4.label;
            int maxValue4 = item.set;
            int progressValue4 = model4.count;

            binding.textViewLabel4f.setText(label4);
            binding.rounded4f.setMax(maxValue4);
            binding.rounded4f.setProgress(progressValue4);
            if(maxValue4 != 0) {
                binding.textViewPercentage4f.setText(((progressValue4 * 100) / maxValue4) + " %");
            }
            //Item 5
            ModelQuestionCategory model5 = item.categories.get(4);
            String label5 = model5.label;
            int maxValue5 = item.set;
            int progressValue5 = model5.count;

            binding.textViewLabel5f.setText(label5);
            binding.rounded5f.setMax(maxValue5);
            binding.rounded5f.setProgress(progressValue5);
            if(maxValue5 != 0) {
                binding.textViewPercentage5f.setText(((progressValue5 * 100) / maxValue5) + " %");
            }
        }
    }

}
