package nsa.welshbeacons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CompanyInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_information);
        LocationInformation locationInformation = (LocationInformation)getIntent().getSerializableExtra("LocationInformation");
        setTitle(locationInformation.getLocationName());
        TextView locationName = (TextView)findViewById(R.id.tvwLocationName);
        TextView locationAddress = (TextView)findViewById(R.id.tvwLocationAddress);
        TextView locationOpeningHours = (TextView)findViewById(R.id.tvwLocationOpeningHours);
        locationName.setText(locationInformation.getLocationName());
        locationAddress.setText(locationInformation.getLocationAddressLine1() + " " + locationInformation.getLocationAddressLine2() + " " + locationInformation.getLocationPostcode());
        locationOpeningHours.setText(locationInformation.getLocationOpeningHours());
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
