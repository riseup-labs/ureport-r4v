package com.risuplabs.ureport.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.risuplabs.ureport.R;

import java.io.File;

import javax.inject.Inject;

public class ShowUtils {

    public static void loadImage(Context context, Uri uri,ImageView imageView){
        Glide.with(context)
                .load(new File(uri.getPath()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image)
                .into(imageView);
    }

}
