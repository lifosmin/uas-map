package id.ac.umn.uas.activities.seeker.appliedjobs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.ac.umn.uas.R
import id.ac.umn.uas.activities.seeker.SeekerActivity
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.CancelJobResponse
import retrofit2.Call
import retrofit2.Response

class DetailAppliedSeekerActivity: AppCompatActivity() {
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

        btnApply.text = "Cancel Job"
        btnApply.setOnClickListener {
            apiClient.getApiInterface(this).cancelJob(id)
                .enqueue(object: retrofit2.Callback<CancelJobResponse> {
                    override fun onFailure(call: Call<CancelJobResponse>, t: Throwable) {
                        Toast.makeText(this@DetailAppliedSeekerActivity, "Failed to cancel job", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<CancelJobResponse>, response: Response<CancelJobResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DetailAppliedSeekerActivity, "Job canceled", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailAppliedSeekerActivity, SeekerActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@DetailAppliedSeekerActivity, "Failed to cancel job", Toast.LENGTH_SHORT).show()
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

        btnApply.text = "Cancel Job"
        btnApply.setOnClickListener {
            apiClient.getApiInterface(this).cancelJob(id)
                .enqueue(object: retrofit2.Callback<CancelJobResponse> {
                    override fun onFailure(call: Call<CancelJobResponse>, t: Throwable) {
                        Toast.makeText(this@DetailAppliedSeekerActivity, "Failed to cancel job", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<CancelJobResponse>, response: Response<CancelJobResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DetailAppliedSeekerActivity, "Job canceled", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailAppliedSeekerActivity, SeekerActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@DetailAppliedSeekerActivity, "Failed to cancel job", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }
}
