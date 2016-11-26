package com.example.dblocationmap;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.LocationListener;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDb;// this is for the db
    LocationManager mLocationManager;
    Location mLocation;
    TextView tv;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    Button button;
    Button submit;
    Button showButton;
    EditText editId;
    Button buttonUpdate;
    Button buttonDelete;
    public static double lat;
    public static double lon;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean checkAndRequestPermissions(){
        int coarse = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fine = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int internet = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (coarse != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);}
        if (fine != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);}
        if (internet != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This is why the app had a null pointer exception
        //because i didn't initialized the mydb now it works
        myDb = new DataBaseHelper(this);
        checkAndRequestPermissions();

        //Beginning of the code for finding my location
        tv = (TextView) findViewById(R.id.tv1);
        tv2= (TextView) findViewById(R.id.tv2);
        tv3= (TextView) findViewById(R.id.tv3);
        tv4= (TextView) findViewById(R.id.tv4);
        button = (Button) findViewById(R.id.button);
        submit = (Button) findViewById(R.id.submitbutton);
        //editText = (EditText) findViewById(R.id.editText);
        editId = (EditText) findViewById(R.id.editId);
        buttonUpdate = (Button) findViewById(R.id.updateButton);
        buttonDelete = (Button) findViewById(R.id.deleteButton);
        showButton = (Button) findViewById(R.id.showbutton);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String locationProvider = mLocationManager.getBestProvider(criteria, true);
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

        //start coding about the providers list
        List <String> providers = mLocationManager.getProviders(true);
        StringBuilder mSB = new StringBuilder("Providers:\n");
        tv.setText(" "+ providers);

        for (int i = 0 ; i < providers.size() ; i++){
            mLocationManager.requestLocationUpdates(providers.get(i), 5000, 2.0f, new LocationListener() {

                //These methods are required
                @Override
                public void onLocationChanged(Location location) {
                    //this is used to show location updates
                    mLocation = location;
                    showupdate();
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
                //this is used to show location updates
                public void showupdate(){
                    lat = mLocation.getLatitude();
                    lon = mLocation.getLongitude();
                    tv4.setText("Last location lat: "+lat + " long: "+lon);
                }
            });


            mSB.append(providers.get(i)).append(": \n");
            mLocation = mLocationManager.getLastKnownLocation(providers.get(i));
            if(mLocation!=null){
                mSB.append(mLocation.getLatitude()).append(" , ");
                mSB.append(mLocation.getLongitude()).append("\n");
            }else {
                mSB.append("Location cannot be found \n");
            }

        }
        tv2.setText(mSB.toString());

    //Database actions

        //Create an on click listener to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //old start
                // String myText = editText.getText().toString();
                //Toast.makeText(MainActivity.this, ""+myText,Toast.LENGTH_LONG).show();
                // old end
                //new start
                String latitude = Double.toString(lat);
                String longitude = Double.toString(lon);
                //new end
                //old start
                //boolean isInserted = myDb.insertData(myText);
                //if(isInserted == true)
                //    Toast.makeText(MainActivity.this, "Data inserted successfully!", Toast.LENGTH_LONG).show();
                //else
                //    Toast.makeText(MainActivity.this, "Data doesn't inserted. Please try again.", Toast.LENGTH_LONG).show();
                //old end
                //new start
                boolean isInserted = myDb.insertData(latitude,longitude);
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data inserted successfully!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data doesn't inserted. Please try again.", Toast.LENGTH_LONG).show();
                //new end
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                //if the res is 0 means that no data were found in the database
                if (res.getCount() == 0) {
                    showMessage("Error","Nothing found!");
                    return;
                }else {   //i use this to show the data
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        //i put the result in the buffer
                        buffer.append("Id: " + res.getString(0) + "\n");
                        buffer.append("Latitude: " + res.getString(1) + "\n");
                        buffer.append("Longitude: " + res.getString(2) + "\n\n");
                        showMessage("Data",buffer.toString());
                    }
                }
            }
        });
        //new end

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = myDb.deleteData(editId.getText().toString());
                if(deleteRows != 0)
                    Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data doesn't deleted. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = Double.toString(lat);
                String longitude = Double.toString(lon);

                boolean isUpdate = myDb.updateData(editId.getText().toString(),latitude,longitude);
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data doesn't updated. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }





}
