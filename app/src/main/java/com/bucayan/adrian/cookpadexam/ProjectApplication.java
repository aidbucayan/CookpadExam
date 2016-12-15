package com.bucayan.adrian.cookpadexam;

import android.app.Application;
import android.content.Context;

/**
 * @author by Adrian Bucayan on 12/15/2016.
 */
public class ProjectApplication extends Application {

    private static ProjectApplication instance;

    public static ProjectApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
