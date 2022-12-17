package id.ac.umn.uas.models

data class GetJobApplicant(
    val message: String,
    val users: List<User>
)