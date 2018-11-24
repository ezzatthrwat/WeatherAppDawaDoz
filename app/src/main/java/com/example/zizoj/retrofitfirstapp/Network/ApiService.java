package com.example.zizoj.retrofitfirstapp.Network;


import com.example.zizoj.retrofitfirstapp.Network.model.Weathers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {


    @GET
    Call<Weathers> methods1(@Url String city);


}
