package com.risuplabs.ureport_r4v.ui.results.polls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.mordred.wordcloud.WordCloud;
import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.databinding.ItemPollWordCloudBinding;
import com.risuplabs.ureport_r4v.databinding.ItemPollsBinding;
import com.risuplabs.ureport_r4v.di.SurveyorApplication;
import com.risuplabs.ureport_r4v.model.results.ModelQuestion;

import java.util.HashMap;
import java.util.Map;

public class SetupWordCloud {

    public static void setUpWordCloud(ModelQuestion item, ItemPollWordCloudBinding binding, ItemPollsBinding parent) {

        int count = 3;


        Map<String, Integer> nMap = new HashMap<>();

        for(int i = 0 ; i < item.results.categories.size() ; i++){
            if(count < 0) count = 1;
            nMap.put(item.results.categories.get(i).label, count--);
        }

        WordCloud wd = new WordCloud(nMap, 350, 380, SurveyorApplication.get().getResources().getColor(R.color.colorPrimary),Color.WHITE);
        wd.setPaddingX(5);
        wd.setPaddingY(5);
        wd.setWordColorOpacityAuto(true);

        Bitmap generatedWordCloudBmp = wd.generate();

        binding.imageView.setImageBitmap(generatedWordCloudBmp);

    }
}
