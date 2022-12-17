package id.ac.umn.uas.activities.seeker

import id.ac.umn.uas.activities.adapter.JobAdapter
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
import id.ac.umn.uas.activities.seeker.appliedjobs.SeekerAppliedJobActivity
import id.ac.umn.uas.activities.User.ProfileActivity
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.AppliedJobCountResponse
import id.ac.umn.uas.models.GetJobResponse
import id.ac.umn.uas.models.User
import retrofit2.Call
import retrofit2.Response


class SeekerActivity: AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var sp: SharedPreferences
    private var adapter: JobAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_seeker)

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
                    Toast.makeText(this@SeekerActivity, t.message, Toast.LENGTH_LONG).show()
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
                        editor.putString("jobs", json)
                        editor.commit()

                        init()

                        recyclerView = findViewById(R.id.seekerRecyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@SeekerActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            })

        val userImage = findViewById<CircleImageView>(R.id.profile_image)
        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        welcomeHead.text = "Welcome Back, ${userObj?.nama}"

        apiClient.getApiInterface(this).countAppliedJob()
            .enqueue(object: retrofit2.Callback<AppliedJobCountResponse> {
                override fun onFailure(call: Call<AppliedJobCountResponse>, t: Throwable) {
                    Toast.makeText(this@SeekerActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<AppliedJobCountResponse>,
                    response: Response<AppliedJobCountResponse>
                ) {
                    if (response.isSuccessful) {
                        val appliedJobCount = response.body()?.count
                        val appliedJobCountText = findViewById<TextView>(R.id.buttonJobList)
                        appliedJobCountText.text = "Applied Job : $appliedJobCount"
                    }
                }
            })

        val appliedJobCountText = findViewById<TextView>(R.id.buttonJobList)
        appliedJobCountText.setOnClickListener {
            val intent = Intent(this, SeekerAppliedJobActivity::class.java)
            startActivity(intent)
        }

//        change userImage with userObj image upload from api via Bitmap
        val imageUrl = userObj?.image

        Glide.with(this)
            .load(imageUrl)
            .into(userImage)

    }

    private fun init() {
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        var job = sp.getString("jobs", null)

        val jobObj = Gson().fromJson(job, GetJobResponse::class.java)

        adapter = JobAdapter(jobObj.job, this)
    }

    override fun onResume() {
        super.onResume()
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
                    Toast.makeText(this@SeekerActivity, t.message, Toast.LENGTH_LONG).show()
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
                        editor.putString("jobs", json)
                        editor.commit()

                        init()

                        recyclerView = findViewById(R.id.seekerRecyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@SeekerActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            })

        val userImage = findViewById<CircleImageView>(R.id.profile_image)
        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        welcomeHead.text = "Welcome Back, ${userObj?.nama}"

        apiClient.getApiInterface(this).countAppliedJob()
            .enqueue(object: retrofit2.Callback<AppliedJobCountResponse> {
                override fun onFailure(call: Call<AppliedJobCountResponse>, t: Throwable) {
                    Toast.makeText(this@SeekerActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<AppliedJobCountResponse>,
                    response: Response<AppliedJobCountResponse>
                ) {
                    if (response.isSuccessful) {
                        val appliedJobCount = response.body()?.count
                        val appliedJobCountText = findViewById<TextView>(R.id.buttonJobList)
                        appliedJobCountText.text = "Applied Job : $appliedJobCount"
                    }
                }
            })

        val appliedJobCountText = findViewById<TextView>(R.id.buttonJobList)
        appliedJobCountText.setOnClickListener {
            val intent = Intent(this, SeekerAppliedJobActivity::class.java)
            startActivity(intent)
        }

//        change userImage with userObj image upload from api via Bitmap
        val imageUrl = userObj?.image

        Glide.with(this)
            .load(imageUrl)
            .into(userImage)

    }
}
