package id.ac.umn.uas.activities.provider

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.ac.umn.uas.R
import id.ac.umn.uas.activities.adapter.JobProviderAdapter
import id.ac.umn.uas.activities.adapter.JobProviderApplicantAdapter
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.GetJobApplicant
import id.ac.umn.uas.models.GetJobResponse
import id.ac.umn.uas.models.User
import retrofit2.Call
import retrofit2.Response

class TrackSeekerActivity: AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var sp: SharedPreferences
    private var adapter: JobProviderApplicantAdapter? = null
    private var gson: Gson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_list)

        apiClient = ApiClient()
        gson = Gson()

        //        fetchToken() from sessionManager
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        //        check if sharepreferences myPrefs exist
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = gson?.fromJson(user, User::class.java)

        var jobId = intent.getStringExtra("id")
        jobId = jobId.toString()

        //        get applicant data from api
        apiClient.getApiInterface(this).getApplicant(jobId)
            .enqueue(object: retrofit2.Callback<GetJobApplicant> {
                override fun onFailure(call: Call<GetJobApplicant>, t: Throwable) {
                    Toast.makeText(this@TrackSeekerActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetJobApplicant>,
                    response: Response<GetJobApplicant>
                ) {
                    if(response.code() == 200) {
                        val data = response.body()
                        Log.d("Data", data.toString())

                        val gson = Gson()
                        val json = gson.toJson(data)

                        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("applicantList", json)
                        editor.commit()

                        init()

                        recyclerView = findViewById(R.id.recyclerview)
                        recyclerView.layoutManager = LinearLayoutManager(this@TrackSeekerActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            })

    }

    private fun init() {
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        var user = sp.getString("applicantList", null)

        val userObj = Gson().fromJson(user, GetJobApplicant::class.java)

        adapter = JobProviderApplicantAdapter(userObj.users, this)
    }
}