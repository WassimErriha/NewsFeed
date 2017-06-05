package com.example.android.newsfeed;

import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.R.attr.thumbnail;

/**
 * Created by jessica on 3/8/2017
 */

public final class QueryUtils {

    public static final String LOG_TAG =QueryUtils.class.getName();


    public  static ArrayList<Story> fetchStoriesData(String url) {
        // create url

        URL storiesUrl = createUrl(url);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(storiesUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        ArrayList <Story> stories = extractStories(jsonResponse);

        return  stories;
    }

    public static ArrayList<Story> extractStories (String url){

        ArrayList<Story> stories = new ArrayList<>();


        try {


            JSONObject root = new JSONObject(url);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0 ; i < results.length(); i++ )
            {
                String thumbnail = null;
                JSONObject story = results.getJSONObject(i);
                String storyTitle = story.getString("webTitle");
                String storyUrl = story.getString("webUrl");
                String storySection = story.getString("sectionName");
                String storyDate = story.getString("webPublicationDate");
                try {
                    JSONObject fields = story.getJSONObject("fields");
                    if (fields != null ){
                        thumbnail = fields.getString("thumbnail");
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Problem retrieving the thumbnail", e);
                }
                    stories.add(new Story(storyTitle, storySection,storyDate, storyUrl,thumbnail));

            }
        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return stories;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
