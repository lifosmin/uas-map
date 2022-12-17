package id.ac.umn.uas.activities.User

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.activities.User.LoginActivity
import id.ac.umn.uas.api.ApiClient
import id.ac.umn.uas.models.DefaultResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class SignupActivity: AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private var image: MultipartBody.Part? = null
    private var isImageChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val editNamaText = findViewById<EditText>(R.id.editNamaText)
        val editEmailText = findViewById<EditText>(R.id.editEmailText)
        val editPasswordText = findViewById<EditText>(R.id.editPasswordText)
        val editTanggalLahirText = findViewById<EditText>(R.id.editTanggalLahirText)
        val editAlamatText = findViewById<EditText>(R.id.editAlamatText)
        val editNoTelpText = findViewById<EditText>(R.id.editNoTelponText)

        val radioKelamin = findViewById<RadioGroup>(R.id.radioGroupKelamin)

        val btnGambar = findViewById<Button>(R.id.uploadImage)
        val btnAmbilGambar = findViewById<Button>(R.id.takeImage)

        val btnSignup = findViewById<Button>(R.id.buttonSignUp)
        val btnLogin = findViewById<LinearLayout>(R.id.linearLayoutLogin)

        editTanggalLahirText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val monthString = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    editTanggalLahirText.setText("$dayOfMonth ${monthString[monthOfYear]} $year")
                }, year, month, day)
            dpd.show()
        }

        apiClient = ApiClient()

        btnSignup.setOnClickListener {
            var image : MultipartBody.Part? = null
            val nama : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editNamaText.text.toString())
            val email : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editEmailText.text.toString())
            val password : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editPasswordText.text.toString())
            val tanggalLahir : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editTanggalLahirText.text.toString())
            val jenisKelamin : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, if(radioKelamin.checkedRadioButtonId == R.id.radioLaki) "1" else "0")
            val alamat : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editAlamatText.text.toString())
            val noTelp : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editNoTelpText.text.toString())

            if(editNamaText.text.toString().isEmpty()){
                editNamaText.error = "Nama is required"
                editNamaText.requestFocus()
                return@setOnClickListener
            }
            if(editEmailText.text.toString().isEmpty()){
                editEmailText.error = "Email is required"
                editEmailText.requestFocus()
                return@setOnClickListener
            }
            if(editPasswordText.text.toString().isEmpty()){
                editPasswordText.error = "Password is required"
                editPasswordText.requestFocus()
                return@setOnClickListener
            }
            if(editTanggalLahirText.text.toString().isEmpty()){
                editTanggalLahirText.error = "Tanggal Lahir is required"
                editTanggalLahirText.requestFocus()
                return@setOnClickListener
            }
            if(radioKelamin.checkedRadioButtonId == -1){
                Toast.makeText(this, "Jenis Kelamin is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(editAlamatText.text.toString().isEmpty()){
                editAlamatText.error = "Alamat is required"
                editAlamatText.requestFocus()
                return@setOnClickListener
            }
            if(editNoTelpText.text.toString().isEmpty()) {
                editNoTelpText.error = "No Telpon is required"
                editNoTelpText.requestFocus()
                return@setOnClickListener
            }

            if(!isImageChanged) {
                val bitmap =
                    (resources.getDrawable(R.drawable.default_profile_picture) as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = File(cacheDir, "default_image.jpg")
                file.writeBytes(byteArray)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                image = MultipartBody.Part.createFormData("image", file.name, requestFile)
            }else {
                val imageUri = getImageUri()
                val imageFile = File(getRealPathFromURI(imageUri))
                val requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
                image = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }

            apiClient.getApiInterface(this).registerUser(image, nama, email, password, tanggalLahir, jenisKelamin, alamat, noTelp).enqueue(object: retrofit2.Callback<DefaultResponse> {
                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "Sign Up Berhasil!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignupActivity, "Sign Up Gagal!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
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
        val bitmap = (findViewById<CircleImageView>(R.id.profile_image).drawable as BitmapDrawable).bitmap
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
            isImageChanged = true
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            var profileImage = findViewById<CircleImageView>(R.id.profile_image)
            profileImage.setImageBitmap(imageBitmap)
            isImageChanged = true
        }
    }

    override fun onResume() {
        super.onResume()
        val editNamaText = findViewById<EditText>(R.id.editNamaText)
        val editEmailText = findViewById<EditText>(R.id.editEmailText)
        val editPasswordText = findViewById<EditText>(R.id.editPasswordText)
        val editTanggalLahirText = findViewById<EditText>(R.id.editTanggalLahirText)
        val editAlamatText = findViewById<EditText>(R.id.editAlamatText)
        val editNoTelpText = findViewById<EditText>(R.id.editNoTelponText)

        val radioKelamin = findViewById<RadioGroup>(R.id.radioGroupKelamin)

        val btnGambar = findViewById<Button>(R.id.uploadImage)
        val btnAmbilGambar = findViewById<Button>(R.id.takeImage)

        val btnSignup = findViewById<Button>(R.id.buttonSignUp)
        val btnLogin = findViewById<LinearLayout>(R.id.linearLayoutLogin)

        editTanggalLahirText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val monthString = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                editTanggalLahirText.setText("$dayOfMonth ${monthString[monthOfYear]} $year")
            }, year, month, day)
            dpd.show()
        }

        apiClient = ApiClient()

        btnSignup.setOnClickListener {
            var image : MultipartBody.Part? = null
            val nama : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editNamaText.text.toString())
            val email : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editEmailText.text.toString())
            val password : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editPasswordText.text.toString())
            val tanggalLahir : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editTanggalLahirText.text.toString())
            val jenisKelamin : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, if(radioKelamin.checkedRadioButtonId == R.id.radioLaki) "1" else "0")
            val alamat : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editAlamatText.text.toString())
            val noTelp : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, editNoTelpText.text.toString())

            if(editNamaText.text.toString().isEmpty()){
                editNamaText.error = "Nama is required"
                editNamaText.requestFocus()
                return@setOnClickListener
            }
            if(editEmailText.text.toString().isEmpty()){
                editEmailText.error = "Email is required"
                editEmailText.requestFocus()
                return@setOnClickListener
            }
            if(editPasswordText.text.toString().isEmpty()){
                editPasswordText.error = "Password is required"
                editPasswordText.requestFocus()
                return@setOnClickListener
            }
            if(editTanggalLahirText.text.toString().isEmpty()){
                editTanggalLahirText.error = "Tanggal Lahir is required"
                editTanggalLahirText.requestFocus()
                return@setOnClickListener
            }
            if(radioKelamin.checkedRadioButtonId == -1){
                Toast.makeText(this, "Jenis Kelamin is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(editAlamatText.text.toString().isEmpty()){
                editAlamatText.error = "Alamat is required"
                editAlamatText.requestFocus()
                return@setOnClickListener
            }
            if(editNoTelpText.text.toString().isEmpty()) {
                editNoTelpText.error = "No Telpon is required"
                editNoTelpText.requestFocus()
                return@setOnClickListener
            }

            if(!isImageChanged) {
                val bitmap =
                    (resources.getDrawable(R.drawable.default_profile_picture) as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = File(cacheDir, "default_image.jpg")
                file.writeBytes(byteArray)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                image = MultipartBody.Part.createFormData("image", file.name, requestFile)
            }else {
                val imageUri = getImageUri()
                val imageFile = File(getRealPathFromURI(imageUri))
                val requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
                image = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }

            apiClient.getApiInterface(this).registerUser(image, nama, email, password, tanggalLahir, jenisKelamin, alamat, noTelp).enqueue(object: retrofit2.Callback<DefaultResponse> {
                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "Sign Up Berhasil!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignupActivity, "Sign Up Gagal!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
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

}