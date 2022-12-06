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

    @GET("user")
    fun getUser(): Call<DefaultResponse>
}