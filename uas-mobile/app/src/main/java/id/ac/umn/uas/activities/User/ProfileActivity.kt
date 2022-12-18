package id.ac.umn.uas.activities.User

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.LogoutResponse
import id.ac.umn.uas.models.UpdateUserResponse
import id.ac.umn.uas.models.User
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        var profileImage = findViewById<CircleImageView>(R.id.update_profile_image)
        var editNamaText = findViewById<TextView>(R.id.editNamaText)
        var editEmailText = findViewById<TextView>(R.id.editEmailText)
        var editPasswordText = findViewById<TextView>(R.id.editPasswordText)
        var editTanggalLahirText = findViewById<TextView>(R.id.editTanggalLahirText)
        var radioKelamin = findViewById<RadioGroup>(R.id.radioGroupKelamin)
        var editAlamatText = findViewById<TextView>(R.id.editAlamatText)
        var editNoTelpText = findViewById<TextView>(R.id.editNoTelponText)
        var btnSimpan = findViewById<Button>(R.id.btnSimpan)

        var btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            apiClient.getApiInterface(this).logoutUser().enqueue(object: retrofit2.Callback<LogoutResponse> {
                override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, response.body()?.message , Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        try {
                            Toast.makeText(this@ProfileActivity, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        if (userObj.jenis_kelamin == "0") {
            radioKelamin.check(R.id.radioLaki)
        } else {
            radioKelamin.check(R.id.radioPerempuan)
        }

        editNamaText.text = userObj.nama
        editEmailText.text = userObj.email
        editTanggalLahirText.text = userObj.tanggal_lahir
        editAlamatText.text = userObj.alamat
        editNoTelpText.text = userObj.no_telp

        Glide.with(this)
            .load(userObj.image)
            .into(profileImage)


        editTanggalLahirText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val monthString = arrayOf(
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
            )
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    editTanggalLahirText.setText("$dayOfMonth ${monthString[monthOfYear]} $year")
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        apiClient = ApiClient()

        btnSimpan.setOnClickListener {
            val nama = editNamaText.text.toString()
            val email = editEmailText.text.toString()
            val password = editPasswordText.text.toString()
            val tanggalLahir = editTanggalLahirText.text.toString()
            val alamat = editAlamatText.text.toString()
            val noTelp = editNoTelpText.text.toString()
            val jenisKelamin = if (radioKelamin.checkedRadioButtonId == R.id.radioLaki) "0" else "1"

            apiClient.getApiInterface(this).updateUser(nama, email, password, tanggalLahir, jenisKelamin, alamat, noTelp).enqueue(object: retrofit2.Callback<UpdateUserResponse> {
                override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, response.body()?.message , Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        try {
                            Toast.makeText(this@ProfileActivity, response.errorBody()?.string() , Toast.LENGTH_LONG).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    override fun onResume(){
        super.onResume()

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        var profileImage = findViewById<CircleImageView>(R.id.update_profile_image)
        var editNamaText = findViewById<TextView>(R.id.editNamaText)
        var editEmailText = findViewById<TextView>(R.id.editEmailText)
        var editPasswordText = findViewById<TextView>(R.id.editPasswordText)
        var editTanggalLahirText = findViewById<TextView>(R.id.editTanggalLahirText)
        var radioKelamin = findViewById<RadioGroup>(R.id.radioGroupKelamin)
        var editAlamatText = findViewById<TextView>(R.id.editAlamatText)
        var editNoTelpText = findViewById<TextView>(R.id.editNoTelponText)
        var btnSimpan = findViewById<Button>(R.id.btnSimpan)

        var btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            apiClient.getApiInterface(this).logoutUser().enqueue(object: retrofit2.Callback<LogoutResponse> {
                override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, response.body()?.message , Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        try {
                            Toast.makeText(this@ProfileActivity, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        if (userObj.jenis_kelamin == "0") {
            radioKelamin.check(R.id.radioLaki)
        } else {
            radioKelamin.check(R.id.radioPerempuan)
        }

        editNamaText.text = userObj.nama
        editEmailText.text = userObj.email
        editTanggalLahirText.text = userObj.tanggal_lahir
        editAlamatText.text = userObj.alamat
        editNoTelpText.text = userObj.no_telp

        Glide.with(this)
            .load(userObj.image)
            .into(profileImage)


        editTanggalLahirText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val monthString = arrayOf(
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
            )
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    editTanggalLahirText.setText("$dayOfMonth ${monthString[monthOfYear]} $year")
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        apiClient = ApiClient()

        btnSimpan.setOnClickListener {
            val nama = editNamaText.text.toString()
            val email = editEmailText.text.toString()
            val password = editPasswordText.text.toString()
            val tanggalLahir = editTanggalLahirText.text.toString()
            val alamat = editAlamatText.text.toString()
            val noTelp = editNoTelpText.text.toString()
            val jenisKelamin = if (radioKelamin.checkedRadioButtonId == R.id.radioLaki) "0" else "1"

            apiClient.getApiInterface(this).updateUser(nama, email, password, tanggalLahir, jenisKelamin, alamat, noTelp).enqueue(object: retrofit2.Callback<UpdateUserResponse> {
                override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, response.body()?.message , Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        try {
                            Toast.makeText(this@ProfileActivity, response.errorBody()?.string() , Toast.LENGTH_LONG).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}