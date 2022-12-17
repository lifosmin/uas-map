package id.ac.umn.uas.models

data class User(
    val alamat: String,
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val image: String,
    val jenis_kelamin: String,
    val nama: String,
    val no_telp: String,
    val tanggal_lahir: String,
    val updated_at: String
)