package nsa.welshbeacons;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LocationInformation implements Serializable {
    public LocationInformation(String locationID, String locationName, String locationAddressLine1, String locationAddressLine2, String locationPostcode, String locationOpeningHours, String locationLatitude, String locationLongitude) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationAddressLine1 = locationAddressLine1;
        this.locationAddressLine2 = locationAddressLine2;
        this.locationPostcode = locationPostcode;
        this.locationOpeningHours = locationOpeningHours;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    private String locationID;
    private String locationName;
    private String locationAddressLine1;
    private String locationAddressLine2;
    private String locationPostcode;
    private String locationOpeningHours;
    private String locationLatitude;
    private String locationLongitude;

    public String getLocationID() {
        return locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationAddressLine1() {
        return locationAddressLine1;
    }

    public String getLocationAddressLine2() {
        return locationAddressLine2;
    }

    public String getLocationPostcode() {
        return locationPostcode;
    }

    public String getLocationOpeningHours() {
        return locationOpeningHours;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }
}
