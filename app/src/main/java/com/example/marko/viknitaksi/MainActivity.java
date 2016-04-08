package com.example.marko.viknitaksi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

      static double latituda=0;
      static double longituda=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //oderduvanje na lokacija so pojma nemam kako rab
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener();

        //proveruva permisi za gps i za android 6.0
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
            //populateListView();
            //praj onclikc funkcija za sekoj item od listata
            registerClickCallback();
            vnesNaPodatoci();

    }


    public class MyCurrentLoctionListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            latituda=location.getLatitude();
            longituda=location.getLongitude();
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
        int i = 0;
        String myLocation = "Непозната позиција, не сте лоцирани!";

        //proverka na koja lokaciaj e za da popolni niza
            ArrayList<String> br = null;
            if (((latituda < 41.37) && (latituda > 41.31)) && ((longituda < 21.58) && (longituda > 21.52))) {
                myLocation = "Прилеп";
                DBHandler db = new DBHandler(this);
                br = db.getBrojoj(myLocation);
            }
            if ((latituda <= 41.08 && latituda >= 40.98) && (longituda <= 21.38 && longituda >= 21.28)) {
                myLocation = "Битола";
                DBHandler db = new DBHandler(this);
                br = db.getBrojoj(myLocation);
        }


        //I make a log to see the results
        TextView labela = (TextView) findViewById(R.id.textView);
        labela.setText(myLocation);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, br);

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
                callIntent.setData(Uri.parse("tel:" + textView.getText().toString()));
                startActivity(callIntent);
            }
        });
    }
    public void locriajKlik(View v){
        populateListView();
        registerClickCallback();
    }
    public void vnesNaPodatoci(){
        DBHandler db = new DBHandler(this);

        //prvo vnesuvam deka ima bazata podatoci
        db.addInfo(new Info("1", "1", "1", "1"));
        //insert prilep
        db.addInfo(new Info("100", "Прилеп", "Дени", "1595"));
        db.addInfo(new Info("101", "Прилеп", "Милениум", "1573"));
        db.addInfo(new Info("102", "Прилеп", "Пет", "1555"));
        db.addInfo(new Info("103", "Прилеп", "Лион", "1577"));
        //insert Bitola
        db.addInfo(new Info("104", "Битола", "Цагер", "1586"));
        db.addInfo(new Info("105", "Битола", "Теа", "1554"));
        db.addInfo(new Info("106", "Битола", "Монгол", "1592"));
        db.addInfo(new Info("107", "Битола", "Гонзалес", "1577"));
        db.addInfo(new Info("108", "Битола", "Фаворит", "1591"));
        db.addInfo(new Info("109", "Битола", "Пелфи", "1593"));
        db.addInfo(new Info("110", "Битола", "Алфа", "1595"));

    }
}