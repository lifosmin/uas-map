package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.DefaultResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_choice)

//        Get User and Welcome data from previous activity
        val user = intent.getStringExtra("User")
        val welcome = intent.getStringExtra("Welcome")

        Log.d("ChoiceActivity", "User: $user")

//        Change welcomeHead text into welcome data
        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)
        welcomeHead.text = welcome

        var buttonProvider = findViewById<Button>(R.id.buttonProvider)
        var buttonSeeker = findViewById<Button>(R.id.buttonSeeker)

        buttonProvider.setOnClickListener {
            val intentProvider = Intent(this, ProviderActivity::class.java)
            startActivity(intentProvider)
        }
        buttonSeeker.setOnClickListener {
            val intentSeeker = Intent(this, SeekerActivity::class.java)
            startActivity(intentSeeker)
        }
    }
}
