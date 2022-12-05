package id.ac.umn.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.buttonLogin)
        val signup = findViewById<LinearLayout>(R.id.signup)
        login.setOnClickListener {
            val intentLogin = Intent(this, ChoiceActivity::class.java)
            startActivity(intentLogin)
        }
        signup.setOnClickListener {
            val intentSignup = Intent(this, SignupActivity::class.java)
            startActivity(intentSignup)
        }
    }
}