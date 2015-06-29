package com.franmondey.soundphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franmondey.soundphone.R;
import com.franmondey.soundphone.model.beans.Collection;
import com.squareup.picasso.Picasso;
import java.util.List;

// TODO: 18/06/2015 Seleccionar los Sets a mano
// TODO: 18/06/2015 Refresh y Looking for list spinners


public class MyRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {

    private List<Collection> value;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<Collection> value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "value must not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException(
                    "context must not be null");
        }
        this.value = value;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_list_item, viewGroup, false);
        FeedListRowHolder mh = new FeedListRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(FeedListRowHolder feedListRowHolder, int i) {
        Collection feedItem = value.get(i);

        Picasso.with(mContext).load(feedItem.getTrack().getArtwork_url())
                .error(R.drawable.placeholder)
                .into(feedListRowHolder.thumbnail);

        feedListRowHolder.track.setText(feedItem.getTrack().getTitle());
        feedListRowHolder.artist.setText(feedItem.getUser().getUsername());
    }

    @Override
    public int getItemCount() {
        return value.size();
    }
}
