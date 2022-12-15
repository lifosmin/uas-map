package id.ac.umn.uas.models

import com.google.gson.annotations.SerializedName

data class GetJobResponse(
    val job: List<Job>,
    val message: String
)