package kr.weareboard.lp.domain.entity.lp.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import kr.weareboard.lp.domain.entity.lp.Lp
import org.hibernate.validator.constraints.Length
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank

data class LpCreateRequest(
    @field:Schema(name = "제목", description = "제목은 최소 2글자 이상입니다.")
    @field:NotBlank(message = "제목을 입력해주세요.")
    @field:Length(min = 2, message = "제목은 최소 2글자 이상입니다.")
    val title: String?,

    @field:Schema(name = "가수명", description = "가수명은 최소 1글자 이상입니다.")
    @field:NotBlank(message = "가수명을 입력해주세요.")
    @field:Length(min = 1, message = "가수명은 최소 1글자 이상입니다.")
    val singer: String?,

    @field:Schema(name = "메세지", description = "메세지는 1~500글자입니다.")
    @field:NotBlank(message = "메세지를 입력해주세요.")
    @field:Length(min = 1, max = 500, message = "메세지는 1~500글자입니다.")
    val message: String?,

    @field:Schema(name = "노래 경로", description = "경로가 있다면 입력해주세요.")
    val url: String?,

    @field:Schema(name = "커스텀 이미지 파일", description = "커스텀 이미지 파일")
    val coverImgFile: MultipartFile?,

    @field:Schema(name = "커스텀 커버 경로(클라이언트 file path)", description = "경로가 있다면 입력해주세요.")
    val randomCoverPath: String?,

    @field:Schema(name = "썸네일 이미지 경로", description = "경로가 있다면 입력해주세요.")
    val thumbnailImgPath: String?,

    @field:Schema(name = "받는 사람 id", description = "받는 사람 id는 최소 1 이상입니다.")
    @field:Length(min = 1, message = "받는 사람 id는 최소 1 이상입니다.")
    val receiverId: Long?,

    @field:Schema(name = "닉네임", description = "작성자 닉네임을 입력해주세요. 1~50자 입니다.")
    @field:NotBlank(message = "작성자 닉네임을 입력해주세요.")
    @field:Length(min = 1, max = 50, message = "작성자 닉네임은 1~50자입니다.")
    val writerNickname: String?,
//    @field:Schema(name = "커스텀 이미지 경로", description = "경로가 있다면 입력해주세요.")
//    val imgPath: String,

//    @field:Schema(name = "열람 여부", description = "비밀번호는 4~20자 이내로 설정해주세요.")
//    val isRead: Boolean,


) {

    init {
        if (receiverId == null) {
            throw IllegalArgumentException("받는 사람 id를 입력해주세요.")
        }
    }

    fun toEntity(): Lp {
        return Lp(
            title = title!!,
            singer = singer!!,
            message = message!!,
            url = url ?: "",
            randomCoverPath = randomCoverPath ?: "",
            thumbnailImgPath = thumbnailImgPath ?: "",
            writerNickname = writerNickname!!,
        )
    }
}