package nsa.welshbeacons;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.MessageFormat;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context applicationContext) {
        super(applicationContext, databaseName, null, 1);
    }

    private static final String databaseName = "welshbeacons_db_new.db";
    private static final String databaseLocation = "/data/data/nsa/welshbeacons";
    private Context applicationContext;
    private SQLiteDatabase database;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String databaseCreate = "";
        db.execSQL("CREATE TABLE `Locations` (`LocationID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `LocationName` TEXT NOT NULL, `LocationAddressLine1` TEXT NOT NULL, `LocationAddressLine2` TEXT NOT NULL, `LocationPostcode` TEXT NOT NULL, `LocationOpeningHours` TEXT NOT NULL, `LocationLongitude` TEXT NOT NULL, `LocationLatitude` TEXT NOT NULL);");
        db.execSQL("CREATE TABLE `Beacons` (`BeaconID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `BeaconGUID` TEXT NOT NULL, `BeaconMajor` TEXT NOT NULL, `BeaconMinor` TEXT NOT NULL, `LocationID` INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
