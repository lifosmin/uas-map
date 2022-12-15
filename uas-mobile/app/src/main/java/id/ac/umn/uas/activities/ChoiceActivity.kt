package id.ac.umn.uas.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.TypedArrayUtils.getString
import com.google.gson.Gson
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.DefaultResponse
import id.ac.umn.uas.models.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ChoiceActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        val welcomeHead = findViewById<TextView>(R.id.welcomeHead)

        welcomeHead.text = "Welcome, ${userObj.nama}"

        var buttonProvider = findViewById<Button>(R.id.buttonProvider)
        var buttonSeeker = findViewById<Button>(R.id.buttonSeeker)

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

    }
}
