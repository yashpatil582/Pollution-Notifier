package swaroop.com.yash;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class location extends AppCompatActivity {


    String cityname;
    TextView latTextView;
    TextView longTextView;
    TextView locTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        latTextView = (TextView) findViewById(R.id.l1);
        longTextView = (TextView) findViewById(R.id.l2);
        locTextView = (TextView) findViewById(R.id.l3);

        //TO get the location,manifest file is added with 2 permissions
        //Location Manager is used to figure out which location provider needs to be used.
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Best location provider is decided by the criteria
        Criteria criteria = new Criteria();
        //location manager will take the best location from the criteria
        if (locationManager != null) {
            locationManager.getBestProvider(criteria, true);
        }

        //nce you know the name of the LocationProvider, you can call getLastKnownPosition() to find out where you were recently.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = null;
        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        }

        Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
        if (location != null) {
            latTextView.setText(String.valueOf(location.getLatitude()));
        }
        if (location != null) {
            longTextView.setText(String.valueOf(location.getLongitude()));
        }
        Log.d("Tag","1");
              List<Address> addresses;

                try {
                  addresses=gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                  if(addresses.size()>0)

                      {
                         //while(locTextView.getText().toString()=="Location") {
                           cityname = addresses.get(0).getLocality().toString();
                           locTextView.setText(cityname);
                           // }
                         }

                    } catch (IOException e) {
                      e.printStackTrace();

                    }


              }

  }