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
    @Headers("Accept: application/json")
    @POST("user/update")
    fun updateUser(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tanggal_lahir") tanggal_lahir: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
        @Field("alamat") alamat: String,
        @Field("no_telp") no_telp: String
    ): Call<UpdateUserResponse>

    @Multipart
    @POST("user/create_job")
    fun createJob(
        @Part image: MultipartBody.Part,
        @Part("judul") judul: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("gaji") gaji: RequestBody,
        @Part("lokasi") lokasi: RequestBody,
    ): Call<CreateJobResponse>

    @FormUrlEncoded
    @POST("user/signin")
//    if login info is correct call loginresponse, else call default response
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
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

    @FormUrlEncoded
    @POST("user/accept_job")
    fun acceptJob(
        @Field("job_id") job_id: String,
        @Field("user_id") user_id: String,
        @Field("action") action: String,
    ): Call<ApproveHiringResponse>

    @GET("jobs/applicant")
    fun getJobApplicant(): Call<GetJobResponse>

    @POST("token/logout")
    fun logoutUser(): Call<LogoutResponse>
}