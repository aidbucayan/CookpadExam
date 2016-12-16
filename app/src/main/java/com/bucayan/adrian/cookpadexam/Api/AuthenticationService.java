package com.bucayan.adrian.cookpadexam.Api;

import com.bucayan.adrian.cookpadexam.Model.MediaResponse;
import com.bucayan.adrian.cookpadexam.Model.Token;
import com.bucayan.adrian.cookpadexam.Model.UserInfo;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Adrian Bucayan on 12/15/16.
 */
public interface AuthenticationService {

    @FormUrlEncoded
    @Headers({"Content-type:, application/x-www-form-urlencoded"})
    @POST("oauth/access_token/")
    Call<Token> getInstagramAccessToken(@Field("client_id") String client_id,
                                        @Field("client_secret") String client_secret,
                                        @Field("grant_type") String grant_type,
                                        @Field("redirect_uri") String redirect_uri,
                                        @Field("code") String code);


    @Headers({"Content-type:, application/x-www-form-urlencoded"})
    @GET("v1/users/{id}/")
    Call<UserInfo> getUserDetails(@Path("id") String id,
                                  @Query("access_token") String access_token);

    @Headers({"Content-type:, application/x-www-form-urlencoded"})
    @GET("v1/users/{id}/media/recent/")
    Call<MediaResponse> getUserImages(@Path("id") String id,
                                      @Query("client_id") String client_id,
                                      @Query("count") String count,
                                      @Query("access_token") String access_token);

}
