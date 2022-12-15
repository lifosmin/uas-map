package id.ac.umn.uas.models

data class Job(
    val created_at: String,
    val created_by: String,
    val id: Int,
    val job_date: String,
    val job_desc: String,
    val job_image: String,
    val job_location: String,
    val job_price: String,
    val job_quota: String,
    val job_title: String,
    val status: String,
    val updated_at: String
)