package nsa.welshbeacons;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GCellBeaconManagerScanEvents {

    public MapsActivity() {
        markerMap = new HashMap<Marker, LocationInformation>();
    }

    private GoogleMap googleMap;
    private DatabaseConnector database;

    private ArrayList<MajorMinorPair> majorMinorPairs = new ArrayList<MajorMinorPair>();

    GCellBeaconScanManager scanMan;

    private Map<Marker, LocationInformation> markerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        database = new DatabaseConnector(getApplicationContext());

        checkIfFirstRun();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setTitle("Welsh Beacons");

        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);

        scanMan.startScanningForBeacons();

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), adap.getItem(i).toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        loadDatabaseMarkers();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LocationInformation locationInformation = markerMap.get(marker);
        Intent i = new Intent(this, CompanyInformationActivity.class);
        i.putExtra("LocationInformation", locationInformation);
        startActivity(i);
    }

    private void loadDatabaseMarkers() {
        ArrayList<LocationInformation> locations = database.getLocations();
        Toast.makeText(getApplicationContext(), String.valueOf(locations.size()), Toast.LENGTH_SHORT).show();
        for(int i = 0; i < locations.size(); i++) {
            LocationInformation locationInformation = locations.get(i);
            Marker newMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(locationInformation.getLocationLatitude()), Double.valueOf(locationInformation.getLocationLongitude())))
                    .title(locationInformation.getLocationName())
                    .snippet(locationInformation.getLocationAddressLine1())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            markerMap.put(newMarker, locationInformation);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(locations.get(0).getLocationLatitude()), Double.valueOf(locations.get(0).getLocationLongitude())), (float)14.0));
        googleMap.setOnInfoWindowClickListener(this);
    }

    private void  checkIfFirstRun() {
        int currentVersionCode = 0;
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode; //
        }catch(android.content.pm.PackageManager.NameNotFoundException e){}
        SharedPreferences sharedPrefs = getSharedPreferences("WelshBeacons", MODE_PRIVATE);
        int savedVersionCode = sharedPrefs.getInt("FirstRun-001", -1);

        if(currentVersionCode != savedVersionCode) {
//            Intent i = new Intent(MapsActivity.this, FirstRun.class);
//            startActivity(i);
            sharedPrefs.edit().putInt("FirstRun-001", currentVersionCode).commit();
            database.createSampleData();
        }
    }

    public Map<Marker, LocationInformation> getMarkerMap() {
        return markerMap;
    }

    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {
        for(GCelliBeacon beacon : list) {
            if(containsMajorMinorPair(new MajorMinorPair(String.valueOf(beacon.getMajorNo()), String.valueOf(beacon.getMinorNo()))) == false) {
                majorMinorPairs.add(new MajorMinorPair(String.valueOf(beacon.getMajorNo()), String.valueOf(beacon.getMinorNo())));
                if (beacon.getProximity() == GCelliBeacon.GCellProximity.IMMEDIATE || beacon.getProximity() == GCelliBeacon.GCellProximity.NEAR
                        ) {
                    int i = Log.i(beacon.getProxUuid().getStringFormattedUuid(), beacon.getProxUuid().getStringFormattedUuid());
                    if (beacon.getProxUuid().getStringFormattedUuid().equals("96530D4D-09AF-4159-B99E-951A5E826584")) {
                        Toast.makeText(getApplicationContext(), "MacBook", Toast.LENGTH_LONG).show();
                        LocationInformation beaconLocationInformation = database.getLocationInformationByBeaconMajorMinorPair("96530D4D-09AF-4159-B99E-951A5E826584", new MajorMinorPair("1000", "609"));// != null) // MajorMinorPair(beacon.getMajorNo().toString(), beacon.getMinorNo().toString())) == null) {
                        if (beaconLocationInformation == null) {
                            createNotification(true, majorMinorPairs.size(), "Welsh Beacons", "A Welsh speaking location is available in your immediate area.");
                        }
                        if (beaconLocationInformation != null) {
                            createNotification(true, majorMinorPairs.size(), "Welsh Beacons", beaconLocationInformation.getLocationName() + " is a Welsh speaking location available in your immediate area.");
                        }
                    }
                }
            }
        }
    }

    private boolean containsMajorMinorPair(MajorMinorPair majorMinorPair) {
        for(MajorMinorPair majorMinorPair1 : majorMinorPairs) {
            if(majorMinorPair1.getMajor().equals(majorMinorPair.getMajor()) && majorMinorPair1.getMinor().equals(majorMinorPair.getMinor())) {
                return true;
            }
        }
        return false;
    }

    //Code procedure below is Copyright (C) Chris Gwilliams
    public void createNotification(boolean dismiss, int id, String title, String text) {
        android.support.v4.app.NotificationCompat.Builder notifBuilder = new android.support.v4.app.NotificationCompat.Builder(getApplicationContext());
        // You can look at other attributes to set but these three MUST be set in order to build
        notifBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(text);
        //TODO EXTRA CREDIT could you improve this?
        if(dismiss == false) {
            notifBuilder.setOngoing(true);
        }
//        //Create an action for when the intent is clicked (just opening this activity)
//        Intent resultIntent = new Intent(this, MainActivity.class);
//        PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        this,
//                        0,
//                        resultIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        notifBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, notifBuilder.build());


    }
    //Code procedure below is Copyright (C) Chris Gwilliams
    public void dismissNotification(int id) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(id);
    }

    @Override
    public void didEnterBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {

    }

    @Override
    public void didExitBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {

    }

    @Override
    public void didRangeBeaconsinRegion(GCellBeaconRegion gCellBeaconRegion, List<GCelliBeacon> list) {

    }

    @Override
    public void bleNotSupported() {

    }

    @Override
    public void bleNotEnabled() {

    }

    @Override
    public void locationPermissionsDenied() {

    }
}
