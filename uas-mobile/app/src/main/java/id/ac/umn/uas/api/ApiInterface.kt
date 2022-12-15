package id.ac.umn.uas.api

import android.net.Uri
import id.ac.umn.uas.models.DefaultResponse
import id.ac.umn.uas.models.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Multipart
    @POST("user/signup")
    fun registerUser(
        @Part image: MultipartBody.Part,
        @Part("nama") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("tanggal_lahir") tanggal_lahir: RequestBody,
        @Part("jenis_kelamin") jenis_kelamin: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("no_telp") no_telp: RequestBody
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
    fun getUser(

    ): Call<DefaultResponse>

    @GET("job")
    fun getJob(

    ): Call<DefaultResponse>
}