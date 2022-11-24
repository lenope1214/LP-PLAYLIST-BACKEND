package kr.co.jsol.domain.jwt.dto

open class JwtToken(
    open val refreshToken: String,
    open val accessToken: String,
    val tokenType: String = "Bearer ",
) {

}
