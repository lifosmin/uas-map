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

class DetailProviderActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hire_detail)

        val namaBesar = findViewById<TextView>(R.id.namaBesar)
        val namaKecil = findViewById<TextView>(R.id.userNama)
        val email = findViewById<TextView>(R.id.userEmail)
        val kontak = findViewById<TextView>(R.id.userKontak)
        val tanggal = findViewById<TextView>(R.id.userTanggalLahir)
        val kelamin = findViewById<TextView>(R.id.userJenisKelamin)
        val alamat = findViewById<TextView>(R.id.userAlamat)
        val image = findViewById<CircleImageView>(R.id.userImage)
        val btnHire = findViewById<Button>(R.id.buttonHire)
        val id = findViewById<TextView>(R.id.userId)

//        get data from intent
        val intent = intent
        val userId = intent.getStringExtra("id")
        val userNama = intent.getStringExtra("nama")
        val userEmail = intent.getStringExtra("email")
        val userKontak = intent.getStringExtra("kontak")
        val userTanggal = intent.getStringExtra("tanggal")
        val userKelamin = intent.getStringExtra("kelamin")
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
    }
}
