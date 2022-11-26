package kr.weareboard.lp.domain.entity.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class PutPasswordRequest(
    @field:Schema(name = "비밀번호를 변경할 계정 아이디", description = "아이디는 4~16자 이내로 설정해주세요.")
    @field:NotBlank(message = "아이디를 입력해주세요.")
    @field:Length(min = 4, max = 16, message = "아이디는 4~16자 이내로 설정해주세요.")
    val username: String,

    @field:Schema(name = "변경할 계정 비밀번호", description = "비밀번호는 4~20자 이내로 설정해주세요.")
    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @field:Length(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 설정해주세요.")
    val password: String
)
