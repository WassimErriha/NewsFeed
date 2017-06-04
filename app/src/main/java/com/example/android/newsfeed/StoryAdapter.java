package com.example.android.newsfeed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private ArrayList <Story> mStories;
    public OnStoryClicked mStoryClickHandler;

    public interface OnStoryClicked {
        void onSwipe(Story story);
    }

    public StoryAdapter(ArrayList< Story> stories, OnStoryClicked storyClicked) {
        mStories = stories;
        mStoryClickHandler = storyClicked;
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

        viewHolder.title.setText(storyTitle);
        viewHolder.section.setText(storySection);
        viewHolder.date.setText(storyDate);
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


            private ViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);

                title = (TextView) itemView.findViewById(R.id.title_text_view);
                section = (TextView) itemView.findViewById(R.id.section_text_view);
                date = (TextView) itemView.findViewById(R.id.date_text_view);


            }

            @Override
            public void onClick(View v) {
                int itemPosition = getAdapterPosition();
                Story storyData = mStories.get(itemPosition);
                mStoryClickHandler.onSwipe(storyData);



            }
        }
}
