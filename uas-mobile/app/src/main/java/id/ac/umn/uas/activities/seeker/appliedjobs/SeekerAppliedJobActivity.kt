package id.ac.umn.uas.activities.seeker.appliedjobs

import id.ac.umn.uas.activities.adapter.AppliedJobListAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.GetJobResponse
import retrofit2.Call
import retrofit2.Response

class SeekerAppliedJobActivity: AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var sp: SharedPreferences
    private var adapter: AppliedJobListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)

        apiClient = ApiClient()

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        apiClient.getApiInterface(this).getAppliedJob()
            .enqueue(object: retrofit2.Callback<GetJobResponse> {
                override fun onFailure(call: Call<GetJobResponse>, t: Throwable) {
                    Toast.makeText(this@SeekerAppliedJobActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetJobResponse>,
                    response: Response<GetJobResponse>
                ) {
                    if (response.isSuccessful) {
                        val jobList = response.body()

                        val gson = Gson()
                        val json = gson.toJson(jobList)

                        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("appliedJobs", json)
                        editor.commit()

                        init()

                        recyclerView = findViewById(R.id.recyclerview)
                        recyclerView.layoutManager = LinearLayoutManager(this@SeekerAppliedJobActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            })
    }

    private fun init() {
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        var job = sp.getString("appliedJobs", null)

        val jobObj = Gson().fromJson(job, GetJobResponse::class.java)

        adapter = AppliedJobListAdapter(jobObj.job, this)
    }

    override fun onResume() {
        super.onResume()
        apiClient = ApiClient()

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        apiClient.getApiInterface(this).getAppliedJob()
            .enqueue(object: retrofit2.Callback<GetJobResponse> {
                override fun onFailure(call: Call<GetJobResponse>, t: Throwable) {
                    Toast.makeText(this@SeekerAppliedJobActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetJobResponse>,
                    response: Response<GetJobResponse>
                ) {
                    if (response.isSuccessful) {
                        val jobList = response.body()

                        val gson = Gson()
                        val json = gson.toJson(jobList)

                        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("appliedJobs", json)
                        editor.commit()

                        init()

                        recyclerView = findViewById(R.id.recyclerview)
                        recyclerView.layoutManager = LinearLayoutManager(this@SeekerAppliedJobActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            })
    }
}