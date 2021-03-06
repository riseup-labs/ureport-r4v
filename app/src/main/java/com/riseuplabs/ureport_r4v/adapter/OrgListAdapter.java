package com.riseuplabs.ureport_r4v.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;

import java.util.List;


public class OrgListAdapter extends ArrayAdapter<Org> {

    public OrgListAdapter(Context context, int resourceId, List<Org> orgs) {
        super(context, resourceId, orgs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewCache cache;
        Org org = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_org, parent, false);
            cache = new ViewCache();
            cache.titleView = convertView.findViewById(R.id.text_org);
            convertView.setTag(cache);
        } else {
            cache = (ViewCache) convertView.getTag();
        }

        cache.titleView.setText(org.getName());
        return convertView;
    }

    public static class ViewCache {
        TextView titleView;
    }
}