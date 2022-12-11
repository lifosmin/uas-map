package id.ac.umn.uas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.DefaultResponse
import id.ac.umn.uas.models.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var welcomeHead = findViewById<TextView>(R.id.welcomeHead)
        var editEmailText = findViewById<EditText>(R.id.editEmailText)
        var editPasswordText = findViewById<EditText>(R.id.editPasswordText)

        var buttonLogin = findViewById<Button>(R.id.buttonSignIn)
        var buttonSignUp = findViewById<LinearLayout>(R.id.linearLayoutSignUp)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        buttonLogin.setOnClickListener{
            var email = editEmailText.text.toString()
            var password = editPasswordText.text.toString()

            if(email.isEmpty()){
                editEmailText.error = "Email is required"
                editEmailText.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editPasswordText.error = "Password is required"
                editPasswordText.requestFocus()
                return@setOnClickListener
            }

            apiClient.getApiInterface(this).loginUser(email, password)
                .enqueue(object: retrofit2.Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if(response.code() == 200){
                            response.body()?.let {
                                Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_LONG).show()
                                sessionManager.saveAuthToken(it.token)
                                if(it.token != null){
                                    apiClient.getApiInterface(this@LoginActivity).getUser()
                                        .enqueue(object : retrofit2.Callback<DefaultResponse> {
                                            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                                                t.printStackTrace()
                                            }

                                            override fun onResponse(
                                                call: Call<DefaultResponse>,
                                                response: Response<DefaultResponse>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val intent = Intent(this@LoginActivity, ChoiceActivity::class.java)

                                                    val user = response.body()?.user

//                                                    User(alamat=kepo, created_at=2022-12-11T19:25:44.000000Z, email=raphael@gmail.com, id=1, jenis_kelamin=Perempuan, nama=raphael, no_telp=123456789, tanggal_lahir=10 Oktober 2002, updated_at=2022-12-11T19:25:44.000000Z)
//                                                    get nama from User
                                                    var nama = user?.nama

                                                    nama = nama?.split(" ")?.joinToString(" ") { it.capitalize() }


                                                    welcomeHead.text = "Welcome, " + nama?.substringBefore(" ")
                                                    intent.putExtra("User", user.toString())
                                                    intent.putExtra("Welcome", welcomeHead.text.toString())

                                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

//                                                  Pass User and Welcome data to next activity
                                                    startActivity(intent)
                                                } else {
                                                    val jObjError = JSONObject(response.errorBody()?.string())
                                                    Toast.makeText(this@LoginActivity, jObjError.getString("message"), Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        })
                                }
                            }
                        } else if(response.code() == 401){
                            Toast.makeText(this@LoginActivity, "Email atau password salah", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }

        buttonSignUp.setOnClickListener {
            var intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

    }
}