package id.ac.umn.uas.api

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    get token from session manager
    const val BASE_URL = "http://10.0.2.2:8000/api/"
}