package id.ac.umn.uas.api

import id.ac.umn.uas.models.*
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
    fun getUser(): Call<DefaultResponse>

    @GET("user/available/jobs")
    fun getJob(): Call<GetJobResponse>

    @FormUrlEncoded
    @POST("user/apply_job")
    fun applyJob(
        @Field("job_id") job_id: String,
    ): Call<ApplyJobResponse>

    @GET("user/applied_job")
    fun getAppliedJob(): Call<GetJobResponse>

    @GET("user/count_applied_job")
    fun countAppliedJob(): Call<AppliedJobCountResponse>

    @FormUrlEncoded
    @POST("user/cancel_job")
    fun cancelJob(
        @Field("job_id") job_id: String,
    ): Call<CancelJobResponse>

//    get applicant with params job_id
    @GET("jobs/applicant/user")
    fun getApplicant(
        @Query("job_id") job_id: String,
    ): Call<GetJobApplicant>
}