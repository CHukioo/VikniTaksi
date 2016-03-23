package com.example.marko.viknitaksi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  public static class globalVar{
      public static double latituda=0;
      public static double longituda=0;
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //oderduvanje na lokacija so pojma nemam kako rab
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        // ja kreira i ispisuva listata
        populateListView();
        //praj onclikc funkcija za sekoj item od listata
        registerClickCallback();
    }

    public class MyCurrentLoctionListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            globalVar.latituda=location.getLatitude();
            globalVar.longituda=location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }



    private void populateListView() {

        String[] brojoj= new String[]{"nemapodatok"};

        //proverka na koja lokaciaj e za da popolni niza
        if(((globalVar.latituda<41.37)&&(globalVar.latituda>41.31))&&((globalVar.longituda<21.58)&&(globalVar.longituda>21.52))){
            brojoj = new String[]{"1555", "1573", "1595"};
        }
        else {
            if ((globalVar.latituda <= 41.08 && globalVar.latituda >= 40.98) && (globalVar.longituda <= 21.38 && globalVar.longituda >= 21.28)) {
                brojoj = new String[]{"1444", "1462", "1484"};
            }
            else {
                brojoj = new String[]{"samoPrilepNekaStoj"};
            }
        }

        String myLocation = "Latitude = " + globalVar.latituda + " Longitude = " + globalVar.longituda;

        //I make a log to see the results
        TextView labela = (TextView) findViewById(R.id.textView);
        labela.setText(myLocation);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, brojoj);

        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setAdapter(adapter);

    }

    private void registerClickCallback() {
        ListView list= (ListView) findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewCliced, int position, long id) {
                TextView textView = (TextView) viewCliced;
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+textView.getText().toString()));
                startActivity(callIntent);
            }
        });
    }

}