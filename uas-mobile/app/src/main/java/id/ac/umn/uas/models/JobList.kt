package id.ac.umn.uas.models

import com.google.gson.annotations.SerializedName

public class JobList {
    @SerializedName("job")
    public val job: List<Job>? = null

    @SerializedName("message")
    public val message: String? = null
}