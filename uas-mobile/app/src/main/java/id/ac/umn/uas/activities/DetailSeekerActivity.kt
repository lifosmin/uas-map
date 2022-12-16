package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.ac.umn.uas.R

class DetailSeekerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_seeker)

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
        textPrice.text = jobPrice
        textDate.text = jobDate
        textLocation.text = jobLocation

        Glide.with(this)
            .load(jobImage)
            .apply(RequestOptions().override(100, 100))
            .into(textImage)

//        btnApply.setOnClickListener {
//            val intent = Intent(this, ApplyActivity::class.java)
//            intent.putExtra("id", jobId)
//            startActivity(intent)
//        }
    }
}
