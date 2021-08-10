package com.risuplabs.ureport_r4v.surveyor.task;

import android.os.AsyncTask;

import com.risuplabs.ureport_r4v.di.SurveyorApplication;
import com.risuplabs.ureport_r4v.surveyor.data.Org;
import com.risuplabs.ureport_r4v.surveyor.data.OrgService;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Token;
import com.risuplabs.ureport_r4v.utils.surveyor.Logger;

import java.util.HashSet;
import java.util.Set;


/**
 * Task to fetch orgs from RapidPro, create their directories, save their details, and return their UUIDs
 */
public class FetchOrgsTask extends AsyncTask<Token, Void, Set<String>> {

    private final Listener listener;
    private boolean failed;

    public FetchOrgsTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected Set<String> doInBackground(Token... tokens) {
        OrgService svc = SurveyorApplication.get().getOrgService();

        Set<Org> orgs = new HashSet<>();
        Set<String> orgUUIDs = new HashSet<>();

        for (Token token : tokens) {
            try {
                Org org = svc.getOrFetch(token.getOrg().getUuid(), token.getOrg().getName(), token.getToken());

                orgs.add(org);
                orgUUIDs.add(org.getUuid());

                Logger.d("Fetched org with UUID " + org.getUuid());
            } catch (Exception e) {
                Logger.e("Unable to fetch org", e);
                this.failed = true;
                break;
            }
        }

        return orgUUIDs;
    }

    @Override
    protected void onPostExecute(Set<String> orgUUIDs) {
        super.onPostExecute(orgUUIDs);

        if (this.failed) {
            this.listener.onFailure();
        } else {
            this.listener.onComplete(orgUUIDs);
        }
    }

    public interface Listener {
        void onComplete(Set<String> orgUUIDs);

        void onFailure();
    }
}
