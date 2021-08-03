package com.risuplabs.ureport.surveyor.task;

import android.os.AsyncTask;

import com.risuplabs.ureport.surveyor.data.Submission;
import com.risuplabs.ureport.surveyor.net.TembaException;
import com.risuplabs.ureport.utils.surveyor.Logger;

import java.io.IOException;


/**
 * Task for sending submissions to the server
 */
public class SubmitSubmissionsTask extends AsyncTask<Submission, Integer, Integer> {

    private Listener listener;
    private int numFailed = 0;

    public SubmitSubmissionsTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(Submission... submissions) {
        int total = submissions.length;

        int s = 0;
        for (Submission submission : submissions) {
            try {
                submission.submit();
            } catch (IOException | TembaException e) {
                Logger.e("Unable to send submission", e);
                numFailed++;
            }

            s++;
            publishProgress(100 * s / total);
        }

        return total;
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
    protected void onPostExecute(Integer total) {
        super.onPostExecute(total);

        if (numFailed > 0) {
            this.listener.onFailure(numFailed);
        } else {
            try {
                this.listener.onComplete(total);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface Listener {
        void onProgress(int percent);

        void onComplete(int total) throws IOException;

        void onFailure(int numFailed);
    }
}
