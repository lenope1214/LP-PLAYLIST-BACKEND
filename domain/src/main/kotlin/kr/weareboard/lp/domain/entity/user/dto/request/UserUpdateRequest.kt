package kr.weareboard.lp.domain.entity.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

data class UserUpdateRequest(
    @field:Schema(name = "계정 번호", description = "필수 입력입니다.")
    @field:Min(value = 1, message = "계정 번호는 1 이상 입력해주세요.")
    val id: Long,

    @field:Schema(name = "이름", description = "사용자 이름")
    @field:Length(min = 2, message = "이름은 2자 이상 입력해주세요.")
    val name: String?,

    @field:Schema(name = "연락처", description = "구분자로 사이에 -를 입력해주세요.")
//    @field:Pattern(
//        regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$",
//        message = "연락처 형식이 올바르지 않습니다."
//    )
    val phone: String?,
)
