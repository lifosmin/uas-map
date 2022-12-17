package id.ac.umn.uas.api

import android.content.Context
import okhttp3.OkHttpClient
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