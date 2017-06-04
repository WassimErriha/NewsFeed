package com.example.android.newsfeed;

import static android.R.attr.thumb;
import static android.R.attr.thumbnail;

/**
 * Created by jessica on 3/8/2017.
 */

public class Story {

   String  mTitle;
    String mSection;
    String mDate;
    String mUrl;



    public Story(String title, String section, String date, String url){
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }
}
