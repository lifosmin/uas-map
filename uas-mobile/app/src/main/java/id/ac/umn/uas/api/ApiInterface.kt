package id.ac.umn.uas.api

import id.ac.umn.uas.models.DefaultResponse
import id.ac.umn.uas.models.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("user/signup")
    fun registerUser(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("user/signin")
//    if login info is correct call loginresponse, else call default response
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("user/create_job")
//    if login info is correct call loginresponse, else call default response
    fun createJob(
        @Field("job_title") job_title: String,
        @Field("job_desc") job_desc: String,
        @Field("job_date") job_date: String,
        @Field("job_price") job_price: Integer,
        @Header("Authorization") token: String,
    ): Call<LoginResponse>


    @GET("user")
    fun getUser(): Call<DefaultResponse>
}