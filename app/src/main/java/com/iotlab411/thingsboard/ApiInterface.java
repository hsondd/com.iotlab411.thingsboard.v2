package com.iotlab411.thingsboard;

import com.iotlab411.thingsboard.Model.ModelTemp;
import com.iotlab411.thingsboard.Model.ModelToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    String URL_BASE = "http://demo.thingsboard.io/api/";

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<ModelToken> login(@Body String body);

    //ss1
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/993a75f0-bcd6-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getLastTemp1(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/993a75f0-bcd6-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getTemp1(@Query("keys") String keys,@Query("startTs") Long startTs,
                              @Query("endTs") Long endTs,
                              @Query("interval") Long interval,
                              @Query("limit") Long limit,
                              @Query("agg") String agg,
                              @Header("X-Authorization") String auth);

    //ss2
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/6b877d90-bc3d-11ea-a0ad-19f7b95d9654/values/timeseries")
    Call<ModelTemp>  getLastTemp2(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/6b877d90-bc3d-11ea-a0ad-19f7b95d9654/values/timeseries")
    Call<ModelTemp>  getTemp2(@Query("keys") String keys,@Query("startTs") Long startTs,
                              @Query("endTs") Long endTs,
                              @Query("interval") Long interval,
                              @Query("limit") Long limit,
                              @Query("agg") String agg,
                              @Header("X-Authorization") String auth);

    //ss3
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/154204b0-bb74-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getLastTemp11(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/154204b0-bb74-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getTemp11(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);

    //ss4
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/0ed2fc00-bc38-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getLastTemp12(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/0ed2fc00-bc38-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getTemp12(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);

    //ss5
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/33d05330-bc39-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getLastTemp13(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/33d05330-bc39-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getTemp13(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);

    //ss6
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/90999980-bb73-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getLastTemp14(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/90999980-bb73-11ea-b4ad-47e5929eed78/values/timeseries")
    Call<ModelTemp>  getTemp14(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);

    //p1sn061
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/5ce54cd0-7e31-11ea-8142-b3576b7d39f1/values/timeseries")
    Call<ModelTemp>  getLastTemp21(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/5ce54cd0-7e31-11ea-8142-b3576b7d39f1/values/timeseries")
    Call<ModelTemp>  getTemp21(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);

    //p1sn062
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/5df81c60-7e31-11ea-b382-7d0ef2a682d3/values/timeseries")
    Call<ModelTemp>  getLastTemp22(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/5df81c60-7e31-11ea-b382-7d0ef2a682d3/values/timeseries")
    Call<ModelTemp>  getTemp22(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);

    //p1sn010
    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/de74a2c0-11b5-11ea-bd5a-9f3eedcb469c/values/timeseries")
    Call<ModelTemp>  getLastTemp23(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/de74a2c0-11b5-11ea-bd5a-9f3eedcb469c/values/timeseries")
    Call<ModelTemp>  getTemp23(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);


    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/8255e800-a6de-11ea-af28-555c475a54cd/values/timeseries")
    Call<ModelTemp>  getLastTemp24(@Query("keys") String keys, @Header("X-Authorization") String auth);



    ////////////////////////////////////////////////////////////////////

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/8255e800-a6de-11ea-af28-555c475a54cd/values/timeseries")
    Call<ModelTemp>  getTemp24(@Query("keys") String keys,@Query("startTs") Long startTs,
                               @Query("endTs") Long endTs,
                               @Query("interval") Long interval,
                               @Query("limit") Long limit,
                               @Query("agg") String agg,
                               @Header("X-Authorization") String auth);


    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/14e3aec1-2f47-11e9-860e-a5f248c1bbee/values/timeseries")
    Call<ModelTemp> getLastTemp3(@Query("keys") String keys, @Header("X-Authorization") String auth);

    @Headers({ "Content-Type: application/json"})
    @GET("plugins/telemetry/DEVICE/14e3aec1-2f47-11e9-860e-a5f248c1bbee/values/timeseries")
    Call<ModelTemp>  getTemp3(@Query("keys") String keys,@Query("startTs") Long startTs,
                                  @Query("endTs") Long endTs,
                                  @Query("interval") Long interval,
                                  @Query("limit") Long limit,
                                  @Query("agg") String agg,
                                  @Header("X-Authorization") String auth);
}
