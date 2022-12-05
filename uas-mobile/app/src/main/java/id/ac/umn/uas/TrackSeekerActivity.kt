package id.ac.umn.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

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