package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Story>>
        ,StoryAdapter.OnStoryClicked{

    public StoryAdapter storyAdapter;
    public RecyclerView recyclerView;

    public String storiesUrl = "http://content.guardianapis.com/search?order-by=newest" +
            "&show-fields=thumbnail&q=android%20AND%20ios" +
            "&api-key=12cfac12-f8ac-4c29-90e5-15562b6a454c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }
    @Override
    public Loader<ArrayList<Story>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(storiesUrl);

        return new MyLoader(this, baseUri.toString());
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<Story>> loader, ArrayList<Story> stories) {

        storyAdapter = new StoryAdapter(stories,this);
        recyclerView.setAdapter(storyAdapter);
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Story>> loader) {
    }

    @Override
    public void onSwipe(Story story) {
        String storyUrl = story.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(storyUrl));
        startActivity(intent);
    }
}
