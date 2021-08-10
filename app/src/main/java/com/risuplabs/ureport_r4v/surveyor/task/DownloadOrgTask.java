package com.risuplabs.ureport_r4v.surveyor.task;

import android.os.AsyncTask;

import com.risuplabs.ureport_r4v.surveyor.data.Org;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Flow;
import com.risuplabs.ureport_r4v.utils.surveyor.Logger;

import java.util.List;

/**
 * Task to completely refresh a single org - details and assets
 */
public class DownloadOrgTask extends AsyncTask<Org, Integer, Void> {

    private final Listener listener;
    public List<Flow> flows;
    private boolean failed;

    public DownloadOrgTask(Listener listener, List<Flow> flows) {
        this.listener = listener;
        this.flows = flows;
    }

    @Override
    protected Void doInBackground(Org... args) {
        Org org = args[0];

        try {
            org.download(true, new Org.RefreshProgress() {
                @Override
                public void reportProgress(int percent) {
                    publishProgress(percent);
                }
            },flows);

        } catch (Exception e) {
            Logger.e("Unable to refresh org", e);
            this.failed = true;
        }

        return null;
    }

    /**
     * @see AsyncTask#onProgressUpdate(Object[])
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        listener.onProgress(values[0]);

    }

    /**
     * @see AsyncTask#onPostExecute(Object)
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (this.failed) {
            this.listener.onFailure();
        } else {
            this.listener.onComplete();
        }
    }

    public interface Listener {
        void onProgress(int percent);

        void onComplete();

        void onFailure();
    }


}
