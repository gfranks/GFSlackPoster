package com.github.gfranks.slack.poster.app;

import android.app.Application;

import com.github.gfranks.slack.poster.GFSlackPoster;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GFSlackPoster.initDefaults(new GFSlackPoster.Builder()
                .setAppName(getString(R.string.app_name)));
    }
}
