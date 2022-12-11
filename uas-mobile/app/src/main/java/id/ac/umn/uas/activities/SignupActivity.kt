package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.DefaultResponse
import retrofit2.Call
import retrofit2.Response

class SignupActivity: AppCompatActivity() {
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var editNamaText = findViewById<EditText>(R.id.editNamaText)
        var editEmailText = findViewById<EditText>(R.id.editEmailText)
        var editPasswordText = findViewById<EditText>(R.id.editPasswordText)

        var btnSignup = findViewById<Button>(R.id.buttonSignUp)
        var btnLogin = findViewById<LinearLayout>(R.id.linearLayoutLogin)

        apiClient = ApiClient()

        btnSignup.setOnClickListener {
            var nama = editNamaText.text.toString()
            var email = editEmailText.text.toString()
            var password = editPasswordText.text.toString()
            var 

            if(nama.isEmpty()) {
                editNamaText.error = "Nama tidak boleh kosong"
                editNamaText.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()) {
                editEmailText.error = "Email tidak boleh kosong"
                editEmailText.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                editPasswordText.error = "Password tidak boleh kosong"
                editPasswordText.requestFocus()
                return@setOnClickListener
            }

            if(password.length < 6) {
                editPasswordText.error = "Password minimal 6 karakter"
                editPasswordText.requestFocus()
                return@setOnClickListener
            }

            apiClient.getApiInterface(this@SignupActivity).registerUser(nama, email, password)
                .enqueue(object: retrofit2.Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if(response.code() == 201) {
                            Toast.makeText(applicationContext, "Pendaftaran berhasil", Toast.LENGTH_LONG).show()
                            val intentLogin = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intentLogin)
                        } else if(response.code() == 422) {
                            Toast.makeText(
                                applicationContext,
                                "Email sudah terdaftar",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Pendaftaran gagal",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })

        }

        btnLogin.setOnClickListener {
            var intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}