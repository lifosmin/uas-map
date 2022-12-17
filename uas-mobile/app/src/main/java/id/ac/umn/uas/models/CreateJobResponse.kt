package id.ac.umn.uas.models

data class CreateJobResponse(
    val job: JobCreate,
    val message: String
)