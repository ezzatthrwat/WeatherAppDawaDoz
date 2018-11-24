package com.example.zizoj.retrofitfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CitiesMenu extends AppCompatActivity {

    ListView LV;
    ArrayAdapter adapter ;

    String[] Cities = {"Cairo","Alexandria","Aswan","London","Paris"};

    boolean connected = false;

    String s;

   String MY_PREFS_NAME = "checkCity";

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citiesmenu);

        initListview();

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


        Intent intent2 = getIntent();
        if (intent2.hasExtra(getString(R.string.calling_activity))){
            s = intent2.getStringExtra("CityNameFromHome");
        }else {
            if (prefs.getBoolean("checked" , false)){
                startActivity(new Intent(CitiesMenu.this , HomeActivity.class));
                finish();
            }
        }
    }

    public void finishTVClick(View view) {

        if (s != null){
            Intent intent = new Intent(CitiesMenu.this,HomeActivity.class);
            intent.putExtra("CityName" , s ); // default
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "you should select city", Toast.LENGTH_SHORT).show();
        }

    }

    private void initListview(){

        LV = findViewById(R.id.CitiesList);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1 , Cities) ;
        LV.setAdapter(adapter);




        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("citynamewhenoff", Cities[i]);
                editor.putBoolean("checked" , true);
                editor.commit();

               Intent intent = new Intent(CitiesMenu.this,HomeActivity.class);
               intent.putExtra("CityName" , Cities[i] ); // default
                startActivity(intent);

                finish();


            }
        });


    }

    private void hereWeGo(String name){

        Intent intent = new Intent(CitiesMenu.this,HomeActivity.class);
        intent.putExtra("CityName" , name);
        startActivity(intent);
        finish();
    }

    public boolean internetConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return connected = true;
        }
        else{
            return connected = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (s != null){
            Intent intent = new Intent(CitiesMenu.this,HomeActivity.class);
            intent.putExtra("CityName" , s ); // default
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "you should select city", Toast.LENGTH_SHORT).show();
        }
    }
}
