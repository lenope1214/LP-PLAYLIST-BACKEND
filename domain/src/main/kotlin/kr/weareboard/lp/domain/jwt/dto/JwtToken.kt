package kr.weareboard.lp.domain.jwt.dto

open class JwtToken(
    open val refreshToken: String,
    open val accessToken: String,
    val tokenType: String = "Bearer ",
) {

}
