package com.risuplabs.ureport_r4v.ui.results.polls;

import android.content.Context;

import com.richpath.RichPath;
import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.databinding.ItemPollLocationBinding;
import com.risuplabs.ureport_r4v.model.results.ModelQuestion;
import com.risuplabs.ureport_r4v.model.results.ModelResultsByLocation;

import java.util.List;

public class SetupLocationResult {

    public static void setupLocation(ModelQuestion item, ItemPollLocationBinding binding, Context context) {


        final RichPath[] pathStack = new RichPath[2];
        List<ModelResultsByLocation> model = item.results_by_location;
        RichPath initialRichPath = binding.richPathView.findRichPathByName("Brasil");
        highlight(model, pathStack, initialRichPath, binding, context);

        binding.richPathView.setOnPathClickListener(richPath -> {

            if (richPath.getName() != null) {
                highlight(model, pathStack, richPath, binding, context);
            }
        });

    }

    public static void setLocationData(ItemPollLocationBinding binding, ModelResultsByLocation model) {
        int responses = model.set;
        binding.textViewRespondedMap.setText(String.valueOf(responses));
        if (responses > 0) {
            int total_response = model.set + model.unset;
            int response_rate = (responses * 100) / total_response;
            binding.textViewRespondedAllMap.setText(response_rate + "%");

            int category_size = model.categories.size();
            if (category_size >= 2) {
                binding.respondedLabelMap.setText(model.categories.get(0).label);
                int respo = model.categories.get(0).count * 100 / responses;
                binding.respondedPercentageMap.setText(respo + "%");
                binding.textViewRest.setText((100 - respo) + "%");
            }
        } else {
            binding.respondedPercentageMap.setText("0%");
            binding.textViewRest.setText("0%");
            binding.textViewRespondedAllMap.setText("0%");
        }
    }

    public static void highlight(
            List<ModelResultsByLocation> model,
            RichPath[] pathStack,
            RichPath richPath,
            ItemPollLocationBinding binding,
            Context context) {
        float scalex = 1.005f;
        pathStack[1] = richPath;
        binding.areaName.setText(richPath.getName().toUpperCase());
        richPath.setScaleX(scalex);
        richPath.setFillColor(context.getResources().getColor(R.color.colorPrimary));
        if (pathStack[0] != null) {
            if (pathStack[0] == pathStack[1]) {
                pathStack[0].setFillColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                pathStack[0].setFillColor(context.getResources().getColor(R.color.mapColor));
            }
            //saving previous view
            pathStack[0] = pathStack[1];
        } else {
            //Insert initial view
            pathStack[0] = pathStack[1];
        }
        for (int i = 0; i < model.size(); i++) {
            if (model.get(i).label.equals(richPath.getName())) {
                setLocationData(binding, model.get(i));
            }
        }

    }
}
