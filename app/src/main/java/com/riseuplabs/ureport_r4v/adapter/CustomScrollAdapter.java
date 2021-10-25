package com.riseuplabs.ureport_r4v.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.model.dashboard.ModelDashboardListRV;

import java.util.List;

public class CustomScrollAdapter extends RecyclerView.Adapter<CustomScrollAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private final List<ModelDashboardListRV> data;

    public CustomScrollAdapter(List<ModelDashboardListRV> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.dashboard_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Animate Here
        holder.activityName.setText(data.get(position).getName());
        holder.bgColor.setBackgroundResource(data.get(position).getBgColor());
        holder.cardImage.setImageResource(data.get(position).getCardImage());
        holder.bgShadow.setImageResource(data.get(position).getBgShadow());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView activityName;
        private final View bgColor;
        private final ImageView cardImage;
        private final ImageView bgShadow;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            bgColor = itemView.findViewById(R.id.bgColor);
            activityName = itemView.findViewById(R.id.activityName);
            cardImage = itemView.findViewById(R.id.cardImage);
            bgShadow = itemView.findViewById(R.id.bg_shadow);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CustomScrollAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}