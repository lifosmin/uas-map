package id.ac.umn.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

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
