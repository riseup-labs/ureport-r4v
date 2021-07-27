package com.risuplabs.ureport.ui.results.polls;

import android.view.View;

import com.risuplabs.ureport.databinding.ItemPollStatisticsBinding;
import com.risuplabs.ureport.model.results.ModelQuestion;
import com.risuplabs.ureport.model.results.ModelQuestionCategory;

import java.util.List;

public class SetupPollStatistics {

    public static void setUpStatistics(ModelQuestion item, ItemPollStatisticsBinding binding) {
        List<ModelQuestionCategory> category = item.results.categories;
        int size = item.results.categories.size();

        if (size == 1) {
            //Item 0
            binding.textViewLabel1.setText(category.get(0).label);
            binding.rounded1.setMax(item.results.set);
            binding.rounded1.setProgress(category.get(0).count);
            int percentageTemp = 0;
            if (item.results.set > 0) {
                percentageTemp = (category.get(0).count * 100) / item.results.set;
            }
            binding.textViewPercentage.setText("" + percentageTemp + "%");
        }
        if (size == 2) {
            //Item 0
            binding.textViewLabel1.setText(category.get(0).label);
            binding.rounded1.setMax(item.results.set);
            binding.rounded1.setProgress(category.get(0).count);
            int percentageTemp = 0;
            if (item.results.set > 0) {
                percentageTemp = (category.get(0).count * 100) / item.results.set;
            }
            binding.textViewPercentage.setText("" + percentageTemp + "%");

            //Item 1
            binding.textViewLabel2.setText(category.get(1).label);
            binding.rounded2.setMax(item.results.set);
            binding.rounded2.setProgress(category.get(1).count);
            int percentageTemp2 = 0;
            if (item.results.set > 0) {
                percentageTemp2 = (category.get(1).count * 100) / item.results.set;
            }
            binding.textViewPercentage2.setText("" + percentageTemp2 + "%");
        }
        if (size == 3) {
            //Item 0
            binding.textViewLabel1.setText(category.get(0).label);
            binding.rounded1.setMax(item.results.set);
            binding.rounded1.setProgress(category.get(0).count);
            int percentageTemp = 0;
            if (item.results.set > 0) {
                percentageTemp = (category.get(0).count * 100) / item.results.set;
            }
            binding.textViewPercentage.setText("" + percentageTemp + "%");

            //Item 1
            binding.textViewLabel2.setText(category.get(1).label);
            binding.rounded2.setMax(item.results.set);
            binding.rounded2.setProgress(category.get(1).count);
            int percentageTemp2 = 0;
            if (item.results.set > 0) {
                percentageTemp2 = (category.get(1).count * 100) / item.results.set;
            }
            binding.textViewPercentage2.setText("" + percentageTemp2 + "%");

            //Item 2
            binding.layout3.setVisibility(View.VISIBLE);
            binding.textViewLabel3.setText(category.get(2).label);
            binding.rounded3.setMax(item.results.set);
            binding.rounded3.setProgress(category.get(2).count);
            int percentageTemp3 = 0;
            if (item.results.set > 0) {
                percentageTemp3 = (category.get(2).count * 100) / item.results.set;
            }
            binding.textViewPercentage3.setText("" + percentageTemp3 + "%");


        }
        if (size == 4) {
            //Item 0
            binding.textViewLabel1.setText(category.get(0).label);
            binding.rounded1.setMax(item.results.set);
            binding.rounded1.setProgress(category.get(0).count);
            int percentageTemp = 0;
            if (item.results.set > 0) {
                percentageTemp = (category.get(0).count * 100) / item.results.set;
            }
            binding.textViewPercentage.setText("" + percentageTemp + "%");

            //Item 1
            binding.textViewLabel2.setText(category.get(1).label);
            binding.rounded2.setMax(item.results.set);
            binding.rounded2.setProgress(category.get(1).count);
            int percentageTemp2 = 0;
            if (item.results.set > 0) {
                percentageTemp2 = (category.get(1).count * 100) / item.results.set;
            }
            binding.textViewPercentage2.setText("" + percentageTemp2 + "%");

            //Item 2
            binding.layout3.setVisibility(View.VISIBLE);
            binding.textViewLabel3.setText(category.get(2).label);
            binding.rounded3.setMax(item.results.set);
            binding.rounded3.setProgress(category.get(2).count);
            int percentageTemp3 = 0;
            if (item.results.set > 0) {
                percentageTemp3 = (category.get(2).count * 100) / item.results.set;
            }
            binding.textViewPercentage3.setText("" + percentageTemp3 + "%");

            //Item3
            binding.layout4.setVisibility(View.VISIBLE);
            binding.textViewLabel4.setText(category.get(3).label);
            binding.rounded4.setMax(item.results.set);
            binding.rounded4.setProgress(category.get(3).count);
            int percentageTemp4 = 0;
            if (item.results.set > 0) {
                percentageTemp4 = (category.get(3).count * 100) / item.results.set;
            }
            binding.textViewPercentage4.setText("" + percentageTemp4 + "%");

        }
        if (size == 5) {
            //Item 0
            binding.textViewLabel1.setText(category.get(0).label);
            binding.rounded1.setMax(item.results.set);
            binding.rounded1.setProgress(category.get(0).count);
            int percentageTemp = 0;
            if (item.results.set > 0) {
                percentageTemp = (category.get(0).count * 100) / item.results.set;
            }
            binding.textViewPercentage.setText("" + percentageTemp + "%");

            //Item 1
            binding.textViewLabel2.setText(category.get(1).label);
            binding.rounded2.setMax(item.results.set);
            binding.rounded2.setProgress(category.get(1).count);
            int percentageTemp2 = 0;
            if (item.results.set > 0) {
                percentageTemp2 = (category.get(1).count * 100) / item.results.set;
            }
            binding.textViewPercentage2.setText("" + percentageTemp2 + "%");

            //Item 2
            binding.layout3.setVisibility(View.VISIBLE);
            binding.textViewLabel3.setText(category.get(2).label);
            binding.rounded3.setMax(item.results.set);
            binding.rounded3.setProgress(category.get(2).count);
            int percentageTemp3 = 0;
            if (item.results.set > 0) {
                percentageTemp3 = (category.get(2).count * 100) / item.results.set;
            }
            binding.textViewPercentage3.setText("" + percentageTemp3 + "%");

            //Item 3
            binding.layout4.setVisibility(View.VISIBLE);
            binding.textViewLabel4.setText(category.get(3).label);
            binding.rounded4.setMax(item.results.set);
            binding.rounded4.setProgress(category.get(3).count);
            int percentageTemp4 = 0;
            if (item.results.set > 0) {
                percentageTemp4 = (category.get(3).count * 100) / item.results.set;
            }
            binding.textViewPercentage4.setText("" + percentageTemp4 + "%");

            //Item 4
            binding.layout5.setVisibility(View.VISIBLE);
            binding.textViewLabel5.setText(category.get(4).label);
            binding.rounded5.setMax(item.results.set);
            binding.rounded5.setProgress(category.get(4).count);
            int percentageTemp5 = 0;
            if (item.results.set > 0) {
                percentageTemp5 = (category.get(4).count * 100) / item.results.set;
            }
            binding.textViewPercentage5.setText("" + percentageTemp5 + "%");

        }

    }

}
