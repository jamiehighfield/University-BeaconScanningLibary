package nsa.welshbeacons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChangeLangActivity extends AppCompatActivity {

    private String LANG_CURRENT = "en";
    Button goNextScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_change);

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LANG_CURRENT.equals("en")) {
                    changeLang(ChangeLangActivity.this, "cy");
                } else {
                    changeLang(ChangeLangActivity.this, "en");
                }
                finish();
                startActivity(new Intent(ChangeLangActivity.this, TabbedActivity.class));
            }
        });


    }

    public void changeLang(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language", lang);
        editor.apply();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences.getString("Language", "en");

        super.attachBaseContext(AppContextWrapper.wrap(newBase, LANG_CURRENT));
    }
}