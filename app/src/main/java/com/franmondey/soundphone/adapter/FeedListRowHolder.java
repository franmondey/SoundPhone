package com.franmondey.soundphone.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.franmondey.soundphone.R;

public class FeedListRowHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;
    protected TextView track;
    protected TextView artist;

    public FeedListRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.track = (TextView) view.findViewById(R.id.track_name);
        this.artist = (TextView) view.findViewById(R.id.artist_name);
    }
}
