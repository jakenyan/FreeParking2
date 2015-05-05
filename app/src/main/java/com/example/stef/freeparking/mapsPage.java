 package com.example.stef.freeparking;


import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.security.Provider;


 public class mapsPage extends FragmentActivity implements OnMapReadyCallback {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_page);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

     private Location getMyLocation (String provider) {
        Location myLoc = null;
        LocationManager locationManager= (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            if( locationManager.isProviderEnabled(provider))
                myLoc = locationManager.getLastKnownLocation(provider);
        }
        catch (IllegalArgumentException e){

        }
        return myLoc;
     }



     public void onMapReady(GoogleMap map) {
        Location me= getMyLocation(LocationManager.GPS_PROVIDER);
        LatLng myPosition = new LatLng( me.getLatitude(), me.getLongitude());
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( me.getLatitude(), me.getLongitude()), 13));
        markMap(map);
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

             @Override
             public void onInfoWindowClick(Marker marker) {
                 if(marker.getTitle().equals("SJU")){
                    Intent intent = new Intent(mapsPage.this, sju.class);
                    startActivity(intent);
                 }
                 else {
                     Intent intent = new Intent(mapsPage.this, drexel.class);
                     startActivity(intent);
                 }


         }
     });
     }
     public void markMap(GoogleMap map){

         //sju
         LatLng mandevilleA = new LatLng (39.996408, -75.235913);
         Marker sju = map.addMarker(new MarkerOptions()
                 .title("SJU")
                 .snippet("Saint Joseph's University")
                 .position(mandevilleA));

         //drexel
         LatLng drexelA = new LatLng (39.955594, -75.191801);
         Marker drexel = map.addMarker(new MarkerOptions()
                 .title("Drexel University")
                 .snippet("Drexel University Parking Lot A")
                 .position(drexelA));


     }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
