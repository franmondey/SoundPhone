package com.franmondey.soundphone;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.franmondey.soundphone.controller.api.SoundApi;
import com.franmondey.soundphone.controller.api.SoundApiFactory;
import com.franmondey.soundphone.model.beans.Collection;
import com.franmondey.soundphone.model.beans.FetchSetsResult;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;
import com.magnet.android.mms.exception.SchemaException;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private Context context;
    private LinearLayoutManager layoutManager;
    private List<Collection> value;
    private Toolbar mToolbar;
    private android.support.v7.view.ActionMode actionMode;
    public MultiSelector mMultiSelector = new MultiSelector();



    private BroadcastReceiver mBroadcastReceiver;
    private final IntentFilter mIntentFilter = new IntentFilter() {
        {
            // interested in this action only
            addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//            addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
//            addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        }
    };

    private DownloadManager downloadManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.activity_main);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    onDownloadComplete(intent);
                }
            }
        };

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getApplicationInfo().name);
        setSupportActionBar(mToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        // this is the default; this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        try {
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(context);

            // invoke the controller using the generated controller class
            SoundApiFactory restFactory = new SoundApiFactory(magnetClient);
            SoundApi restController  = restFactory.obtainInstance();

            Call g =  restController.fetchSets("","","",""/* Tracks to get, OAUTH TOKEN , "json", STATUS CODE MAP*/, new StateChangedListener() {
                @Override
                public void onExecuting(Call<?> call, ProgressData progressData) {

                }

                @Override
                public void onSuccess(Call<?> call) throws Throwable {
                    FetchSetsResult results = ((Call<FetchSetsResult>) call).get();
                    value = results.getCollection();
                    int i=0;
                    while (i < value.size()) {
                        if(value.get(i).getTrack() == null) {
                            value.remove(i);
                        } else {
                            i++;
                        }
                    }
                    i=0;
                    while (i < value.size()) {
                        if(value.get(i).getTrack().getDuration() < 900000) {
                            value.remove(i);
                        } else {
                            i++;
                        }
                    }
                    Log.d("SoundPhone", "size of collection: " + value.size());
                    Log.d("SoundPhone", "fetched future_href");
                    Log.d("SoundPhone", "fetched next_href: " + results.getNext_href());

                    adapter = new MyRecyclerAdapter(context, value);
                    mRecyclerView.setAdapter(adapter);

                }

                @Override
                public void onError(Call<?> call, Throwable throwable) {
                    Log.d("SoundPhone", "A la b: " + call.toString() + " " + throwable.toString());
                    Toast.makeText(context, "Check your Internet Connection",
                            Toast.LENGTH_LONG).show();
                }
            });

        } catch (SchemaException se) {
            // please look at SchemaException for the detail
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mBroadcastReceiver);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_download) {
            Log.d("SoundPhone", "lista de colecciones: " + value);
            downloadTrack(value);
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadTrack(List<Collection> collections) {
        int i = 0;
        String strI;
        String url;
        while(i<collections.size()) {
            url = collections.get(i).getTrack().getStream_url()+"/*ADD CLIENT ID*/";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false);
            request.setTitle(collections.get(i).getTrack().getTitle());
            strI = Integer.toString(i);
            request.setDescription(strI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, collections.get(i).getTrack().getUser().getUsername() +
                    " - " +
                    collections.get(i).getTrack().getTitle() + ".mp3");

            Log.d("SoundPhone", "lugar de descarga: " + url);
            try {
            /*downloadID = */
                downloadManager.enqueue(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    private void onDownloadComplete(Intent intent) {
        final long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        final DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        final Cursor c = downloadManager.query(query);
        try {
            c.moveToFirst();
            if (DownloadManager.STATUS_SUCCESSFUL
                    == c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                final String description = c.getString(c.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
                final int descInt = Integer.parseInt(description);
                final String uriStr =
                        c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                final File f = new File(Uri.parse(uriStr).getPath());

                Mp3File mp3file = new Mp3File(f);
                ID3v2 id3v2Tag;
                if (mp3file.hasId3v2Tag()) {
                    id3v2Tag = mp3file.getId3v2Tag();
                } else {
                    // mp3 does not have an ID3v2 tag, let's create one..
                    id3v2Tag = new ID3v24Tag();
                    mp3file.setId3v2Tag(id3v2Tag);
                }

                Toast.makeText(getApplicationContext(), "Preparing for takeoff", Toast.LENGTH_SHORT).show();

                id3v2Tag.setTrack(value.get(descInt).getTrack().getTitle());
                id3v2Tag.setItunesComment(value.get(descInt).getTrack().getDescription());
                id3v2Tag.setArtist(value.get(descInt).getTrack().getUser().getUsername());
                id3v2Tag.setTitle(value.get(descInt).getTrack().getTitle());
                id3v2Tag.setAlbum(value.get(descInt).getTrack().getLabel_name());
                //id3v2Tag.setYear(Integer.toString(value.get(descInt).getTrack().getRelease_year()));
                id3v2Tag.setGenre(12);
                id3v2Tag.setComment(value.get(descInt).getTrack().getDescription());
                id3v2Tag.setComposer(value.get(descInt).getTrack().getUser().getUsername());
                id3v2Tag.setPublisher(value.get(descInt).getUser().getUsername());
                id3v2Tag.setOriginalArtist(value.get(descInt).getTrack().getUser().getUsername());
                id3v2Tag.setAlbumArtist(value.get(descInt).getTrack().getUser().getUsername());
                id3v2Tag.setCopyright(value.get(descInt).getTrack().getLicense());
                id3v2Tag.setUrl(value.get(descInt).getTrack().getStream_url());
                id3v2Tag.setEncoder(value.get(descInt).getTrack().getTag_list());
                id3v2Tag.setAudiofileUrl(value.get(descInt).getTrack().getPermalink_url());
                id3v2Tag.setGrouping(value.get(descInt).getTrack().getStream_url());
                mp3file.save(f.getName());
            }
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private android.support.v7.view.ActionMode.Callback mDownloadMode = new ModalMultiSelectorCallback(mMultiSelector){
        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_selected_recyclerview, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_download_selected:
                    List<Collection> downloadSelected = new ArrayList<Collection>(value);

                    List<Integer> selectedItemPositions = mMultiSelector.getSelectedPositions();
                    for (int i = downloadSelected.size() - 1; i >= 0; i--) {
                        boolean inList = false;
                        for(int j = selectedItemPositions.size() - 1; j >=0; j--) {
                            if (selectedItemPositions.get(j) == i) {
                                inList = true;
                            }
                        }
                        if(!inList) {
                            downloadSelected.remove(i);
                        }
                    }
                    Log.d(TAG, downloadSelected.toString());

                    /*
                    for (int i = mCrimes.size()-1; i >= 0; i--) {
                        if (mMultiSelector.isSelected(i, 0)) {
                            Crime crime = mCrimes.get(i);
                            CrimeLab.get(getActivity()).deleteCrime(crime);
                            mRecyclerView.getAdapter().notifyItemRemoved(i);
                        }
                    }*/
                    downloadTrack(downloadSelected);
                    mMultiSelector.clearSelections();
                    mode.finish();
                    mToolbar.setVisibility(View.VISIBLE);

                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode actionMode) {
            super.onDestroyActionMode(actionMode);
            mToolbar.setVisibility(View.VISIBLE);
        }
    };

    public class FeedListRowHolder extends SwappingHolder
            implements View.OnClickListener, View.OnLongClickListener {
        protected ImageView thumbnail;
        protected TextView track;
        protected TextView artist;

        public FeedListRowHolder(View view) {
            super(view, mMultiSelector);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.track = (TextView) view.findViewById(R.id.track_name);
            this.artist = (TextView) view.findViewById(R.id.artist_name);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            view.setLongClickable(true);
        }

        @Override
        public void onClick(View v) {

            if (!mMultiSelector.tapSelection(this)) {

                Toast.makeText(v.getContext(),"Clicked",Toast.LENGTH_LONG).show();
                // start an instance of CrimePagerActivity
            /*Intent i = new Intent(getActivity(), CrimePagerActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
            startActivity(i);*/
            }
        }

        @Override
        public boolean onLongClick(View v) {
            startSupportActionMode(mDownloadMode);
            mToolbar.setVisibility(View.GONE);
            mMultiSelector.setSelected(this, true);
            return true;
        }
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {

        // TODO: 18/06/2015 Refresh y Looking for list spinners


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

        public Collection getItem(int position) {
            return value.get(position);
        }

        @Override
        public void onBindViewHolder(MainActivity.FeedListRowHolder feedListRowHolder, int i) {
            Collection feedItem = value.get(i);


            Picasso.with(mContext).load(feedItem.getTrack().getArtwork_url())
                    .placeholder(R.drawable.ic_crop_original_white_48dp).into(feedListRowHolder.thumbnail);

            feedListRowHolder.track.setText(feedItem.getTrack().getTitle());
            feedListRowHolder.artist.setText(feedItem.getUser().getUsername());

        }


        @Override
        public int getItemCount() {
            return value.size();
        }

    }
}

