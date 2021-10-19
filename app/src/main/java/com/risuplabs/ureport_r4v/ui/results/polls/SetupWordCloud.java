package com.risuplabs.ureport_r4v.ui.results.polls;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.mordred.wordcloud.WordCloud;
import com.risuplabs.ureport_r4v.databinding.ItemPollPieChartBinding;
import com.risuplabs.ureport_r4v.databinding.ItemPollWordCloudBinding;
import com.risuplabs.ureport_r4v.databinding.ItemPollsBinding;
import com.risuplabs.ureport_r4v.model.results.ModelQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetupWordCloud {

    public static void setUpWordCloud(ModelQuestion item, ItemPollWordCloudBinding binding, ItemPollsBinding parent) {

        int count = 4;

        parent.layoutPieChart.chartParent.setVisibility(View.VISIBLE);
        parent.layoutStatistics.stateParent.setVisibility(View.GONE);
        parent.textViewStatistics.setVisibility(View.GONE);
        parent.textViewlocations.setVisibility(View.GONE);
        parent.textViewGender.setVisibility(View.GONE);
        parent.textViewAge.setVisibility(View.GONE);
        parent.textViewPieChart.setVisibility(View.GONE);

        Map<String, Integer> nMap = new HashMap<>();

        for(int i = 0 ; i < item.results.categories.size() ; i++){
            if(count < 0) count = 1;
            nMap.put(item.results.categories.get(i).label, count--);
        }

//        nMap.put("oguzhan", 2);
//        nMap.put("mordred", 2);
//        nMap.put("is", 4);
//        nMap.put("on",2);
//        nMap.put("the", 3);
//        nMap.put("salda lake",5);

        WordCloud wd = new WordCloud(nMap,Color.BLUE,Color.WHITE);
        wd.setPaddingX(5);
        wd.setPaddingY(5);

        Bitmap generatedWordCloudBmp = wd.generate();

        binding.imageView.setImageBitmap(generatedWordCloudBmp);

    }
}
