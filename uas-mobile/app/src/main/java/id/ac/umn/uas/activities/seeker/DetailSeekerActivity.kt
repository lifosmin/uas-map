package id.ac.umn.uas.activities.seeker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.ApplyJobResponse
import retrofit2.Call
import retrofit2.Response

class DetailSeekerActivity: AppCompatActivity() {
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_seeker)

        apiClient = ApiClient()

        val jobId = intent.getStringExtra("id")
        val jobTitle = intent.getStringExtra("judul")
        val jobDesc = intent.getStringExtra("deskripsi")
        val jobPrice = intent.getStringExtra("gaji")
        val jobDate = intent.getStringExtra("tanggal")
        val jobLocation = intent.getStringExtra("lokasi")
        val jobImage = intent.getStringExtra("image")

        val btnApply = findViewById<TextView>(R.id.buttonApply)

        val textJudul = findViewById<TextView>(R.id.judulJob)
        val textDesc = findViewById<TextView>(R.id.jobDesc)
        val textPrice = findViewById<TextView>(R.id.jobGaji)
        val textDate = findViewById<TextView>(R.id.jobTanggal)
        val textLocation = findViewById<TextView>(R.id.jobLokasi)
        val textImage = findViewById<ImageView>(R.id.jobImage)

        textJudul.text = jobTitle
        textDesc.text = jobDesc
        var tempJobPrice = jobPrice?.replace("\\B(?=(\\d{3})+(?!\\d))".toRegex(), ",")
        textPrice.text = "Rp. $tempJobPrice"
        textDate.text = jobDate
        textLocation.text = jobLocation

        val id = jobId.toString()

        Glide.with(this)
            .load(jobImage)
            .apply(RequestOptions().override(100, 100))
            .into(textImage)

        btnApply.setOnClickListener {
            apiClient.getApiInterface(this).applyJob(id)
                .enqueue(object: retrofit2.Callback<ApplyJobResponse> {
                    override fun onFailure(call: Call<ApplyJobResponse>, t: Throwable) {
                        Toast.makeText(this@DetailSeekerActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<ApplyJobResponse>,
                        response: Response<ApplyJobResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DetailSeekerActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                            val intent = Intent(this@DetailSeekerActivity, SeekerActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@DetailSeekerActivity, response.errorBody()?.string() , Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        apiClient = ApiClient()

        val jobId = intent.getStringExtra("id")
        val jobTitle = intent.getStringExtra("judul")
        val jobDesc = intent.getStringExtra("deskripsi")
        val jobPrice = intent.getStringExtra("gaji")
        val jobDate = intent.getStringExtra("tanggal")
        val jobLocation = intent.getStringExtra("lokasi")
        val jobImage = intent.getStringExtra("image")

        val btnApply = findViewById<TextView>(R.id.buttonApply)

        val textJudul = findViewById<TextView>(R.id.judulJob)
        val textDesc = findViewById<TextView>(R.id.jobDesc)
        val textPrice = findViewById<TextView>(R.id.jobGaji)
        val textDate = findViewById<TextView>(R.id.jobTanggal)
        val textLocation = findViewById<TextView>(R.id.jobLokasi)
        val textImage = findViewById<ImageView>(R.id.jobImage)

        textJudul.text = jobTitle
        textDesc.text = jobDesc
        var tempJobPrice = jobPrice?.replace("\\B(?=(\\d{3})+(?!\\d))".toRegex(), ",")
        textPrice.text = "Rp. $tempJobPrice"
        textDate.text = jobDate
        textLocation.text = jobLocation

        val id = jobId.toString()

        Glide.with(this)
            .load(jobImage)
            .apply(RequestOptions().override(100, 100))
            .into(textImage)

        btnApply.setOnClickListener {
            apiClient.getApiInterface(this).applyJob(id)
                .enqueue(object: retrofit2.Callback<ApplyJobResponse> {
                    override fun onFailure(call: Call<ApplyJobResponse>, t: Throwable) {
                        Toast.makeText(this@DetailSeekerActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<ApplyJobResponse>,
                        response: Response<ApplyJobResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DetailSeekerActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                            val intent = Intent(this@DetailSeekerActivity, SeekerActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@DetailSeekerActivity, response.errorBody()?.string() , Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }
}
