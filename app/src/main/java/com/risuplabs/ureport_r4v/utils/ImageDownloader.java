package com.risuplabs.ureport_r4v.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport_r4v.model.story.ModelProgress;
import com.risuplabs.ureport_r4v.model.story.ModelStoryImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ImageDownloader {
    int count = 0;
    int size ;
    List<ModelStoryImage> imageList;

    public void downloadImage(List<ModelStoryImage> list, Context context) {
        Collections.reverse(list);
        this.imageList = list;
        size = imageList.size();
        for(int i  = 0 ; i < size ; i++){
            ContextWrapper wrapper = new ContextWrapper(context);
            File file = wrapper.getDir("Images", MODE_PRIVATE);
            file = new File(file, imageList.get(i).filename );
            if (!file.exists()) {
                new Downloader(context,imageList.get(i).filename).execute(stringToURL(list.get(i).url));
            }
        }
    }

    private class Downloader extends AsyncTask<URL, Void, Bitmap> {

        private static final String TAG = "DownloadTask";
        String filename;
        Context context;

        public Downloader(Context context,String filename) {
            this.filename = filename;
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: started");
        }

        protected Bitmap doInBackground(URL... urls) {
            if (urls.length != 0) {
                URL url = urls[0];
                HttpURLConnection connection = null;

                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                    return bmp;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {

                if(count == size){
                    count = 0;
                    size = 0;
                    imageList = new ArrayList<>();
                    ModelProgress progress = new ModelProgress(0,0,"finish");
                    ProgressData.progressData.setValue(progress);
                }else{
                    count++;
                    ModelProgress progress = new ModelProgress(count,size,"success");
                    ProgressData.progressData.setValue(progress);
                    saveImageToInternalStorage(context,filename,result);
                }
            } else {
                if(count == size){
                    count = 0;
                    size = 0;
                    imageList = new ArrayList<>();
                    ModelProgress progress = new ModelProgress(0,0,"finish");
                    ProgressData.progressData.setValue(progress);
                }else {
                    Log.d(TAG, "onPostExecute: error");
                    count++;
                    ModelProgress progress = new ModelProgress(count, size, "error");
                    ProgressData.progressData.setValue(progress);
                }
            }

        }
    }

    public URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveImageToInternalStorage(Context context,String filename,Bitmap bitmap) {
        ContextWrapper wrapper = new ContextWrapper(context);
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, filename);

        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            stream.flush();
            stream.close();

        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }
    }

    public LiveData<Uri> getImageUri(Context context,String filename) {
        MutableLiveData<Uri> uri = new MutableLiveData<>();
        ContextWrapper wrapper = new ContextWrapper(context);
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, filename );
        if (file.exists()) {
            uri.setValue(Uri.parse(file.getAbsolutePath()));
        }
        else{
            uri.setValue(null);
        }
        return uri;
    }

    public boolean isFileExist(Context context,String filename){
        ContextWrapper wrapper = new ContextWrapper(context);
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, filename);
        return file.exists();
    }

}
