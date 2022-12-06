package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uas.R

class TrackSeekerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_list)

        var intentDetailSeeker = findViewById<Button>(R.id.buttonTrackDetailSeeker)
        intentDetailSeeker.setOnClickListener {
            val intentDetail = Intent(this, TrackDetailSeekerActivity::class.java)
            startActivity(intentDetail)
        }
    }
}