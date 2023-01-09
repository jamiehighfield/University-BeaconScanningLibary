package nsa.welshbeacons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnector {

    public DatabaseConnector(Context applicationContext) {
        databaseHelper = new DatabaseHelper(applicationContext);
    }

    DatabaseHelper databaseHelper;

    public void createSampleData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put("LocationName", "Costa Coffee");
        cv1.put("LocationAddressLine1", "Queen's Building's");
        cv1.put("LocationAddressLine2", "Cardiff");
        cv1.put("LocationPostcode", "CFAB CDE");
        cv1.put("LocationOpeningHours", "24 Hours");
        cv1.put("LocationLatitude", "51.484202");
        cv1.put("LocationLongitude", "-3.169671");
        ContentValues cv2 = new ContentValues();
        cv2.put("LocationName", "Starbucks");
        cv2.put("LocationAddressLine1", "Cardiff University Student's Union");
        cv2.put("LocationAddressLine2", "Senghenydd Road");
        cv2.put("LocationPostcode", "CFAB CDE");
        cv2.put("LocationOpeningHours", "24 Hours");
        cv2.put("LocationLatitude", "51.488374");
        cv2.put("LocationLongitude", "-3.177459");
        db.insert("Locations", null, cv1);
        db.insert("Locations", null, cv2);
        ContentValues cv3 = new ContentValues();
        cv3.put("BeaconGUID", "{ABC}");
        cv3.put("BeaconMajor", "1000");
        cv3.put("BeaconMinor", "109");
        cv3.put("LocationID", "1");
        db.insert("Beacons", null, cv3);
        db.close();
    }

    public ArrayList<LocationInformation> getLocations() {
        ArrayList<LocationInformation> returnLocationInformations = new ArrayList<LocationInformation>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns  = {"LocationID", "LocationName", "LocationAddressLine1", "LocationAddressLine2", "LocationPostcode", "LocationOpeningHours", "LocationLatitude", "LocationLongitude"};

        Cursor c = db.query("Locations", columns, null, null, null, null, null);
        c.moveToFirst();
        if(c.getCount() != 0) {
            do {
                returnLocationInformations.add(new LocationInformation(String.valueOf(c.getLong(0)), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7)));
            } while (c.moveToNext());
        }

        return returnLocationInformations;
    }

    public LocationInformation getLocationInformationByBeaconMajorMinorPair(String GUID, MajorMinorPair majorMinorPair) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Log.i("MajorMinor", majorMinorPair.getMajor() + " " + majorMinorPair.getMinor());
        String[] columnsQuery1  = {"BeaconID", "BeaconGUID", "BeaconMajor", "BeaconMinor", "LocationID"};
        String[] whereArgsQuery1 = {majorMinorPair.getMinor()};
        Cursor cursorQuery1 = db.query("Beacons", columnsQuery1, null, null, null, null, null);
        cursorQuery1.moveToFirst();
        Log.i("Query Count: ", String.valueOf(cursorQuery1.getCount()));
        if(cursorQuery1.getCount() != 0) {
            do {
                Log.i("LocationID", cursorQuery1.getString(4));
                String[] columnsQuery2  = {"LocationID", "LocationName", "LocationAddressLine1", "LocationAddressLine2", "LocationPostcode", "LocationOpeningHours", "LocationLatitude", "LocationLongitude"};
                String[] whereArgsQuery2 = {cursorQuery1.getString(4)};
                Cursor cursorQuery2 = db.query("Locations", columnsQuery2, "LocationID = ?", whereArgsQuery2, null, null, null);
                cursorQuery2.moveToFirst();

                if(cursorQuery2.getCount() != 0) {
                    do {
                         return new LocationInformation(String.valueOf(cursorQuery2.getLong(0)), cursorQuery2.getString(1), cursorQuery2.getString(2), cursorQuery2.getString(3), cursorQuery2.getString(4), cursorQuery2.getString(5), cursorQuery2.getString(6), cursorQuery2.getString(7));
                    } while (cursorQuery2.moveToNext());
                }
                Log.i("LocationB", "Macssss");
            } while (cursorQuery1.moveToNext());
        }
        return null;
    }
}
