package com.hqxh.fiamproperty.constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ljd on 3/25/16.
 */
public interface HttpApi {
    @GET("common/api")
    Call<ResponseBody> rel(@Query("data") String data);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody>  login(@Field("loginid") String loginid, @Field("password") String password, @Field("imei") String imei);

}
