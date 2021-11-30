package com.riseuplabs.ureport_r4v.utils.pref_manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.riseuplabs.ureport_r4v.di.SurveyorApplication;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Inject
    public SharedPrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        doEdit();
        editor.putString(key, value);
        doCommit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public String getString(String key,String value){
        return sharedPreferences.getString(key,value);
    }

    public void putInt(String key, int value) {
        doEdit();
        editor.putInt(key, value);
        doCommit();
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }
    public int getInt(String key,int value){
        return sharedPreferences.getInt(key,value);
    }

    public void putBoolean(String key, boolean value) {
        doEdit();
        editor.putBoolean(key, value);
        doCommit();
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);

    }public boolean getBoolean(String key, boolean def_value){
        return sharedPreferences.getBoolean(key,def_value);
    }

    public void clearPreference(String key) {
        doEdit();
        editor.remove(key);
        doCommit();
    }

    public void setPreference(String key, Set<String> values) {
        doEdit();
        editor.putStringSet(key, values).apply();
        doCommit();
    }

    public Set<String> getStringSet(String key, Set<String> defValues){
        return sharedPreferences.getStringSet(key,defValues);
    }

    private void doEdit() {
        if (editor != null)
            editor.apply();
    }

    private void doCommit() {
        if (editor != null)
            editor.commit();
    }

    public void clear(){
        doEdit();
        editor.clear();
        doCommit();
    }

    public static int getORGID(){
        Context context = SurveyorApplication.get();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(PrefKeys.ORG_ID,37);
    }

}
