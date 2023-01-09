package nsa.welshbeacons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstRun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ImageView arrow = (ImageView) findViewById(R.id.nextArrow);
        arrow.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void changeToWelsh(View view) {
        String text = getResources().getString(R.string.WelshIntro);
        TextView tw = (TextView)findViewById(R.id.introText);
        tw.setText(text);
        Toast.makeText(FirstRun.this, "Language has changed to Welsh",Toast.LENGTH_LONG).show();
    }

    public void changeToEng(View view){
        String text = getResources().getString(R.string.EnglishIntro);
        TextView textView = (TextView)findViewById(R.id.introText);
        textView.setText(text);
        Toast.makeText(FirstRun.this, "Language has been changed to English", Toast.LENGTH_LONG).show();
    }
}
