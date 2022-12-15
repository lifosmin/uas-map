package id.ac.umn.uas.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.DefaultResponse
import id.ac.umn.uas.models.GetJobResponse
import id.ac.umn.uas.models.User
import retrofit2.Call
import retrofit2.Response

class SeekerActivity: AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_seeker)

        apiClient = ApiClient()

//        fetchToken() from sessionManager
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

//        check if sharepreferences myPrefs exist
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

//        get job data from api
        apiClient.getApiInterface(this).getJob()
            .enqueue(object: retrofit2.Callback<GetJobResponse> {
                override fun onFailure(call: Call<GetJobResponse>, t: Throwable) {
                    Toast.makeText(this@SeekerActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetJobResponse>,
                    response: Response<GetJobResponse>
                ) {
                    if(response.code() == 200) {
                        val job = response.body()?.job
                        val dataJob = job
                    }
                }
            })

        val userImage = findViewById<CircleImageView>(R.id.profile_image)
        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        welcomeHead.text = "Welcome back, ${userObj?.nama}"

//        change userImage with userObj image upload bitmap
        

    }
}
