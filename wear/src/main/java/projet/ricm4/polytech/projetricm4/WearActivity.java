package projet.ricm4.polytech.projetricm4;


import android.support.wearable.view.WearableListView;
import android.content.Context;
import java.util.List;

import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class WearActivity extends Activity implements WearableListView.ClickListener, MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {
    private final String LOG_TAG = WearActivity.class.getSimpleName();
    private WearableListView listView;

    private static final String WEAR_MESSAGE_PATH = "/message";
    private static final String WEAR_MESSAGE_PATH2 = "/message2";
    private GoogleApiClient mApiClient;
   public List<SettingsItems> items;
   public  SettingsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);



        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                listView = (WearableListView) stub.findViewById(R.id.sample_list_view);
                loadAdapter();

            }
        });
        items = new ArrayList<>();

        initGoogleApiClient();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    private void loadAdapter() {
        //items.add(new SettingsItems(R.drawable.ic_action_locate, "toto"));

         mAdapter = new SettingsAdapter(this, items);

        listView.setAdapter(mAdapter);

        listView.setClickListener(this);
    }


    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        switch (viewHolder.getPosition()) {
            case 0:
                //Do something
                break;
            case 1:
               //Do something else
                break;
        }
    }

    @Override
    public void onTopEmptyRegionClick() {
        //Prevent NullPointerException
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void onDestroy() {
        if( mApiClient != null )
            mApiClient.unregisterConnectionCallbacks( this );
        super.onDestroy();
    }

    protected void onStop() {
        if ( mApiClient != null ) {
            Wearable.MessageApi.removeListener( mApiClient, this );
            if ( mApiClient.isConnected() ) {
                mApiClient.disconnect();
            }
        }
        super.onStop();
    }
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
    }

    @Override
    public void onMessageReceived(final MessageEvent messageEvent) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {

                if( messageEvent.getPath().equalsIgnoreCase( WEAR_MESSAGE_PATH ) ) {

                    System.out.println("Recuuuu !!!!");
                    String recu =new String(messageEvent.getData());
                    Log.i(LOG_TAG, "data :"+recu.split("\n")[0]);
                    items.add(new SettingsItems(R.drawable.ic_action_locate, recu.split("\n")[0]));
                    mAdapter.notifyDataSetChanged();



                }
                if( messageEvent.getPath().equalsIgnoreCase( WEAR_MESSAGE_PATH2 ) ) {
                    Log.i(LOG_TAG, "Recu debut !!");
                    items.clear();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}