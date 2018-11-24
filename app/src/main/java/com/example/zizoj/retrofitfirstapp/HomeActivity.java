package com.example.zizoj.retrofitfirstapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zizoj.retrofitfirstapp.Network.ApiClient;
import com.example.zizoj.retrofitfirstapp.Network.ApiService;
import com.example.zizoj.retrofitfirstapp.Network.model.Current;
import com.example.zizoj.retrofitfirstapp.Network.model.Forecastday;
import com.example.zizoj.retrofitfirstapp.Network.model.Weathers;
import com.example.zizoj.retrofitfirstapp.RoomDatabase.Converters;
import com.example.zizoj.retrofitfirstapp.RoomDatabase.WeatherDatabase;
import com.example.zizoj.retrofitfirstapp.RoomDatabase.WeatherDatabaseModel;
import com.example.zizoj.retrofitfirstapp.Utils.Config;
import com.example.zizoj.retrofitfirstapp.Utils.ForecastingListItemAdapter;
import com.example.zizoj.retrofitfirstapp.Utils.ModifyString;
import com.example.zizoj.retrofitfirstapp.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "weathercity_db";
    private WeatherDatabase weatherDatabase;
    WeatherDatabaseModel weatherDatabaseModel;


    ApiService apiService;

    //list data
    ArrayList<Current> CurrentweathersList = new ArrayList<Current>();
    ArrayList<Forecastday> ForcastingweathersList = new ArrayList<Forecastday>();
    List<WeatherDatabaseModel> weatherlist ;

    TextView Temp , TempDate , TempMin , TempMax , ConditionTextView , CityName;
    ImageView WeatherIcon;
    RecyclerView ForecastRecyclerView ;
    CoordinatorLayout layout ;

    String time;
    String name  = null;

    boolean connected = false;


    SharedPreferences.Editor editor ;

    String MY_PREFS_NAME = "checkCity";

    String MY_PREFS_NAME_2 = "prefwidgetapp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);


//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME_2, MODE_PRIVATE);
//        name = prefs.getString("citynamewhenoff" , "aswan");

            setupWidget();
            initImageLoader();
            initRetrofit();
            getCurrentTime();
            getCityWeather();
            initRoomDatabase();

        if (!internetConnection()){

            if (name != null){

               weatherlist = weatherDatabase.daoAccess().fetchOneMoviesbyMovieId(name);

                if (weatherlist.size() > 0 ) {

                    CityName.setText(weatherlist.get(0).getCity_name() );
                    Temp.setText(weatherlist.get(0).getTemp() + (char) 0x00B0);
                    TempDate.setText(ModifyString.showOnlyDate(weatherlist.get(0).getTemp_date()));
                    TempMin.setText(weatherlist.get(0).getTemp_min() + (char) 0x00B0);
                    TempMax.setText(weatherlist.get(0).getTemp_max() + (char) 0x00B0);
                    ConditionTextView.setText(weatherlist.get(0).getCondition());

                    UniversalImageLoader.setImage("http:" + weatherlist.get(0).getImgurl(), WeatherIcon, null, "");


                    ArrayList<Forecastday> forecastdays  = Converters.fromString(weatherlist.get(0).getForcastlist());

                    ForecastingListItemAdapter adapter = new ForecastingListItemAdapter( forecastdays.subList(1,forecastdays.size()));

                    ForecastRecyclerView.setAdapter(adapter);

                    setLayoutBackground(forecastdays);

                    editor = getSharedPreferences(MY_PREFS_NAME_2, MODE_PRIVATE).edit();
                    editor.putString("cityname", weatherlist.get(0).getCity_name());
                    editor.putString("temp" , weatherlist.get(0).getTemp());
                    editor.commit();


                }

            }

        }



    }

    public void initRoomDatabase(){
        weatherDatabase = Room.databaseBuilder(getApplicationContext(),
                WeatherDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }


    private void getCurrentTime(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm a");
        time = mdformat.format(currentTime);

    }


    private void getCityWeather(){

//        Intent intent = getIntent();
//        name = intent.getStringExtra("CityName");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        name = prefs.getString("citynamewhenoff" , "aswan");
        getweather(name);

    }


    private void setupWidget(){

        Temp = findViewById(R.id.TvCurrentTemp);
        TempDate = findViewById(R.id.WeatherTime);
        TempMin = findViewById(R.id.WeatherTempMin);
        TempMax = findViewById(R.id.WeatherTempMax);
        ConditionTextView = findViewById(R.id.ConditionTv);
        WeatherIcon = findViewById(R.id.Weather_Icon);
        CityName = findViewById(R.id.tvCityName);

        layout = findViewById(R.id.MainLay);


        ForecastRecyclerView = findViewById(R.id.forcastingRecycler);
        ForecastRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ForecastRecyclerView.setLayoutManager(layoutManager);

    }


    private void initRetrofit(){
        apiService = ApiClient.getClient(getApplication()).create(ApiService.class);
    }



    public void getweather(String City){



        String url = Config.BASE_URL+"/v1/forecast.json?key=33207495df6b4a9c8ee55950181711&q="+City+"&days=5";

        Call<Weathers> call = apiService.methods1(url);

        call.enqueue(new Callback<Weathers>() {
            @Override
            public void onResponse(Call<Weathers> call, Response<Weathers> response) {

                CurrentweathersList.add(response.body().getCurrent());
                ForcastingweathersList.addAll(response.body().getForecast().getForecastday());

                final String cityName = response.body().getLocation().getName();
                final String temp =  CurrentweathersList.get(0).getTempC().toString();
                final String tempDate = ModifyString.showOnlyDate(CurrentweathersList.get(0).getLastUpdated());
                final String tempMin = ForcastingweathersList.get(0).getDay().getMintempC().toString();
                final String tempMax = ForcastingweathersList.get(0).getDay().getMaxtempC().toString();
                final String Condition = CurrentweathersList.get(0).getCondition().getText() ;
                final String imgURL = CurrentweathersList.get(0).getCondition().getIcon();

                editor = getSharedPreferences(MY_PREFS_NAME_2, MODE_PRIVATE).edit();
                editor.putString("cityname", response.body().getLocation().getName());
                editor.putString("temp" , CurrentweathersList.get(0).getTempC().toString());
                editor.commit();

                // setup data to widget
                ForecastingListItemAdapter adapter = new ForecastingListItemAdapter( ForcastingweathersList.subList(1,ForcastingweathersList.size()));
                ForecastRecyclerView.setAdapter(adapter);

                CityName.setText(cityName );
                Temp.setText(temp  + (char) 0x00B0);
                TempDate.setText(tempDate );
                TempMin.setText(tempMin + (char) 0x00B0);
                TempMax.setText(tempMax + (char) 0x00B0);
                ConditionTextView.setText(Condition);
                UniversalImageLoader.setImage("http:"+imgURL,WeatherIcon,null,"");

                //setbackground
                setLayoutBackground(ForcastingweathersList);
                layout.setVisibility(View.VISIBLE);

                //insert in database
                weatherDatabaseModel = new WeatherDatabaseModel(cityName ,temp ,tempDate,tempMin
                        ,tempMax,Condition,imgURL,Converters.fromArrayLisr(ForcastingweathersList));

                String Cityname  = weatherDatabase.daoAccess().getCityname(weatherDatabaseModel.getCity_name());

                if (Cityname == null) {
                    weatherDatabase.daoAccess().insertOnExistsWeather(weatherDatabaseModel);
                }else {
                    weatherDatabase.daoAccess().updateweather(weatherDatabaseModel);
                }



            }

            @Override
            public void onFailure(Call<Weathers> call, Throwable t) {



            }
        });

    }


    public void setLayoutBackground(ArrayList<Forecastday> forCastDay){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        Date Sunrisedate = null, Sunsetdate = null, Moonrisedate = null , Moonsetdate = null ;

        try {
            Sunrisedate = simpleDateFormat.parse(forCastDay.get(0).getAstro().getSunrise());
            Moonrisedate = simpleDateFormat.parse(forCastDay.get(0).getAstro().getMoonrise());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String Sunrise = simpleDateFormat.format(Sunrisedate);
        String Moonrise = simpleDateFormat.format(Moonrisedate);

        if (time.compareTo(Sunrise)>0 || time.compareTo(Moonrise) < 0){

            layout.setBackground(ContextCompat.getDrawable(HomeActivity.this,R.drawable.nightwall3));

        }else if(time.compareTo(Moonrise)>0 || time.compareTo(Sunrise)<0){

            layout.setBackground(ContextCompat.getDrawable(HomeActivity.this,R.drawable.sunsetwall));

        }
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


    private void initImageLoader(){

        UniversalImageLoader universalImageLoader = new UniversalImageLoader(HomeActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfiguration());

    }

    public void citiesMenuClicke(View view) {

        Intent intent = new Intent(HomeActivity.this , CitiesMenu.class);
        intent.putExtra("CityNameFromHome" , name) ;
        intent.putExtra(getString(R.string.calling_activity) , getString(R.string.home_activity));
        startActivity(intent);

        finish();

    }

    public void onContactUs(View view) {

        startActivity(new Intent(HomeActivity.this , ContactUsActivity.class));


    }
}
