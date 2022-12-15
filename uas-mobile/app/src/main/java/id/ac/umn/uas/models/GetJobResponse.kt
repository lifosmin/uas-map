package id.ac.umn.uas.models

data class GetJobResponse(
    val job: List<Job>,
    val message: String
)