package com.riseuplabs.ureport_r4v.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.riseuplabs.ureport_r4v.R;

import java.io.File;

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
