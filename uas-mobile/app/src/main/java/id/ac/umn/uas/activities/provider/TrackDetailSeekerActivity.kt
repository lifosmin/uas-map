package id.ac.umn.uas.activities.provider

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.ApproveHiringResponse
import id.ac.umn.uas.models.User

class TrackDetailSeekerActivity: AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var gson: Gson
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hire_detail)

//        get user session
        apiClient = ApiClient()

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        gson = Gson()

        //        check if sharepreferences myPrefs exist
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = gson?.fromJson(user, User::class.java)

        val namaBesar = findViewById<TextView>(R.id.namaBesar)
        val namaKecil = findViewById<TextView>(R.id.userNama)
        val email = findViewById<TextView>(R.id.userEmail)
        val kontak = findViewById<TextView>(R.id.userKontak)
        val tanggal = findViewById<TextView>(R.id.userTanggalLahir)
        val kelamin = findViewById<TextView>(R.id.userJenisKelamin)
        val alamat = findViewById<TextView>(R.id.userAlamat)
        val image = findViewById<CircleImageView>(R.id.userImage)
        val btnHire = findViewById<Button>(R.id.buttonHire)
        val btnReject = findViewById<Button>(R.id.buttonReject)
        val id = findViewById<TextView>(R.id.userId)

//        get data from intent
        val intent = intent
        val userId = intent.getStringExtra("id")
        val userNama = intent.getStringExtra("nama")
        val userEmail = intent.getStringExtra("email")
        val userKontak = intent.getStringExtra("kontak")
        val userTanggal = intent.getStringExtra("tanggal")
        var userKelamin = intent.getStringExtra("kelamin")
        var jobId = intent.getStringExtra("job_id")

        userKelamin = if (userKelamin == "0") {
            "Laki-laki"
        } else {
            "Perempuan"
        }

        val userAlamat = intent.getStringExtra("alamat")
        val userImage = intent.getStringExtra("image")

        namaBesar.text = userNama
        namaKecil.text = userNama
        email.text = userEmail
        kontak.text = userKontak
        tanggal.text = userTanggal
        kelamin.text = userKelamin
        alamat.text = userAlamat
        id.text = userId

        Glide.with(this)
            .load(userImage)
            .apply(RequestOptions().override(100, 100))
            .into(image)

        val tempUserId = userId.toString()
        val tempJobId = jobId.toString()

        btnHire.setOnClickListener {
            apiClient.getApiInterface(this).acceptJob(tempJobId, tempUserId, "confirm")
                .enqueue(object : retrofit2.Callback<ApproveHiringResponse> {
                    override fun onFailure(call: retrofit2.Call<ApproveHiringResponse>, t: Throwable) {
                        Toast.makeText(this@TrackDetailSeekerActivity, "User gagal dihire", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: retrofit2.Call<ApproveHiringResponse>,
                        response: retrofit2.Response<ApproveHiringResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data = "User berhasil dihire"
                            Toast.makeText(this@TrackDetailSeekerActivity, data, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                })
        }
        btnReject.setOnClickListener {
            apiClient.getApiInterface(this).acceptJob(tempJobId, tempUserId, "reject")
                .enqueue(object : retrofit2.Callback<ApproveHiringResponse> {
                    override fun onFailure(call: retrofit2.Call<ApproveHiringResponse>, t: Throwable) {
                        Toast.makeText(this@TrackDetailSeekerActivity, "User gagal direject", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: retrofit2.Call<ApproveHiringResponse>,
                        response: retrofit2.Response<ApproveHiringResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data = "User berhasil direject"
                            Toast.makeText(this@TrackDetailSeekerActivity, data, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        apiClient = ApiClient()

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        gson = Gson()

        //        check if sharepreferences myPrefs exist
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = gson?.fromJson(user, User::class.java)

        val namaBesar = findViewById<TextView>(R.id.namaBesar)
        val namaKecil = findViewById<TextView>(R.id.userNama)
        val email = findViewById<TextView>(R.id.userEmail)
        val kontak = findViewById<TextView>(R.id.userKontak)
        val tanggal = findViewById<TextView>(R.id.userTanggalLahir)
        val kelamin = findViewById<TextView>(R.id.userJenisKelamin)
        val alamat = findViewById<TextView>(R.id.userAlamat)
        val image = findViewById<CircleImageView>(R.id.userImage)
        val btnHire = findViewById<Button>(R.id.buttonHire)
        val btnReject = findViewById<Button>(R.id.buttonReject)
        val id = findViewById<TextView>(R.id.userId)

//        get data from intent
        val intent = intent
        val userId = intent.getStringExtra("id")
        val userNama = intent.getStringExtra("nama")
        val userEmail = intent.getStringExtra("email")
        val userKontak = intent.getStringExtra("kontak")
        val userTanggal = intent.getStringExtra("tanggal")
        var userKelamin = intent.getStringExtra("kelamin")
        var jobId = intent.getStringExtra("job_id")

        userKelamin = if (userKelamin == "0") {
            "Laki-laki"
        } else {
            "Perempuan"
        }

        val userAlamat = intent.getStringExtra("alamat")
        val userImage = intent.getStringExtra("image")

        namaBesar.text = userNama
        namaKecil.text = userNama
        email.text = userEmail
        kontak.text = userKontak
        tanggal.text = userTanggal
        kelamin.text = userKelamin
        alamat.text = userAlamat
        id.text = userId

        Glide.with(this)
            .load(userImage)
            .apply(RequestOptions().override(100, 100))
            .into(image)

        val tempUserId = userId.toString()
        val tempJobId = jobId.toString()

        btnHire.setOnClickListener {
            apiClient.getApiInterface(this).acceptJob(tempJobId, tempUserId, "confirm")
                .enqueue(object : retrofit2.Callback<ApproveHiringResponse> {
                    override fun onFailure(call: retrofit2.Call<ApproveHiringResponse>, t: Throwable) {
                        Toast.makeText(this@TrackDetailSeekerActivity, "User gagal dihire", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: retrofit2.Call<ApproveHiringResponse>,
                        response: retrofit2.Response<ApproveHiringResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data = "User berhasil dihire"
                            Toast.makeText(this@TrackDetailSeekerActivity, data, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                })
        }
        btnReject.setOnClickListener {
            apiClient.getApiInterface(this).acceptJob(tempJobId, tempUserId, "reject")
                .enqueue(object : retrofit2.Callback<ApproveHiringResponse> {
                    override fun onFailure(call: retrofit2.Call<ApproveHiringResponse>, t: Throwable) {
                        Toast.makeText(this@TrackDetailSeekerActivity, "User gagal direject", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: retrofit2.Call<ApproveHiringResponse>,
                        response: retrofit2.Response<ApproveHiringResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data = "User berhasil direject"
                            Toast.makeText(this@TrackDetailSeekerActivity, data, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                })
        }
    }
}