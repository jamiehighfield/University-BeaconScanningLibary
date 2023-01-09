package nsa.welshbeacons;

import android.bluetooth.BluetoothClass;

public class MajorMinorPair {

    public MajorMinorPair(String major, String minor) {
        this.major = major;
        this.minor = minor;
    }

    private String major;
    private String minor;

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }
}
