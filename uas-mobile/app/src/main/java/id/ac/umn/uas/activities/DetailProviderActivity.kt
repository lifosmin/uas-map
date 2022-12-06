package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uas.R

class DetailProviderActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_provider)

        var intentTrackSeeker = findViewById<Button>(R.id.buttonTrackSeeker)
        intentTrackSeeker.setOnClickListener {
            val intentTrack = Intent(this, TrackSeekerActivity::class.java)
            startActivity(intentTrack)
        }
    }
}
