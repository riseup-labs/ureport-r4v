package com.riseuplabs.ureport_r4v.ui.opinions.flow_list;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.FlowListAdapter;
import com.riseuplabs.ureport_r4v.base.BaseFragment;
import com.riseuplabs.ureport_r4v.databinding.FragmentFlowListBinding;
import com.riseuplabs.ureport_r4v.surveyor.data.Flow;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.ui.opinions.OpinionViewModel;

import java.util.List;

import javax.inject.Inject;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class FlowListFragment extends BaseFragment<FragmentFlowListBinding> implements AbsListView.OnItemClickListener{

    private Container containerx;
    private ListAdapter adapter;

    @Inject
    OpinionViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_flow_list;
    }

    @Override
    public void onViewReady() {
        if(containerx.getOrg() == null){return;} // Not Logged In

        Org org = containerx.getOrg();
        List<Flow> items = containerx.getListItems();
        Log.d(TAG, "onViewReady: "+items.size());

        String firstUUID = "";
        if(items.size() > 0){
            firstUUID = items.get(0).getUuid();
        }

        adapter = new FlowListAdapter(prefManager, getActivity(), R.layout.item_flow, org, items, firstUUID);
        binding.list.setAdapter(adapter);
        binding.list.setOnItemClickListener(this);

        binding.flowRefreshLayout.setOnRefreshListener(() -> {
            playNotification(prefManager, getContext(), R.raw.swipe_sound);
            ((FlowListActivity) containerx).refreshFlows();
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            containerx = (Container) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement FlowListFragment.Container");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        containerx = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        playNotification(prefManager, getContext(), R.raw.button_click_yes);
        containerx.onItemClick((Flow) adapter.getItem(position));
    }

    /**
     * Container activity should implement this to be notified when a flow is clicked
     */
    public interface Container {
        Org getOrg();

        List<Flow> getListItems();

        void onItemClick(Flow flow);
    }

}