package id.ac.umn.uas.activities.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import id.ac.umn.uas.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        val connect = findViewById<Button>(R.id.buttonLoad)
        connect.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }
    }
}