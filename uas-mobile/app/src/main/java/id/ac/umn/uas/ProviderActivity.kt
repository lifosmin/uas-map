package id.ac.umn.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ProviderActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_provider)

        var intentDetailProvider = findViewById<LinearLayout>(R.id.detailProvider)
        var intentAddJob = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.addJob)
        var intentButtonAddJob = findViewById<Button>(R.id.buttonAddJob)
        intentDetailProvider.setOnClickListener {
            val intentDetail = Intent(this, DetailProviderActivity::class.java)
            startActivity(intentDetail)
        }
        intentAddJob.setOnClickListener {
            val intentAdd = Intent(this, AddJobActivity::class.java)
            startActivity(intentAdd)
        }
        intentButtonAddJob.setOnClickListener {
            val intentAdd = Intent(this, AddJobActivity::class.java)
            startActivity(intentAdd)
        }
    }
}
