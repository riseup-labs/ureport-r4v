package com.riseuplabs.ureport_r4v.ui.results.polls;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.richpath.RichPath;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.result.LocationSpinnerAdapter;
import com.riseuplabs.ureport_r4v.adapter.result.PollStatisticsAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemPollLocationBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestion;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionCategory;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByLocation;

import java.util.ArrayList;
import java.util.List;

public class SetupLocationResult {

    public static void setupLocation(ModelQuestion item, ItemPollLocationBinding binding, Context context) {

        int size = item.results_by_location.size();
        final int[] set = {0};

        // Spinner Drop down elements
        ArrayList<ModelResultsByLocation> countries = new ArrayList<ModelResultsByLocation>();
        countries.addAll(item.results_by_location);

        LocationSpinnerAdapter adapter = new LocationSpinnerAdapter(context, countries);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id)
                    {
                        ModelResultsByLocation clickedItem = (ModelResultsByLocation) parent.getItemAtPosition(position);
                        String name = clickedItem.label;

                        List<ModelQuestionCategory> category = new ArrayList<>();

                        for(int i = 0 ; i < size ; i++){
                            if(name.equals(item.results_by_location.get(i).label)){
                                category.addAll(item.results_by_location.get(i).categories);
                                set[0] = item.results_by_location.get(i).set;
                            }
                        }

                        binding.recyclerViewParent.setHasFixedSize(true);
                        PollStatisticsAdapter mAdapter = new PollStatisticsAdapter(context, set[0]);
                        binding.recyclerViewParent.setAdapter(mAdapter);

                        mAdapter.addItems(category);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {
                    }
                });


    }
}
