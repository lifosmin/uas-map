package id.ac.umn.uas.activities.User

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.ac.umn.uas.R
import id.ac.umn.uas.activities.provider.ProviderActivity
import id.ac.umn.uas.activities.seeker.SeekerActivity
import id.ac.umn.uas.models.User

class ChoiceActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        var name = userObj.nama.split(" ")
        var nameCap = name[0].capitalize()

        welcomeHead.text = "Welcome, ${nameCap}"

        var buttonProvider = findViewById<Button>(R.id.buttonProvider)
        var buttonSeeker = findViewById<Button>(R.id.buttonSeeker)
        var buttonBiodata = findViewById<Button>(R.id.buttonBiodata)

        buttonProvider.setOnClickListener {
            val intentProvider = Intent(this, ProviderActivity::class.java)

            intentProvider.putExtra("User", user.toString())

            startActivity(intentProvider)
        }
        buttonSeeker.setOnClickListener {
            val intentSeeker = Intent(this, SeekerActivity::class.java)

            intentSeeker.putExtra("User", user.toString())

            startActivity(intentSeeker)
        }

        buttonBiodata.setOnClickListener {
            val intentBiodata = Intent(this, ProfileActivity::class.java)

            intentBiodata.putExtra("User", user.toString())

            startActivity(intentBiodata)
        }

    }

    override fun onResume() {
        super.onResume()

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        var name = userObj.nama.split(" ")
        var nameCap = name[0].capitalize()

        welcomeHead.text = "Welcome, ${nameCap}"

        var buttonProvider = findViewById<Button>(R.id.buttonProvider)
        var buttonSeeker = findViewById<Button>(R.id.buttonSeeker)
        var buttonBiodata = findViewById<Button>(R.id.buttonBiodata)

        buttonProvider.setOnClickListener {
            val intentProvider = Intent(this, ProviderActivity::class.java)

            intentProvider.putExtra("User", user.toString())

            startActivity(intentProvider)
        }
        buttonSeeker.setOnClickListener {
            val intentSeeker = Intent(this, SeekerActivity::class.java)

            intentSeeker.putExtra("User", user.toString())

            startActivity(intentSeeker)
        }
        buttonBiodata.setOnClickListener {
            val intentBiodata = Intent(this, ProfileActivity::class.java)

            intentBiodata.putExtra("User", user.toString())

            startActivity(intentBiodata)
        }

    }
}
