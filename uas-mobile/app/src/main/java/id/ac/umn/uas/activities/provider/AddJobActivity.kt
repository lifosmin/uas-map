package id.ac.umn.uas.activities.provider

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.activities.User.LoginActivity
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.api.SessionManager
import id.ac.umn.uas.models.CreateJobResponse
import id.ac.umn.uas.models.DefaultResponse
import id.ac.umn.uas.models.User
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class AddJobActivity: AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    private lateinit var sp : SharedPreferences
    private var isImageChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        var editJobImage = findViewById<ImageView>(R.id.jobImage)
        var buttonAddImage = findViewById<Button>(R.id.addImage)
        var buttonTakeImage = findViewById<Button>(R.id.takeImage)

        var editJobJudul = findViewById<EditText>(R.id.jobJudul)
        var editJobDetail = findViewById<EditText>(R.id.jobDetail)
        var editJobAlamat = findViewById<EditText>(R.id.jobAlamat)
        var editJobGaji = findViewById<EditText>(R.id.jobGaji)
//        var editJobTanggal = findViewById<TextView>(R.id.jobTanggal)

        var buttonAddJob = findViewById<Button>(R.id.addJob)

        apiClient = ApiClient()

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        buttonAddJob.setOnClickListener {
            var image : MultipartBody.Part? = null
            val judul : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobJudul.text.toString())
            val detail : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobDetail.text.toString())
            val alamat : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobAlamat.text.toString())
            val gaji : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobGaji.text.toString())

            if(editJobJudul.text.toString().isEmpty()){
                editJobJudul.error = "Judul tidak boleh kosong"
                editJobJudul.requestFocus()
                return@setOnClickListener
            }

            if(editJobDetail.text.toString().isEmpty()){
                editJobDetail.error = "Detail tidak boleh kosong"
                editJobDetail.requestFocus()
                return@setOnClickListener
            }

            if(editJobAlamat.text.toString().isEmpty()){
                editJobAlamat.error = "Alamat tidak boleh kosong"
                editJobAlamat.requestFocus()
                return@setOnClickListener
            }

            if(editJobGaji.text.toString().isEmpty()){
                editJobGaji.error = "Gaji tidak boleh kosong"
                editJobGaji.requestFocus()
                return@setOnClickListener
            }

            if (isImageChanged) {
                val imageUri = getImageUri()
                val imageFile = File(getRealPathFromURI(imageUri))
                val requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
                image = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }else{
                val bitmap =
                    (resources.getDrawable(R.drawable.default_profile_picture) as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = File(cacheDir, "default_image.jpg")
                file.writeBytes(byteArray)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                image = MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            apiClient.getApiInterface(this).createJob(image, judul, detail, gaji, alamat).enqueue(object : retrofit2.Callback<CreateJobResponse> {
                override fun onResponse(call: Call<CreateJobResponse>, response: Response<CreateJobResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddJobActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@AddJobActivity, ProviderActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@AddJobActivity,  response.errorBody()?.string() , Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CreateJobResponse>, t: Throwable) {
                    Toast.makeText(this@AddJobActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        buttonAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, 1)
        }

        buttonTakeImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 2)
        }

    }

    private fun getImageUri(): Any {
        val bitmap = (findViewById<ImageView>(R.id.jobImage).drawable as BitmapDrawable).bitmap
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
            var jobImage = findViewById<ImageView>(R.id.jobImage)
            var imageUri = data.data
            jobImage.setImageURI(imageUri)
            isImageChanged = true
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            var jobImage = findViewById<ImageView>(R.id.jobImage)
            jobImage.setImageBitmap(imageBitmap)
            isImageChanged = true
        }
    }

    override fun onResume() {
        super.onResume()
        var editJobImage = findViewById<ImageView>(R.id.jobImage)
        var buttonAddImage = findViewById<Button>(R.id.addImage)
        var buttonTakeImage = findViewById<Button>(R.id.takeImage)

        var editJobJudul = findViewById<EditText>(R.id.jobJudul)
        var editJobDetail = findViewById<EditText>(R.id.jobDetail)
        var editJobAlamat = findViewById<EditText>(R.id.jobAlamat)
        var editJobGaji = findViewById<EditText>(R.id.jobGaji)
//        var editJobTanggal = findViewById<TextView>(R.id.jobTanggal)

        var buttonAddJob = findViewById<Button>(R.id.addJob)

        apiClient = ApiClient()

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val user = sp.getString("user", null)
        val userObj = Gson().fromJson(user, User::class.java)

        buttonAddJob.setOnClickListener {
            var image : MultipartBody.Part? = null
            val judul : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobJudul.text.toString())
            val detail : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobDetail.text.toString())
            val alamat : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobAlamat.text.toString())
            val gaji : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editJobGaji.text.toString())

            if(editJobJudul.text.toString().isEmpty()){
                editJobJudul.error = "Judul tidak boleh kosong"
                editJobJudul.requestFocus()
                return@setOnClickListener
            }

            if(editJobDetail.text.toString().isEmpty()){
                editJobDetail.error = "Detail tidak boleh kosong"
                editJobDetail.requestFocus()
                return@setOnClickListener
            }

            if(editJobAlamat.text.toString().isEmpty()){
                editJobAlamat.error = "Alamat tidak boleh kosong"
                editJobAlamat.requestFocus()
                return@setOnClickListener
            }

            if(editJobGaji.text.toString().isEmpty()){
                editJobGaji.error = "Gaji tidak boleh kosong"
                editJobGaji.requestFocus()
                return@setOnClickListener
            }

            if (isImageChanged) {
                val imageUri = getImageUri()
                val imageFile = File(getRealPathFromURI(imageUri))
                val requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
                image = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }else{
                val bitmap =
                    (resources.getDrawable(R.drawable.default_profile_picture) as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = File(cacheDir, "default_image.jpg")
                file.writeBytes(byteArray)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                image = MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            apiClient.getApiInterface(this).createJob(image, judul, detail, gaji, alamat).enqueue(object : retrofit2.Callback<CreateJobResponse> {
                override fun onResponse(call: Call<CreateJobResponse>, response: Response<CreateJobResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddJobActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@AddJobActivity, ProviderActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@AddJobActivity,  response.errorBody()?.string() , Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CreateJobResponse>, t: Throwable) {
                    Toast.makeText(this@AddJobActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        buttonAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, 1)
        }

        buttonTakeImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 2)
        }
    }
}