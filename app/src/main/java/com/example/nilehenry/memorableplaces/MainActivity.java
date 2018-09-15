package com.example.nilehenry.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static ArrayList places= new ArrayList<String>();
    static ArrayList<Location> locations= new ArrayList<Location>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView= (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.nilehenry.memorableplaces",Context.MODE_PRIVATE);

        ArrayList<String> latitudes= new ArrayList<String>();
        ArrayList<String> longitudes= new ArrayList<String>();

        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();
        try{
            places= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes",ObjectSerializer.serialize(new ArrayList<String>())));
            Toast.makeText(this,places.toString(),Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){

        }

        if ((places.size()>0)&&(latitudes.size()>0)&&(longitudes.size()>0)){
            if ((places.size()==latitudes.size())&&(latitudes.size()==longitudes.size())){
                for (int i=0;i<latitudes.size();i=i+1){
                    Location location= new Location(LocationManager.GPS_PROVIDER);
                    location.setLatitude(Double.parseDouble(latitudes.get(i)));
                    location.setLongitude(Double.parseDouble(longitudes.get(i)));
                    locations.add(location);
                }
            }
        }

        if (places.size()==0) {
            places.add("Add a new place..");
            locations.add(null);
        }


        arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (position==0){
                    //go to map, clear map markers, GIVE OPTION TO ADD NEW place, zoom in on users location
                    Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
                    intent.putExtra("placemarker",position);
                    startActivity(intent);
                //}
                /*else{
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("placemarker")
                }*/

            }
        });

        arrayAdapter.notifyDataSetChanged();

    }


}
