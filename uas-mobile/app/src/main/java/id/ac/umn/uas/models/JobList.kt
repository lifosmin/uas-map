package id.ac.umn.uas.models

import com.google.gson.annotations.SerializedName

public class JobList {
    @SerializedName("data")
    public val job: List<Job>? = null
}