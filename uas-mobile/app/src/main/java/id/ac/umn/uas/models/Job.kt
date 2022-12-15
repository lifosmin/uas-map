package id.ac.umn.uas.models

data class Job(
    val created_at: String,
    val created_by: String,
    val id: Int,
    val job_date: String,
    val job_desc: Any,
    val job_price: String,
    val job_title: String,
    val updated_at: String
)