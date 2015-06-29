package com.franmondey.soundphone;

// TODO: 18/06/2015 Mantener Lista de Sets Bajados para no repetir (borrar luego de mucho tiempo)
// TODO: 18/06/2015 Search of individual tracks to download

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Spinner;
import android.widget.Toast;

import com.franmondey.soundphone.adapter.MyRecyclerAdapter;
import com.franmondey.soundphone.controller.api.SoundApi;
import com.franmondey.soundphone.controller.api.SoundApiFactory;
import com.franmondey.soundphone.model.beans.Collection;
import com.franmondey.soundphone.model.beans.FetchSetsResult;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedHandler;
import com.magnet.android.mms.async.StateChangedListener;
import com.magnet.android.mms.exception.SchemaException;


import java.io.File;
import java.util.List;


public class MainActivity extends Activity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private Context context;
    private LinearLayoutManager layoutManager;
    private List<Collection> value;

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

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        context = getApplicationContext();

        try {
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(context);

            // invoke the controller using the generated controller class
            SoundApiFactory restFactory = new SoundApiFactory(magnetClient);
            SoundApi restController  = restFactory.obtainInstance();

            Call g =  restController.fetchSets("50", "your auth TOKEN. ex: 1-38435-3000118-73200172126f26e8", "json", "200", new StateChangedListener() {
                @Override
                public void onExecuting(Call<?> call, ProgressData progressData) {
                    //Log.d("APP", "Haciendo: " + call.toString());
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
                }
            });

        } catch (SchemaException se) {
            // please look at SchemaException for the detail
        }
        /*
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SoundcloudActivity.class);
                Log.d("Soundphone","Starting Activity");
                startActivity(intent);
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mBroadcastReceiver);
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
        String url;
        while(i<collections.size()) {
            url = collections.get(i).getTrack().getStream_url()+"?client_id=eef4791630fde79f4f8edaa9dac58f37";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false);
            request.setTitle(collections.get(i).getTrack().getTitle());
            request.setDescription("Downloading");

            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_MUSIC, collections.get(i).getTrack().getUser().getUsername() +
                    " - " +
                    collections.get(i).getTrack().getTitle() + ".mp3");

            Log.d("SoundPhone", "lugar de descarga: " + url.toString());
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
                final String uriStr =
                        c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                final File f = new File(Uri.parse(uriStr).getPath());

                Toast.makeText(getApplicationContext(), f.toString(),
                        Toast.LENGTH_LONG).show();

// TODO: 18/06/2015 TAG MP3's
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }
}

