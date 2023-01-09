package nsa.welshbeacons;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.google.android.gms.maps.model.Marker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.*;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

import dalvik.annotation.TestTargetClass;

@RunWith(AndroidJUnit4.class)
public class MapTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void onInfoWindowClick(Marker marker) {
        MapsActivity m = (MapsActivity) mActivityRule.getActivity();
        Map<Marker, LocationInformation> map = m.getMarkerMap();
        assertEquals(map.get(0).getLocationLongitude(), 51.484202);


//        loop through the map and and check that the Longitude equals the one above
    }

    @Test
    public void useAppContext() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("nsa.welshbeacons", appContext.getPackageName());
    }

    @Test
    public void checkMarker() throws Exception {

        checkMarker(map.get(0),getLocationLongitude(), 51.484202);

    }

}

