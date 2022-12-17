package id.ac.umn.uas.models

data class JobCreate(
    val created_at: String,
    val created_by: Int,
    val id: Int,
    val job_desc: String,
    val job_image: String,
    val job_location: String,
    val job_price: String,
    val job_title: String,
    val updated_at: String
)