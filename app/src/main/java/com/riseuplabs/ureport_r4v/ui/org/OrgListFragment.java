package com.riseuplabs.ureport_r4v.ui.org;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.OrgListAdapter;
import com.riseuplabs.ureport_r4v.base.BaseFragment;
import com.riseuplabs.ureport_r4v.databinding.FragmentOrgListBinding;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.riseuplabs.ureport_r4v.utils.surveyor.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class OrgListFragment extends BaseFragment<FragmentOrgListBinding> implements AbsListView.OnItemClickListener{

    private ListAdapter adapter;
    private Container container;

    @Inject
    OrgViewModel orgViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_org_list;
    }

    @Override
    public void onViewReady() {

        List<Org> items = getOrgs();

        adapter = new OrgListAdapter(getActivity(), R.layout.item_org, items);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(this);

    }

    public List<Org> getOrgs() {
        Set<String> orgUUIDs = prefManager.getStringSet(SurveyorPreferences.AUTH_ORGS, Collections.emptySet());
        List<Org> orgs = new ArrayList<>(orgUUIDs.size());

        for (String uuid : orgUUIDs) {
            try {
                orgs.add(SurveyorApplication.get().getOrgService().get(uuid,"poll"));
            } catch (Exception e) {
                Logger.e("Unable to load org", e);
            }
        }
        return orgs;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            container = (Container) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(requireActivity().toString() + " must implement OrgListFragment.Container");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        container = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        playNotification(prefManager, getContext(), R.raw.button_click_yes, view);
        container.onItemClick((Org) adapter.getItem(position));
    }

    public interface Container {
        void onItemClick(Org org);
    }
}