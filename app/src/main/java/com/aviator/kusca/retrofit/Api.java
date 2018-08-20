package com.aviator.kusca.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Aviator on 11/22/2017.Tranquilizer
 */

public interface Api {
    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
    //localhost:9190/kusca-realajax/test.php
    String BASE_URL = "http://192.168.42.177:9190/kusca-realajax//";

    //this is our multipart request
    //we have two parameters on is name and other one is description
    //@Multipart
    //@POST("Api.php?apicall=upload")
    @POST("test.php")
    Call<Seed>  getData();
   // Call<MyResponse> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc);


}
