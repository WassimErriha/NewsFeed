package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class MyLoader extends AsyncTaskLoader <ArrayList<Story>>{

    private static final String LOG_TAG = MyLoader.class.getName();

    String mUrl;

    public MyLoader(Context context, String url) {
        super(context);
        mUrl = url;}
    @Override
    protected void onStartLoading() {
        forceLoad();}

    @Override
    public ArrayList<Story> loadInBackground() {
        if (mUrl == null) {
            return null;}
        ArrayList <Story> earthquake = QueryUtils.fetchStoriesData(mUrl);
        return earthquake;
    }
}
