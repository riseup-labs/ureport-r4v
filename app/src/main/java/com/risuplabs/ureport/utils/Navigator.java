package com.risuplabs.ureport.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Navigator {

    public static void navigate(Context context, Class target){
        Intent intent = new Intent(context,target);
        context.startActivity(intent);
    }

    public static void navigateWithBundle(Context context, Class target, String intentName, Bundle bundle){
        Intent intent = new Intent(context,target);
        intent.putExtra(intentName,bundle);
        context.startActivity(intent);
    }

}
