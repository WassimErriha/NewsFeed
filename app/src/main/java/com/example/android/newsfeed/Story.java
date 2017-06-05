package com.example.android.newsfeed;

/**
 * Created by jessica on 3/8/2017.
 */

public class Story {

   String  mTitle;
    String mSection;
    String mDate;
    String mUrl;
    String mThumbnail;



    public Story(String title, String section, String date, String url, String thumbnail){
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
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
        return mDate;}
    public String getThumbnail (){
        return mThumbnail;
    }
}
