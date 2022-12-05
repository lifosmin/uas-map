package id.ac.umn.uas

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var intentLogin = findViewById<LinearLayout>(R.id.login)
        intentLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }
}