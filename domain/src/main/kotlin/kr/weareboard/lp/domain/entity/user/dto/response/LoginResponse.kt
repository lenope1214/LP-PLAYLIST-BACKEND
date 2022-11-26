package kr.weareboard.lp.domain.entity.user.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import kr.weareboard.lp.domain.entity.user.enum.UserRoleType
import kr.weareboard.lp.domain.jwt.dto.JwtToken

data class LoginResponse(
    val username: String,

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    val laundryId: Long? = null,
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    val laundryName: String? = null,
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    val clientId: Long? = null,
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    val clientName: String? = null,
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    val departmentId: Long? = null,
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    val departmentName: String? = null,

    val userId: Long,
    val name: String,
    val role: UserRoleType,
    override val accessToken: String,
    override val refreshToken: String,
) : JwtToken(accessToken, refreshToken)
