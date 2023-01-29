package kr.weareboard.lp.domain.entity.user.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import kr.weareboard.lp.domain.entity.lp.dto.response.LpSummaryResponse
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(name = "사용자 처음 로그인 여부, true라면 비밀번호 변경 필요")
    val isFirst: Boolean? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(name = "사용자 비밀번호 변경 유무, true라면 비밀번호 변경 필요")
    val changePassword: Boolean? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(name = "사용자가 읽지(확인하지) 않은 LP Id 리스트")
    var newLpIds: List<LpSummaryResponse> = listOf(),
) {
    companion object {

        fun of(
            user: User,
            newLpIds: List<LpSummaryResponse> = listOf(),
        ): UserResponse {
            var changePassword: Boolean? = null
            var isFirst: Boolean? = null

            // 비밀번호 변경을 해야 한다면?
            if(user.changePassword)changePassword = true;

            // 재 로그인 한 적이 없다면? (처음 로그인일때)
            if(!user.isLoggedIn()) isFirst = true;

            return UserResponse(
                id = user.id!!,
//                username = user.username,
                name = user.name,
                role = user.role,
                isFirst = isFirst,
                changePassword = changePassword,
                newLpIds = newLpIds,
            )
        }
    }
}