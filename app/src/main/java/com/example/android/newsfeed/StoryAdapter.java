package com.example.android.newsfeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import static java.lang.System.load;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private ArrayList <Story> mStories;
    public OnStoryClicked mStoryClickHandler;
    Context context;


    public interface OnStoryClicked {
        void onSwipe(Story story);
    }

    public StoryAdapter( Context context, ArrayList< Story> stories, OnStoryClicked storyClicked) {
        mStories = stories;
        mStoryClickHandler = storyClicked;
        this.context = context;

    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder viewHolder, int position) {

        Story story = mStories.get(position);

        String storyTitle = story.getTitle();
        String storySection = story.getSection();
        String storyDate = story.getDate();
        String storyThumbnail = story.getThumbnail();

        viewHolder.title.setText(storyTitle);
        viewHolder.section.setText(storySection);
        viewHolder.date.setText(storyDate);
        loadImage(storyThumbnail,viewHolder);
    }

    @Override
    public int getItemCount() {
        if (mStories == null) {
            return 0;
        }
        return mStories.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final TextView title;
            private final TextView section;
            private final TextView date;
            private final ImageView mImageView;


            private ViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);

                title = (TextView) itemView.findViewById(R.id.title_text_view);
                section = (TextView) itemView.findViewById(R.id.section_text_view);
                date = (TextView) itemView.findViewById(R.id.date_text_view);
                mImageView = (ImageView) itemView.findViewById(R.id.image_view);


            }

            @Override
            public void onClick(View v) {
                int itemPosition = getAdapterPosition();
                Story storyData = mStories.get(itemPosition);
                mStoryClickHandler.onSwipe(storyData);

            }
        }

        public void loadImage (String url, StoryAdapter.ViewHolder holder){
            Picasso.with(this.context)
                    .load(url)
                    .placeholder(R.drawable.image_place_holder)
                    .error(R.drawable.error_image)
                    .into(holder.mImageView);
        }
}
