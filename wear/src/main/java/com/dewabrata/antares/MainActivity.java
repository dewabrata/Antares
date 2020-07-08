package com.dewabrata.antares;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.ambient.AmbientModeSupport;
import androidx.wear.widget.WearableRecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends WearableActivity   implements AmbientModeSupport.AmbientCallbackProvider,
        DataClient.OnDataChangedListener,
        MessageClient.OnMessageReceivedListener,
        CapabilityClient.OnCapabilityChangedListener  {
    private static final String TAG = "MainActivity";

    private static final String CAPABILITY_1_NAME = "capability_1";
    private static final String CAPABILITY_2_NAME = "capability_2";


    private WearableRecyclerView mWearableRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomRecyclerAdapter mCustomRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWearableRecyclerView = findViewById(R.id.recycler_view);

        // Aligns the first and last items on the list vertically centered on the screen.
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        // Improves performance because we know changes in content do not change the layout size of
        // the RecyclerView.
        mWearableRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mWearableRecyclerView.setLayoutManager(mLayoutManager);

        refreshList("32c","6.8","80","0");



        // Enables Always-on
        setAmbientEnabled();
    }

    public void refreshList(String temperature, String ph, String humidity,String saklar){
        mCustomRecyclerAdapter = new CustomRecyclerAdapter(MainActivity.this,generateModelData(temperature, ph, humidity, saklar));

        mWearableRecyclerView.setAdapter(mCustomRecyclerAdapter);

        mWearableRecyclerView.invalidate();
    }

    public ArrayList<ModelData> generateModelData(String temperature, String ph, String humidity,String saklar){

        ArrayList<ModelData> data = new ArrayList<ModelData>();

        data.add(new ModelData(temperature,humidity,ph,saklar,0));
        data.add(new ModelData(temperature,humidity,ph,saklar,1));
        data.add(new ModelData(temperature,humidity,ph,saklar,2));
        data.add(new ModelData(temperature,humidity,ph,saklar,3));

        return data;

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Instantiates clients without member variables, as clients are inexpensive to create and
        // won't lose their listeners. (They are cached and shared between GoogleApi instances.)
        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);
    }

    /*
     * Sends data to proper WearableRecyclerView logger row or if the item passed is an asset, sends
     * to row displaying Bitmaps.
     */
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged(): " + dataEvents);

        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();
                DataItem item = event.getDataItem();
                if (DataLayerListenerService.ANTARES_PATH.equals(path)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                   // Toast.makeText(this, dataMap.getString("DATA"), Toast.LENGTH_LONG).show();

                    refreshList(dataMap.getString("temperature"),dataMap.getString("ph"),dataMap.getString("humidity"),dataMap.getString("saklar"));

                }


            }
        }

    }

    /*
     * Triggered directly from buttons in recycler_row_capability_discovery.xml to check
     * capabilities of connected devices.
     */
    public void onCapabilityDiscoveryButtonClicked(View view) {
        switch (view.getId()) {

        }
    }

    /*
     * Sends data to proper WearableRecyclerView logger row.
     */
    @Override
    public void onMessageReceived(MessageEvent event) {
        Log.d(TAG, "onMessageReceived: " + event);

    }

    /*
     * Sends data to proper WearableRecyclerView logger row.
     */
    @Override
    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        Log.d(TAG, "onCapabilityChanged: " + capabilityInfo);

    }




    /** Find the connected nodes that provide at least one of the given capabilities. */
    private void showNodes(final String... capabilityNames) {

        Task<Map<String, CapabilityInfo>> capabilitiesTask =
                Wearable.getCapabilityClient(this)
                        .getAllCapabilities(CapabilityClient.FILTER_REACHABLE);

        capabilitiesTask.addOnSuccessListener(
                new OnSuccessListener<Map<String, CapabilityInfo>>() {
                    @Override
                    public void onSuccess(Map<String, CapabilityInfo> capabilityInfoMap) {
                        Set<Node> nodes = new HashSet<>();

                        if (capabilityInfoMap.isEmpty()) {
                            showDiscoveredNodes(nodes);
                            return;
                        }
                        for (String capabilityName : capabilityNames) {
                            CapabilityInfo capabilityInfo = capabilityInfoMap.get(capabilityName);
                            if (capabilityInfo != null) {
                                nodes.addAll(capabilityInfo.getNodes());
                            }
                        }
                        showDiscoveredNodes(nodes);
                    }
                });
    }

    private void showDiscoveredNodes(Set<Node> nodes) {
        List<String> nodesList = new ArrayList<>();
        for (Node node : nodes) {
            nodesList.add(node.getDisplayName());
        }
        Log.d(
                TAG,
                "Connected Nodes: "
                        + (nodesList.isEmpty()
                        ? "No connected device was found for the given capabilities"
                        : TextUtils.join(",", nodesList)));
        String msg;
        if (!nodesList.isEmpty()) {
            msg = getString(R.string.connected_nodes, TextUtils.join(", ", nodesList));
        } else {
            msg = getString(R.string.no_device);
        }
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
        return new MyAmbientCallback();
    }

    /** Customizes appearance for Ambient mode. (We don't do anything minus default.) */
    private class MyAmbientCallback extends AmbientModeSupport.AmbientCallback {
        /** Prepares the UI for ambient mode. */
        @Override
        public void onEnterAmbient(Bundle ambientDetails) {
            super.onEnterAmbient(ambientDetails);
        }

        /**
         * Updates the display in ambient mode on the standard interval. Since we're using a custom
         * refresh cycle, this method does NOT update the data in the display. Rather, this method
         * simply updates the positioning of the data in the screen to avoid burn-in, if the display
         * requires it.
         */
        @Override
        public void onUpdateAmbient() {
            super.onUpdateAmbient();
        }

        /** Restores the UI to active (non-ambient) mode. */
        @Override
        public void onExitAmbient() {
            super.onExitAmbient();
        }
    }

}
