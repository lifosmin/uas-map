package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uas.R

class SeekerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_seeker)

//        var intentDetailSeeker = findViewById<LinearLayout>(R.id.detailSeeker)
//        var intentJobList = findViewById<Button>(R.id.buttonJobList)
//        intentDetailSeeker.setOnClickListener {
//            val intentDetail = Intent(this, DetailSeekerActivity::class.java)
//            startActivity(intentDetail)
//        }
//        intentJobList.setOnClickListener {
//            val intentList = Intent(this, JobListActivity::class.java)
//            startActivity(intentList)
//        }
    }
}
