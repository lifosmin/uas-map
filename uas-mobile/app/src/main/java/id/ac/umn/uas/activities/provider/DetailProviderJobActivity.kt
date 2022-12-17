package id.ac.umn.uas.activities.provider

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R

class DetailProviderJobActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_provider)

        val jobJudul = findViewById<TextView>(R.id.judulJob)
        val jobGaji = findViewById<TextView>(R.id.jobGaji)
        val jobImage = findViewById<ImageView>(R.id.jobImage)
        val jobTanggal = findViewById<TextView>(R.id.jobTanggal)
        val jobLokasi = findViewById<TextView>(R.id.jobLokasi)
        val jobDesc = findViewById<TextView>(R.id.jobDesc)
        val buttonTrack = findViewById<Button>(R.id.buttonTrack)

//        get data from intent
        val intent = intent
        val Id = intent.getStringExtra("id")
        val judul = intent.getStringExtra("judul")
        val lokasi = intent.getStringExtra("lokasi")
        val tanggal = intent.getStringExtra("tanggal")
        val gaji = intent.getStringExtra("gaji")
        val deskripsi = intent.getStringExtra("deskripsi")
        val image = intent.getStringExtra("image")

        jobJudul.text = judul

        var tempGaji = gaji?.replace("\\B(?=(\\d{3})+(?!\\d))".toRegex(), ",")
        jobGaji.text = "Rp. $tempGaji"

        jobTanggal.text = tanggal
        jobLokasi.text = lokasi
        jobDesc.text = deskripsi

        Glide.with(this)
            .load(image)
            .apply(RequestOptions().override(100, 100))
            .into(jobImage)

        buttonTrack.setOnClickListener {
            val intent = Intent(this, TrackSeekerActivity::class.java)
            intent.putExtra("id", Id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val jobJudul = findViewById<TextView>(R.id.judulJob)
        val jobGaji = findViewById<TextView>(R.id.jobGaji)
        val jobImage = findViewById<ImageView>(R.id.jobImage)
        val jobTanggal = findViewById<TextView>(R.id.jobTanggal)
        val jobLokasi = findViewById<TextView>(R.id.jobLokasi)
        val jobDesc = findViewById<TextView>(R.id.jobDesc)
        val buttonTrack = findViewById<Button>(R.id.buttonTrack)

//        get data from intent
        val intent = intent
        val Id = intent.getStringExtra("id")
        val judul = intent.getStringExtra("judul")
        val lokasi = intent.getStringExtra("lokasi")
        val tanggal = intent.getStringExtra("tanggal")
        val gaji = intent.getStringExtra("gaji")
        val deskripsi = intent.getStringExtra("deskripsi")
        val image = intent.getStringExtra("image")

        jobJudul.text = judul

        var tempGaji = gaji?.replace("\\B(?=(\\d{3})+(?!\\d))".toRegex(), ",")
        jobGaji.text = "Rp. $tempGaji"

        jobTanggal.text = tanggal
        jobLokasi.text = lokasi
        jobDesc.text = deskripsi

        Glide.with(this)
            .load(image)
            .apply(RequestOptions().override(100, 100))
            .into(jobImage)

        buttonTrack.setOnClickListener {
            val intent = Intent(this, TrackSeekerActivity::class.java)
            intent.putExtra("id", Id)
            startActivity(intent)
        }
    }
}
