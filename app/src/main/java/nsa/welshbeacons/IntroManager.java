package nsa.welshbeacons;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
//   Saving the intro-screens to shared preferences. To not run every time the user opens up the app.

    public IntroManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("first",0);
        editor = pref.edit();
        editor.apply();
    }

    public void setFirst(boolean isFirst){
        editor.putBoolean("check", isFirst);
        editor.commit();
    }

    public boolean Check(){
        return pref.getBoolean("check", true);

    }
}
