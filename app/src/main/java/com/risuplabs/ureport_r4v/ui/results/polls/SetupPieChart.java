package com.risuplabs.ureport_r4v.ui.results.polls;

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
import com.risuplabs.ureport_r4v.databinding.ItemPollPieChartBinding;
import com.risuplabs.ureport_r4v.databinding.ItemPollsBinding;
import com.risuplabs.ureport_r4v.model.results.ModelQuestion;

import java.util.ArrayList;

public class SetupPieChart {

    public static void setUpPieChart(ModelQuestion item, ItemPollPieChartBinding binding, ItemPollsBinding parent) {

        parent.layoutPieChart.chartParent.setVisibility(View.VISIBLE);
        parent.layoutStatistics.stateParent.setVisibility(View.GONE);
        parent.textViewStatistics.setVisibility(View.GONE);
        parent.textViewlocations.setVisibility(View.GONE);
        parent.textViewGender.setVisibility(View.GONE);
        parent.textViewAge.setVisibility(View.GONE);
        parent.textViewPieChart.setVisibility(View.VISIBLE);

        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setExtraOffsets(5, 5, 5, 5);

        binding.pieChart.setDragDecelerationFrictionCoef(0.95f);
        binding.pieChart.getLegend().setWordWrapEnabled(true);


        Legend l = binding.pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        binding.pieChart.setEntryLabelColor(Color.DKGRAY);

        binding.pieChart.setEntryLabelTextSize(12f);

        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setHoleColor(Color.WHITE);

        binding.pieChart.setTransparentCircleColor(Color.WHITE);
        binding.pieChart.setTransparentCircleAlpha(110);

        binding.pieChart.setHoleRadius(38f);
        binding.pieChart.setTransparentCircleRadius(45f);

        binding.pieChart.setDrawCenterText(false);

        binding.pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        binding.pieChart.setRotationEnabled(true);
        binding.pieChart.setHighlightPerTapEnabled(true);
        binding.pieChart.setDrawSliceText(false);
        binding.pieChart.setDrawEntryLabels(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < item.results.categories.size(); i++) {
            if (i < 25) {
                entries.add(new PieEntry(
                        item.results.categories.get(i).count,
                        item.results.categories.get(i).label
                ));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(binding.pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        binding.pieChart.setData(data);
        binding.pieChart.highlightValues(null);
        binding.pieChart.invalidate();

        binding.pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                binding.pieDisplay.setVisibility(View.VISIBLE);
                binding.pieLabel.setText(pe.getLabel());
                binding.pieValue.setText(((int) pe.getValue()) + "");
            }

            @Override
            public void onNothingSelected() {
                binding.pieDisplay.setVisibility(View.GONE);
                binding.pieLabel.setText("");
                binding.pieValue.setText("");
            }
        });

    }

}
