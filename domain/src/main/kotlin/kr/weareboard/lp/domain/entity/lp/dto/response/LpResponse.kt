package kr.weareboard.lp.domain.entity.lp.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import kr.weareboard.lp.domain.entity.lp.Lp
import kr.weareboard.lp.domain.entity.user.dto.response.UserRefererResponse

data class LpResponse(
    @field:Schema(name = "기본키")
    val id: Long,

    @field:Schema(name = "제목")
    val title: String,

    @field:Schema(name = "가수")
    val singer: String,

    @field:Schema(name = "메세지")
    val message: String,

    @field:Schema(name = "노래 url")
    val url: String,

    @field:Schema(name = "커버 이미지 url")
    val coverImgPath: String?,

    @field:Schema(name = "랜덤 커버 path (client static file path)")
    val randomCoverPath: String?,

    @field:Schema(name = "썸네일 이미지 url")
    val thumbnailImgPath: String?,

    @field:Schema(name = "기본키")
    val read: Boolean,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(name = "보낸 사람 정보")
    val writer: UserRefererResponse? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(name = "보낸 사람 닉네임")
    val writerNickname: String?,

    @field:Schema(name = "받은 사람 정보")
    val receiver: UserRefererResponse,
    
){
companion object {
        fun of(
            lp: Lp
        ): LpResponse {
            return LpResponse(
                id = lp.id!!,
                title = lp.title,
                singer = lp.singer,
                message = lp.message,
                url = lp.url,
                coverImgPath = lp.imgPath,
                randomCoverPath = lp.randomCoverPath,
                thumbnailImgPath = lp.thumbnailImgPath,
                read = lp.isRead,
                writer = lp.writer?.let { UserRefererResponse.of(it) },
                writerNickname = lp.writerNickname,
                receiver = lp.receiver!!.let { UserRefererResponse.of(it) },
            )
        }
    }
}