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

     /* @POST("api/customer/authenticate")
    Call<Profile> authenticateUser(@Body AuthenticationCredentials credentials);

    @GET("api/config")
    Call<List<ConfigurationValue>> getConfiguration();

    @POST("api/customer/register")
    Call<Void> registerCustomer(@Body RegisterCustomerRequest registerCustomerRequest);

    @GET("api/customer")
    Call<Customer> loadCustomer(@Header("Cookie") String cookie);

    @PUT("api/customer/{customerId}")
    Call<Customer> updateCustomer(@Header("Cookie") String cookie, @Path("customerId") Long customerId, @Body Customer customer) ;

    @POST("api/customer/fundingsource/register")
    Call<Customer> registerFundingSource(@Header("Cookie") String cookie, @Body RegisterFundingSource registerFundingSource);

    @POST("api/customer/vehicle/register")
    Call<Customer> registerVehicle(@Header("Cookie") String cookie, @Body RegisterVehicleRequest registerVehicleRequest);

    @DELETE("api/customer/vehicle/{vehicleId}")
    Call<Customer> deleteVehicle(@Header("Cookie") String cookie, @Path("vehicleId") Long vehicleId);

    @POST("api/user/password/reset")
    Call<Void> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @POST("/api/customer/register/validate")
    Call<Void> validateEmail(@Body RegisterCustomerRequest registerCustomerRequest);

    @POST("/api/fundingsource/external")
    Call<UrlReturn> fundingSourceExternal(@Header("Cookie") String cookie, @Body RegisterCustomerRequest registerCustomerRequest);

    @POST("api/customer/change")
    Call<Void> changePassword(@Body ChangePasswordRequest changePasswordRequest);*/

}
