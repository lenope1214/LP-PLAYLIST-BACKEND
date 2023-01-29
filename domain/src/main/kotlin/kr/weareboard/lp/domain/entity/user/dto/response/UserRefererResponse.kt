package kr.weareboard.lp.domain.entity.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.weareboard.lp.domain.entity.user.User
import kr.weareboard.lp.domain.entity.user.enum.UserRoleType

data class UserRefererResponse(
    @field:Schema(name = "기본키")
    val id: Long,

    @field:Schema(name = "사용자 이름")
    val name: String,

) {
    companion object {
        fun of(
            user: User
        ): UserRefererResponse {
            return UserRefererResponse(
                id = user.id!!,
                name = user.name,
            )
        }
    }
}