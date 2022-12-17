package id.ac.umn.uas.activities.User

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
import id.ac.umn.uas.models.UpdateUserResponse
import id.ac.umn.uas.models.User
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var apiClient: ApiClient

    private var isImageChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        var profileImage = findViewById<CircleImageView>(R.id.profile_image)
        var btnGambar = findViewById<Button>(R.id.uploadImage)
        var btnAmbilGambar = findViewById<Button>(R.id.takeImage)
        var editNamaText = findViewById<TextView>(R.id.editNamaText)
        var editEmailText = findViewById<TextView>(R.id.editEmailText)
        var editPasswordText = findViewById<TextView>(R.id.editPasswordText)
        var editTanggalLahirText = findViewById<TextView>(R.id.editTanggalLahirText)
        var radioKelamin = findViewById<RadioGroup>(R.id.radioGroupKelamin)
        var editAlamatText = findViewById<TextView>(R.id.editAlamatText)
        var editNoTelpText = findViewById<TextView>(R.id.editNoTelponText)
        var btnSimpan = findViewById<Button>(R.id.btnSimpan)

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
            var image: MultipartBody.Part? = null
            val nama: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editNamaText.text.toString())
            val email: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editEmailText.text.toString())
            val password: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editPasswordText.text.toString())
            val tanggalLahir: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editTanggalLahirText.text.toString())
            val jenisKelamin: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, if (radioKelamin.checkedRadioButtonId == R.id.radioLaki) "1" else "0")
            val alamat: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editAlamatText.text.toString())
            val noTelp: RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editNoTelpText.text.toString())

            if (isImageChanged) {
                val imageUri = getImageUri()
                val imageFile = File(getRealPathFromURI(imageUri))
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
                image = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }else{
                val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val imageFile = File(getRealPathFromURI(getImageUri()))
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
                image = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }

            apiClient.getApiInterface(this).updateUser(image, nama, email, password, tanggalLahir, jenisKelamin, alamat, noTelp).enqueue(object: retrofit2.Callback<UpdateUserResponse> {
                override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, "Update User Berhasil!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@ProfileActivity, "Update User Gagal!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }

        btnGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, 1)
        }

        btnAmbilGambar.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 2)
        }
    }

    private fun getImageUri(): Any {
        val bitmap =
            (findViewById<CircleImageView>(R.id.profile_image).drawable as BitmapDrawable).bitmap
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(imageUri: Any): String {
        val cursor = contentResolver.query(imageUri as Uri, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var imageUri = data?.data
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            var profileImage = findViewById<CircleImageView>(R.id.profile_image)
            var imageUri = data.data
            profileImage.setImageURI(imageUri)
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            var profileImage = findViewById<CircleImageView>(R.id.profile_image)
            profileImage.setImageBitmap(imageBitmap)
        }
    }
}