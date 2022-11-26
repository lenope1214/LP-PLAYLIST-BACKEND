package kr.weareboard.lp.domain.entity.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.weareboard.lp.domain.entity.user.User
import kr.weareboard.lp.domain.entity.user.enum.UserRoleType

data class UserResponse(
    @field:Schema(name = "기본키")
    val id: Long,

//    @field:Schema(name = "사용자 아이디")
//    val username: String,

    @field:Schema(name = "사용자 이름")
    val name: String,

    @field:Schema(name = "사용자 권한")
    val role: UserRoleType = UserRoleType.ROLE_USER,

    @field:Schema(name = "사용자 비밀번호 변경 유무, true라면 비밀번호 변경 필요")
    val changePassword: Boolean = false,
) {
    companion object {
        fun of(
            user: User
        ): UserResponse {
            return UserResponse(
                id = user.id!!,
//                username = user.username,
                name = user.name,
                role = user.role,
                changePassword = user.changePassword,
            )
        }
    }
}