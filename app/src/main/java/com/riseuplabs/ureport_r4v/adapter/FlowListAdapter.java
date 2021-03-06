package com.riseuplabs.ureport_r4v.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.surveyor.data.Flow;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.text.NumberFormat;
import java.util.List;

public class FlowListAdapter extends ArrayAdapter<Flow> {

    private final Org org;
    private String firstUUID = "";
    SharedPrefManager prefManager;

    public FlowListAdapter(SharedPrefManager prefManager, Context context, int resourceId, Org org, List<Flow> flows, String firstUUID) {
        super(context, resourceId, flows);
        this.firstUUID = firstUUID;
        this.org = org;
        this.prefManager = prefManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        //ViewCache cache;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        //if (row == null) {
        row = inflater.inflate(R.layout.item_flow, parent, false);
        //cache = new ViewCache();
        TextView titleView = row.findViewById(R.id.text_flow_name);
        TextView questionView = row.findViewById(R.id.text_flow_questions);
        TextView pendingSubmissions = row.findViewById(R.id.text_pending_submissions);
        TextView surveyor_last_updated = row.findViewById(R.id.surveyor_last_updated);
        //row.setTag(cache);

        //} else {
        //cache = (ViewCache) row.getTag();
        //}


        Flow flow = getItem(position);
        titleView.setText(flow.getName());

        if(flow.getUuid().equals(firstUUID)){
            String last_update = StaticMethods.getLocalUpdateDate(prefManager, "surveyor_last_updated_local");
            if(last_update.equals("")){
                surveyor_last_updated.setText("Pull down to refresh");
            }else{
                surveyor_last_updated.setText("Last updated: " + last_update + "\n"  + "Pull down to refresh");
            }
//            surveyor_last_updated.setVisibility(View.VISIBLE);
            //firstUUID = "-1";
        }

        int pending = SurveyorApplication.get().getSubmissionService().getCompletedCount(org, flow);

        NumberFormat nf = NumberFormat.getInstance();
        pendingSubmissions.setText(nf.format(pending));
        pendingSubmissions.setTag(flow);
        pendingSubmissions.setVisibility(pending > 0 ? View.VISIBLE : View.GONE);

        int numQuestions = flow.getQuestionCount();
        String questionsString = getContext().getResources().getQuantityString(R.plurals.questions, numQuestions, numQuestions);

        questionView.setText(questionsString + " (v" + nf.format(flow.getRevision()) + ")");
        return row;
    }


    @Override
    public long getItemId(int position) {
        return position; //return position here
    }
}
