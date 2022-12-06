package id.ac.umn.uas.models

data class LoginResponse(
    val message: String,
    val error: String,
    val token: String
)