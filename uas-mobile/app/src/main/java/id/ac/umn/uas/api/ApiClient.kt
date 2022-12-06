package id.ac.umn.uas.api

import android.content.Context
import android.provider.SyncStateContract
import id.ac.umn.uas.activities.LoginActivity
import id.ac.umn.uas.activities.SignupActivity
import id.ac.umn.uas.models.LoginResponse
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var apiInterface: ApiInterface

    fun getApiInterface(context: Context): ApiInterface {
        if (!::apiInterface.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            apiInterface = retrofit.create(ApiInterface::class.java)
        }

        return apiInterface
    }

    /**
     * Initialize OkhttpClient with our interceptor
     */
    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

}