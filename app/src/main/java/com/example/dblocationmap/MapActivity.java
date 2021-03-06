package com.example.dblocationmap;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Location;
import android.location.Criteria;
import android.location.LocationManager;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    DataBaseHelper myDb;
    private GoogleMap mMap;
    LocationManager mLocationManager;
    Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        myDb = new DataBaseHelper(this);

        //Find my location code
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String locationprovider = mLocationManager.getBestProvider(criteria, true);
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
        mLocation = mLocationManager.getLastKnownLocation(locationprovider);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mylocation = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());



        mMap.addMarker(new MarkerOptions().position(mylocation).title("I am here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));

        //Load the items of the database
        Cursor res = myDb.getAllData();
        //if the res is 0 means that no data were found in the database
        if (res.getCount() == 0) {
            Toast.makeText(MapActivity.this, "Error,Nothing found!", Toast.LENGTH_LONG).show();
            return;
        }else {   //i use this to show the data
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                //i put the result in the buffer
                buffer.append("Id: " + res.getString(0) + "\n");
                buffer.append("Latitude: " + res.getString(1) + "\n");
                buffer.append("Longitude: " + res.getString(2) + "\n\n");
                Double latitude= res.getDouble(1);
                Double longitude = res.getDouble(2);
                LatLng position = new LatLng(latitude,longitude);
                Toast.makeText(MapActivity.this, " "+latitude+" "+longitude, Toast.LENGTH_LONG).show();
                mMap.addMarker(new MarkerOptions().position(position).title("I was here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            }
        }

    }
}



