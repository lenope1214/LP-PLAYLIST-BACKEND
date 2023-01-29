package kr.weareboard.lp.domain.entity.lp.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import kr.weareboard.lp.domain.entity.user.enum.UserRoleType
import org.hibernate.validator.constraints.Length
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank

data class LpImgUpdateRequest(

    @field:Schema(name = "LP Id", description = "LP 아이디를 입력해주세요.")
    @field:NotBlank(message = "LP 아이디를 입력해주세요.")
    @field:Length(min = 1, message = "LP 아이디는 1이상입니다.")
    val id: Long,

    @field:Schema(name = "이미지 파일", description = "아이디는 4~16자 이내로 설정해주세요.")
    @field:NotBlank(message = "LP 이미지 정보를 입력해주세요.")
    val imgFile: MultipartFile,





    )