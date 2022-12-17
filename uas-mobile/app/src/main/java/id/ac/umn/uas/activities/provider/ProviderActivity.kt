package id.ac.umn.uas.activities.provider

import id.ac.umn.uas.activities.adapter.JobProviderAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.activities.User.ProfileActivity
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.GetJobResponse
import id.ac.umn.uas.models.User
import retrofit2.Call
import retrofit2.Response

class ProviderActivity: AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var sp: SharedPreferences
    private var adapter: JobProviderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_provider)

        var profile = findViewById<CircleImageView>(R.id.profile_image)

        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

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
                    Toast.makeText(this@ProviderActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetJobResponse>,
                    response: Response<GetJobResponse>
                ) {
                    if(response.code() == 200) {
                        val job = response.body()

                        val gson = Gson()
                        val json = gson.toJson(job)

                        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("applicant", json)
                        editor.commit()

                        init()

                        recyclerView = findViewById(R.id.providerRecyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@ProviderActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            })

        val userImage = findViewById<CircleImageView>(R.id.profile_image)
        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        welcomeHead.text = "Welcome Back, ${userObj?.nama}"

        val imageUrl = userObj?.image

        Glide.with(applicationContext)
            .load(imageUrl)
            .into(userImage)
    }

    private fun init() {
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        var job = sp.getString("applicant", null)

        val jobObj = Gson().fromJson(job, GetJobResponse::class.java)

        adapter = JobProviderAdapter(jobObj.job, this)
    }
}
